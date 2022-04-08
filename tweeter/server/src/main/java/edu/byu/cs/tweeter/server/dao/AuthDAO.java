package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public interface AuthDAO {
    AuthToken generateAuthToken(String alias);
    boolean validate(AuthToken authToken);
    void deleteAuthToken(AuthToken authToken);
}
