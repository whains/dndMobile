package edu.byu.cs.tweeter.client.model.service;

import android.os.Message;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.executor.Executor;
import edu.byu.cs.tweeter.client.model.service.handler.AuthenticateHandler;
import edu.byu.cs.tweeter.client.model.service.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService {

    public interface AuthenticateObserver extends ServiceObserver {
        void authenticateSucceeded(AuthToken authToken, User user);
    }

    public interface LogoutObserver extends ServiceObserver {
        void logoutSucceeded();
    }

    public interface GetUserObserver extends ServiceObserver{
        void getUserSucceeded(User user);
    }

    public void login(String alias, String password, AuthenticateObserver observer) {
        Executor.execute(new LoginTask(alias, password, new LoginHandler(observer)));
    }

    public void logout(AuthToken authToken, String alias, LogoutObserver observer) {
        Executor.execute(new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), alias, new LogoutHandler(observer)));
    }

    public void getUser(AuthToken authToken, String alias, GetUserObserver observer) {
        Executor.execute(new GetUserTask(authToken, alias, new GetUserHandler(observer)));
    }

    public void register(String firstName, String lastName, String alias, String password, String imageBytesBase64, AuthenticateObserver observer) {
        Executor.execute(new RegisterTask(firstName, lastName, alias, password, imageBytesBase64, new RegisterHandler(observer)));
    }

    private class LoginHandler extends AuthenticateHandler {
        public LoginHandler(AuthenticateObserver observer) {
            super(observer);
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to login";
        }
    }

    private class RegisterHandler extends AuthenticateHandler {
        public RegisterHandler(AuthenticateObserver observer) {
            super(observer);
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to register";
        }
    }

    private class LogoutHandler extends BackgroundTaskHandler<LogoutObserver>{
        public LogoutHandler(LogoutObserver observer) {
            super(observer);
        }
        @Override
        protected void handleSuccessMessage(Message msg) {
            observer.logoutSucceeded();
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to logout";
        }
    }

    private class GetUserHandler extends BackgroundTaskHandler<GetUserObserver> {
        public GetUserHandler(GetUserObserver observer) {
            super(observer);
        }
        @Override
        protected void handleSuccessMessage(Message msg) {
            User user = (User) msg.getData().getSerializable(GetUserTask.USER_KEY);
            observer.getUserSucceeded(user);
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get user's profile";
        }
    }
}
