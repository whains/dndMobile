package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

public class RegisterTask extends AuthenticateTask {
    private static final String LOG_TAG = "LoginTask";
    private static final String URL_PATH = "/register";

    private String firstName;
    private String lastName;
    private String password;
    private String image;

    private RegisterRequest registerRequest;

    public RegisterTask(String firstName, String lastName, String username, String password,
                        String image, Handler messageHandler) {

        super(messageHandler, username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        registerRequest = new RegisterRequest(firstName, lastName, username, password, image);

    }

    @Override
    protected boolean runTask() throws IOException {
        try {
            RegisterResponse registerResponse = serverFacade.register(registerRequest, URL_PATH);
            if(registerResponse.isSuccess()) {
                this.authenticatedUser = registerResponse.getUser();
                this.authToken = registerResponse.getAuthToken();

                BackgroundTaskUtils.loadImage(registerResponse.getUser());
            }
            else {
                this.errorMessage = registerResponse.getMessage();
                return false;
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
            return false;
        }
        return true;
    }
}
