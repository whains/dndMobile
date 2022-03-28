package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowersCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersCountResponse;

public class GetFollowersCountTask extends GetCountTask {
    private static final String LOG_TAG = "GetFollowersCountTask";
    private static final String URL_PATH = "/followersCount";

    FollowersCountRequest followersCountRequest;

    public GetFollowersCountTask(AuthToken authToken, User targetUser, Handler messageHandler) {
        super(authToken, targetUser, messageHandler);
        followersCountRequest = new FollowersCountRequest(targetUser, authToken);
    }

    @Override
    protected boolean runTask() {
        try {
            FollowersCountResponse response = serverFacade.getFollowersCount(followersCountRequest, URL_PATH);

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
