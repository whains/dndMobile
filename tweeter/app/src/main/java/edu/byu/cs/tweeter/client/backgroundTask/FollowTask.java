package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;

public class FollowTask extends AuthorizedTask {
    private static final String LOG_TAG = "FollowTask";
    private static final String URL_PATH= "/follow";

    private FollowRequest followRequest;

    public FollowTask(AuthToken authToken, User followee, User currentUser, Handler messageHandler) {
        super(authToken, messageHandler);
        String followeeAlias = (followee == null) ? null : followee.getAlias();
        String currentUserAlias = (currentUser == null) ? null : currentUser.getAlias();
        followRequest = new FollowRequest(authToken, followeeAlias, currentUserAlias);
    }

    @Override
    protected boolean runTask() throws IOException {
        try {
            FollowResponse response = serverFacade.follow(followRequest, URL_PATH);
            return response.isSuccess();
        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to follow", ex);
            sendExceptionMessage(ex);
        }
        return true;
    }
}
