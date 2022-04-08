package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.util.Pair;

public class GetFollowersTask extends PagedUserTask{
    private static final String LOG_TAG = "GetFollowersTask";
    private static final String URL_PATH ="/followers";

    FollowersRequest followersRequest;

    public GetFollowersTask(AuthToken authToken, User targetUser, int limit, User lastFollower,
                            Handler messageHandler) {
        super(authToken, targetUser, limit, lastFollower, messageHandler);
        String targetUserAlias = (targetUser == null) ? null : targetUser.getAlias();
        String lastFolloweeAlias = (lastFollower == null) ? null : lastFollower.getAlias();
        followersRequest = new FollowersRequest(authToken, targetUserAlias, limit, lastFolloweeAlias);
    }

    @Override
    protected Pair<List<User>, Boolean> getItems() {
        Pair<List<User>, Boolean> result = null;
        try {
            FollowersResponse response = serverFacade.getFollowers(followersRequest, URL_PATH);
            result = new Pair<>(response.getFollowers(), response.getHasMorePages());
            return result;
        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to get followers", ex);
            sendExceptionMessage(ex);
        }
        return result;
    }
}
