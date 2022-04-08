package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;

public class LogoutTask extends AuthorizedTask {
    private static final String LOG_TAG = "LogoutTask";
    private static final String URL_PATH = "/logout";

    private LogoutRequest logoutRequest;

    public LogoutTask(AuthToken authToken, String alias, Handler messageHandler) {
        super(authToken, messageHandler);
        this.logoutRequest = new LogoutRequest(authToken, alias);
    }

    @Override
    protected boolean runTask() throws IOException {
        try {
            LogoutResponse logoutResponse = serverFacade.logout(logoutRequest, URL_PATH);
            return logoutResponse.isSuccess();
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
        return true;
    }
}
