package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public abstract class PagedTask<T> extends AuthorizedTask {

    public static final String ITEMS_KEY = "items";
    public static final String MORE_PAGES_KEY = "more-pages";

    protected final User targetUser;
    protected final int limit;
    protected final T lastItem;
    protected List<T> items;
    protected boolean hasMorePages;

    protected PagedTask(AuthToken authToken, User targetUser, int limit, T lastItem, Handler messageHandler) {
        super(authToken, messageHandler);
        this.targetUser = targetUser;
        this.limit = limit;
        this.lastItem = lastItem;
    }

    @Override
    protected final void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(ITEMS_KEY, (Serializable) items);
        msgBundle.putBoolean(MORE_PAGES_KEY, hasMorePages);
    }

    @Override
    protected boolean runTask() throws IOException {
        Pair<List<T>, Boolean> pageOfStatus = getItems();
        this.items = pageOfStatus.getFirst();
        this.hasMorePages = pageOfStatus.getSecond();
        for (User user : getUsersForItems(this.items)) {
            BackgroundTaskUtils.loadImage(user);
        }
        return true;
    }

    protected abstract Pair<List<T>, Boolean> getItems();

    protected abstract List<User> getUsersForItems(List<T> items);
}
