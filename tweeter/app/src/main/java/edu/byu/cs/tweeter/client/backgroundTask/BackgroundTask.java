package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;

public abstract class BackgroundTask implements Runnable {
    private static final String LOG_TAG = "BackgroundTask";
    public static final String SUCCESS_KEY = "success";
    public static final String MESSAGE_KEY = "message";
    public static final String EXCEPTION_KEY = "exception";
    protected final Handler messageHandler;
    protected String errorMessage;

    public ServerFacade serverFacade;

    protected BackgroundTask(Handler messageHandler) {
        this.messageHandler = messageHandler;
        this.serverFacade = getServerFacade();
    }

    @Override
    public void run() {
        try {
            if (runTask()) {
                sendSuccessMessage();
            }
            else {
                sendFailedMessage(this.errorMessage);
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }

    protected abstract boolean runTask() throws IOException;

    private void sendSuccessMessage() {
        Bundle msgBundle = createMessageBundle(true);
        loadSuccessBundle(msgBundle);
        sendMessage(msgBundle);
    }

    protected void loadSuccessBundle(Bundle msgBundle) {
        return;
    }

    protected void sendFailedMessage(String message) {
        Bundle msgBundle = createMessageBundle(false);
        msgBundle.putString(MESSAGE_KEY, message);
        sendMessage(msgBundle);
    }

    protected void sendExceptionMessage(Exception exception) {
        Bundle msgBundle = createMessageBundle(false);
        msgBundle.putSerializable(EXCEPTION_KEY, exception);
        sendMessage(msgBundle);
    }

    @NotNull
    private Bundle createMessageBundle(boolean value) {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, value);
        return msgBundle;
    }

    private void sendMessage(Bundle msgBundle) {
        Message msg = Message.obtain();
        msg.setData(msgBundle);
        messageHandler.sendMessage(msg);
    }

    private ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
