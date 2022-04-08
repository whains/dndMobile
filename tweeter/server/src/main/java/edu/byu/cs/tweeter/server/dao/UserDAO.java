package edu.byu.cs.tweeter.server.dao;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public interface UserDAO {
    User login(String alias, String password);
    User getUserByAlias(String alias);
    int getFollowersCount(String alias);
    int getFollowingCount(String alias);
    void updateFollowingCount(String alias, int toAdd);
    void updateFollowersCount(String alias, int toAdd);
    User register(String firstName, String lastName, String alias, String password, String imageBytesBase64, String imageURL);
    void batchUserWrite(List<User> users);
}
