package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.util.Pair;

public interface FollowDAO {
    Pair<List<User>, Boolean> getFollowees(String followerAlias, int limit, String lastFolloweeAlias);
    Pair<List<User>, Boolean> getFollowers(String followeeAlias, int limit, String lastFollowerAlias);
    boolean follow(String currentUserAlias, User currentUser, String followeeAlias, User followee);
    boolean unfollow(String currentUserAlias, String followeeAlias);
    boolean isFollower(String followerAlias, String followeeAlias);
    List<String> getAllFollowers(String alias);
    void batchFollowWrite(String target, List<User> users);
}
