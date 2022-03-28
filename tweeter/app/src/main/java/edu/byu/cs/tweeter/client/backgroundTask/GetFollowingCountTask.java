package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;

public class GetFollowingCountTask extends GetCountTask {
    private static final String LOG_TAG = "GetFollowingCountTask";
    private static final String URL_PATH = "/followingCount";

    private FollowingCountRequest followingCountRequest;

    public GetFollowingCountTask(AuthToken authToken, User targetUser, Handler messageHandler) {
        super(authToken, targetUser, messageHandler);
        followingCountRequest = new FollowingCountRequest(targetUser, authToken);
    }

    @Override
    protected boolean runTask() throws IOException {
        try {
            FollowingCountResponse response = serverFacade.getFollowingCount(followingCountRequest, URL_PATH);

            if(response.isSuccess()) {
                this.count = response.getCount();
            }
            else {
                sendFailedMessage(response.getMessage());
            }
        }catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
        return true;
    }
}
