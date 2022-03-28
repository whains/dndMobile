package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FeedRequest;
import edu.byu.cs.tweeter.model.net.response.FeedResponse;
import edu.byu.cs.tweeter.util.Pair;

public class GetFeedTask extends PagedStatusTask {
    private static final String LOG_TAG = "GetFeedTask";
    private static final String URL_PATH= "/feed";

    private FeedRequest feedRequest;

    public GetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                       Handler messageHandler) {
        super(authToken, targetUser, limit, lastStatus, messageHandler);
        this.feedRequest = new FeedRequest(authToken, targetUser.getAlias(), limit, lastStatus);
    }

    @Override
    protected Pair<List<Status>, Boolean> getItems() {
        Pair<List<Status>, Boolean> result = null;
        try {
            FeedResponse response = serverFacade.getFeed(feedRequest, URL_PATH);
            result = new Pair<>(response.getStatuses(), response.getHasMorePages());
            return result;
        } catch (IOException | TweeterRemoteException ex) {
            Log.e(LOG_TAG, "Failed to get followees", ex);
            sendExceptionMessage(ex);
        }
        return result;
    }
}
