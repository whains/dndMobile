package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public interface PostStatusQueueClient {
    public boolean postStatusToQueue(Status status, AuthToken authToken);
}
