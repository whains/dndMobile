package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;

public class PostStatusTask extends AuthorizedTask {
    private static final String LOG_TAG = "PostStatusTask";
    private static final String URL_PATH= "/postStatus";

    private PostStatusRequest postStatusRequest;

    public PostStatusTask(AuthToken authToken, Status status, String alias, Handler messageHandler) {
        super(authToken, messageHandler);
        postStatusRequest = new PostStatusRequest(authToken, status, alias);
    }

    @Override
    protected boolean runTask() throws IOException {
        try {
            PostStatusResponse response = serverFacade.postStatus(postStatusRequest, URL_PATH);
            return response.isSuccess();
        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to post status", ex);
            sendExceptionMessage(ex);
        }
        return true;
    }
}
