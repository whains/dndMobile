package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.net.request.UpdateFeedRequest;

public interface UpdateFeedQueueClient {
    public boolean postStatusAndFollowersToQueue(UpdateFeedRequest updateFeedRequest);
}
