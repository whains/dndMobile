package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;

public class IsFollowerTask extends AuthorizedTask {
    private static final String LOG_TAG = "IsFollowerTask";
    private static final String URL_PATH="/isFollower";
    public static final String IS_FOLLOWER_KEY = "is-follower";

    private boolean isFollower;

    private IsFollowerRequest isFollowerRequest;

    public IsFollowerTask(AuthToken authToken, User follower, User followee, Handler messageHandler) {
        super(authToken, messageHandler);
        String followerAlias = (follower == null) ? null : follower.getAlias();
        String followeeAlias = (followee == null) ? null : followee.getAlias();
        isFollowerRequest = new IsFollowerRequest(authToken, followerAlias, followeeAlias);
    }

    @Override
    protected boolean runTask() throws IOException {
        try {
            IsFollowerResponse response = serverFacade.isFollower(isFollowerRequest, URL_PATH);
            this.isFollower = response.getFollower();
        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to see if follows user", ex);
            sendExceptionMessage(ex);
        }
        return true;
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putBoolean(IS_FOLLOWER_KEY, isFollower);
    }

}
