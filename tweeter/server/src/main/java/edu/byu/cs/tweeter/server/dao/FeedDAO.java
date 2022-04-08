package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.util.Pair;

public interface FeedDAO {
    Pair<List<Status>, Boolean> getFeed(String alias, int limit, Status lastStatus);
    boolean postStatusToUserFeed(Status status, String alias);
    boolean batchFeedWrite(Status status, List<User> followers);
}
