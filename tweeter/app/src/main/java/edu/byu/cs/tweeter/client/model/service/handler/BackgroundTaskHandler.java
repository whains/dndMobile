package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

public abstract class BackgroundTaskHandler<T extends ServiceObserver> extends Handler {

    protected final T observer;

    public BackgroundTaskHandler(T observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(BackgroundTask.SUCCESS_KEY);
        if (success) {
            handleSuccessMessage(msg);
        } else if (msg.getData().containsKey(BackgroundTask.MESSAGE_KEY)) {
            String message = getFailedMessagePrefix() + ": " + msg.getData().getString(GetFollowersCountTask.MESSAGE_KEY);
            observer.handleFailed(message);
        } else if (msg.getData().containsKey(BackgroundTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(BackgroundTask.EXCEPTION_KEY);
            String message = getFailedMessagePrefix() + " because of exception: " + ex.getMessage();
            observer.handleFailed(message);
        }
    }

    protected abstract void handleSuccessMessage(Message msg);

    protected abstract String getFailedMessagePrefix();
}