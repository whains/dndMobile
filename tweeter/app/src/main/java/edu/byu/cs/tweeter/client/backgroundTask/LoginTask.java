package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;

public class LoginTask extends AuthenticateTask {
    private static final String LOG_TAG = "LoginTask";
    private static final String URL_PATH = "/login";

    LoginRequest loginRequest;

    public LoginTask(String username, String password, Handler messageHandler) {
        super(messageHandler, username, password);
        this.loginRequest = new LoginRequest(username, password);
    }

    @Override
    protected final boolean runTask() throws IOException {
        try {
            LoginResponse response = serverFacade.login(loginRequest, URL_PATH);

            if(response.isSuccess()) {
                this.authenticatedUser = response.getUser();
                this.authToken = response.getAuthToken();

                BackgroundTaskUtils.loadImage(response.getUser());
            }
            else {
                errorMessage = response.getMessage();
                return false;
            }
        }catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
        return true;
    }
}
