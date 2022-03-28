package edu.byu.cs.tweeter.client.model.service.observer;

import java.net.MalformedURLException;
import java.util.List;

public interface PagedObserver<T> extends ServiceObserver {
    void getItemsSucceeded(List<T> items, boolean hasMorePages) throws MalformedURLException;
}
