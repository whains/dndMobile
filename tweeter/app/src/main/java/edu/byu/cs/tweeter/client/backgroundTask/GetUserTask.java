package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.UserRequest;
import edu.byu.cs.tweeter.model.net.response.UserResponse;

public class GetUserTask extends AuthorizedTask {
    private static final String LOG_TAG = "GetUserTask";
    public static final String USER_KEY = "user";
    private static final String URL_PATH="/user";

    private User user;

    UserRequest userRequest;

    public GetUserTask(AuthToken authToken, String alias, Handler messageHandler) {
        super(authToken, messageHandler);
        userRequest = new UserRequest(authToken, alias);
    }

    @Override
    protected boolean runTask() throws IOException {
        try {
            UserResponse userResponse = serverFacade.getUser(userRequest, URL_PATH);
            if (!userResponse.isSuccess()) {
                this.errorMessage = userResponse.getMessage();
                return false;
            }
            this.user = userResponse.getUser();
            BackgroundTaskUtils.loadImage(user);
        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to get user", ex);
            sendExceptionMessage(ex);
            return false;
        }
        return true;
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, user);
    }
}
