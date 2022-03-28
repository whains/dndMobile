package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Message;

import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthenticateHandler extends BackgroundTaskHandler<UserService.AuthenticateObserver> {

    public AuthenticateHandler(UserService.AuthenticateObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(Message msg) {
        User user = (User) msg.getData().getSerializable(RegisterTask.USER_KEY);
        AuthToken authToken = (AuthToken) msg.getData().getSerializable(RegisterTask.AUTH_TOKEN_KEY);
        Cache.getInstance().setCurrUser(user);
        Cache.getInstance().setCurrUserAuthToken(authToken);
        observer.authenticateSucceeded(authToken, user);
    }

    @Override
    protected String getFailedMessagePrefix() {
        return "";
    }
}
