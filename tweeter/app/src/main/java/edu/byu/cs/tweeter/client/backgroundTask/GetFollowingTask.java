package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.util.Pair;


public class GetFollowingTask extends PagedUserTask {
    private static final String LOG_TAG = "GetFollowingTask";
    static final String URL_PATH = "/getFollowing";

    FollowingRequest followingRequest;

    public GetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee,
                            Handler messageHandler) {
        super(authToken, targetUser, limit, lastFollowee, messageHandler);
        String targetUserAlias = (targetUser == null) ? null : targetUser.getAlias();
        String lastFolloweeAlias = (lastFollowee == null) ? null : lastFollowee.getAlias();
        this.followingRequest = new FollowingRequest(authToken, targetUserAlias, limit, lastFolloweeAlias);
    }

    @Override
    protected Pair<List<User>, Boolean> getItems() {
        Pair<List<User>, Boolean> result = null;
        try {
            FollowingResponse response = serverFacade.getFollowees(followingRequest, URL_PATH);
             result = new Pair<>(response.getFollowees(), response.getHasMorePages());
            return result;
        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to get followees", ex);
            sendExceptionMessage(ex);
        }
        return result;
    }
}
