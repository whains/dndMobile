package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;

import edu.byu.cs.tweeter.model.net.request.UpdateFeedRequest;
import edu.byu.cs.tweeter.server.service.StatusService;

public class UpdateFeeds implements RequestHandler<SQSEvent, Void> {
    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            String json = msg.getBody();
            System.out.println(json);
            UpdateFeedRequest updateFeedRequest = (new Gson()).fromJson(json, UpdateFeedRequest.class);
            StatusService statusService = new StatusService(HandlerConfiguration.getInstance().getFactory());
            boolean posted = statusService.batchFeedWrite(updateFeedRequest);
            if (!posted) {
                throw new RuntimeException("[InternalServerError] Problem Batch Writing post to feeds");
            }
        }
        return null;
    }
}
