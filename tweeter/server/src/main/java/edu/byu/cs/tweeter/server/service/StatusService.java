package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.request.UpdateFeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.factories.DAOFactory;
import edu.byu.cs.tweeter.server.util.Pair;

public class StatusService {
    DAOFactory daoFactory;

    public StatusService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public PostStatusResponse postStatus(PostStatusRequest request) {
        if (daoFactory.getAuthDAO().validate(request.getAuthToken())) {
            request.getStatus().getUser().setImageBytes(null);
            boolean sendMessageToPostStatusQueue = daoFactory.getPostStatusQueueClient().postStatusToQueue(request.getStatus(), request.getAuthToken());
            boolean postToStory = daoFactory.getStoryDAO().postStatusToStory(request.getStatus(), request.getAlias());
            boolean postToUserFeed = daoFactory.getFeedDAO().postStatusToUserFeed(request.getStatus(), request.getAlias());
            return new PostStatusResponse(postToStory && sendMessageToPostStatusQueue && postToUserFeed);
        } else {
            throw new RuntimeException("[InternalServerError] Could not Authenticate");
        }
    }

    public boolean batchFeedWrite(UpdateFeedRequest updateFeedRequest) {
        return daoFactory.getFeedDAO().batchFeedWrite(updateFeedRequest.getStatus(), updateFeedRequest.getFollowers());
    }

    public FeedResponse getFeed(FeedRequest request) {
        if (daoFactory.getAuthDAO().validate(request.getAuthToken())) {
            Pair<List<Status>, Boolean> pair = daoFactory.getFeedDAO().getFeed(request.getAlias(), request.getLimit(), request.getLastStatus());
            return new FeedResponse(pair.getFirst(), pair.getSecond());
        } else {
            throw new RuntimeException("[InternalServerError] Could not Authenticate");
        }
    }

    public StoryResponse getStory(StoryRequest request) {
        if (daoFactory.getAuthDAO().validate(request.getAuthToken())) {
            Pair<List<Status>, Boolean> pair = daoFactory.getStoryDAO().getStory(request.getAlias(), request.getLimit(), request.getLastStatus());
            return new StoryResponse(pair.getFirst(), pair.getSecond());
        } else {
            throw new RuntimeException("[InternalServerError] Could not Authenticate");
        }
    }
}
