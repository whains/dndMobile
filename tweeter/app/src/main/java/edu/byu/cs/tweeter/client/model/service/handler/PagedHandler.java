package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Message;

import java.net.MalformedURLException;
import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;

public abstract class PagedHandler<T> extends BackgroundTaskHandler<PagedObserver> {
    public PagedHandler(PagedObserver observer) {
        super(observer);
    }
    @Override
    protected void handleSuccessMessage(Message msg) {
        List<T> items = (List<T>) msg.getData().getSerializable(GetFeedTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(GetFeedTask.MORE_PAGES_KEY);
        try {
            observer.getItemsSucceeded(items, hasMorePages);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
