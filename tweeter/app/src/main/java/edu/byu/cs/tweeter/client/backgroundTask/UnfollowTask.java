package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;

public class UnfollowTask extends AuthorizedTask {
    private static final String LOG_TAG = "UnfollowTask";
    private static final String URL_PATH= "/unfollow";

    private UnfollowRequest unfollowRequest;

    public UnfollowTask(AuthToken authToken, User followee, User currentUser, Handler messageHandler) {
        super(authToken, messageHandler);
        String followeeAlias = (followee == null) ? null : followee.getAlias();
        String currentUserAlias = (currentUser == null) ? null : currentUser.getAlias();
        unfollowRequest = new UnfollowRequest(authToken, followeeAlias, currentUserAlias);
    }

    @Override
    protected boolean runTask() throws IOException {
        try {
            UnfollowResponse response = serverFacade.unfollow(unfollowRequest, URL_PATH);
            return response.isSuccess();
        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to unfollow user", ex);
            sendExceptionMessage(ex);
        }
        return true;
    }
}
