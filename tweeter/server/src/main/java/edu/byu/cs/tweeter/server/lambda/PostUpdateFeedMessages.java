package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.PostUpdateFeedRequest;
import edu.byu.cs.tweeter.model.net.request.UpdateFeedRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.server.dao.UpdateFeedQueueClient;
import edu.byu.cs.tweeter.server.service.FollowService;

public class PostUpdateFeedMessages implements RequestHandler<SQSEvent, Void> {
    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            String json = msg.getBody();
            System.out.println(json);
            PostUpdateFeedRequest postUpdateFeedRequest = (new Gson()).fromJson(json, PostUpdateFeedRequest.class);
            FollowService followService = new FollowService(HandlerConfiguration.getInstance().getFactory());
            Status status = postUpdateFeedRequest.getStatus();

            System.out.println("First");

            String lastFollowerAlias = null;
            int numFollowersGotten = 0;
            while (true) {
                FollowersRequest followersRequest =
                        new FollowersRequest(postUpdateFeedRequest.getAuthToken(), status.getUser().getAlias(), 25, lastFollowerAlias);
                FollowersResponse followersResponse = followService.getFollowers(followersRequest);
                if (!followersResponse.isSuccess()) {
                    throw new RuntimeException("[InternalServerError] Could not Get Followers (SQS Post Status)");
                }
                numFollowersGotten += followersResponse.getFollowers().size();
                System.out.println(numFollowersGotten);
                UpdateFeedRequest updateFeedRequest =
                        new UpdateFeedRequest(followersResponse.getFollowers(), status, postUpdateFeedRequest.getAuthToken());
                UpdateFeedQueueClient updateFeedQueueClient = HandlerConfiguration.getInstance().getFactory().getUpdateFeedQueueClient();
                boolean sent = updateFeedQueueClient.postStatusAndFollowersToQueue(updateFeedRequest);
                if (!sent) {
                    throw new RuntimeException("[InternalServerError] Could not post message to Queue 2 (SQS Update Feed)");
                }
                if (!followersResponse.getHasMorePages()) {
                    break;
                }
                try {
                    int size = followersResponse.getFollowers().size();
                    lastFollowerAlias = followersResponse.getFollowers().get(size - 1).getAlias();
                } catch (IndexOutOfBoundsException e) {
                    throw new RuntimeException("[InternalServerError] Could not get last follower alias to Queue 2 (SQS Update Feed)");
                }
            }

        }
        return null;
    }
}
