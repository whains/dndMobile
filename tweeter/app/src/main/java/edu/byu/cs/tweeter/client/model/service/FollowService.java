package edu.byu.cs.tweeter.client.model.service;

import android.os.Message;

import edu.byu.cs.tweeter.client.backgroundTask.FollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.backgroundTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.UnfollowTask;
import edu.byu.cs.tweeter.client.model.service.executor.Executor;
import edu.byu.cs.tweeter.client.model.service.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.service.handler.PagedHandler;
import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService {

    public interface UnfollowObserver extends ServiceObserver {
        void getUnfollowSucceeded();
    }

    public interface FollowObserver extends ServiceObserver {
        void getFollowSucceeded();
    }

    public interface GetFollowersCountObserver extends ServiceObserver {
        void getFollowersCountSucceeded(int count);
    }

    public interface GetFollowingCountObserver extends ServiceObserver {
        void getFollowingCountSucceeded(int count);
    }

    public interface IsFollowerObserver extends ServiceObserver {
        void isFollowerSucceeded(boolean isFollower);
    }

    public void getFollowing(AuthToken authToken, User targetUser, int limit, User lastFollowee, PagedObserver<User> observer) {
        Executor.execute(new GetFollowingTask(authToken, targetUser, limit, lastFollowee, new GetFollowingHandler(observer)));
    }

    public void getFollowers(AuthToken authToken, User targetUser, int limit, User lastFollower, PagedObserver<User> observer) {
        Executor.execute(new GetFollowersTask(authToken, targetUser, limit, lastFollower, new GetFollowersHandler(observer)));
    }

    public void unfollow(AuthToken authToken, User selectedUser, User currentUser, UnfollowObserver observer) {
        Executor.execute(new UnfollowTask(authToken ,selectedUser, currentUser, new UnfollowHandler(observer)));
    }

    public void follow(AuthToken authToken, User selectedUser, User currentUser, FollowObserver observer) {
        Executor.execute(new FollowTask(authToken, selectedUser, currentUser, new FollowHandler(observer)));
    }

    public void getFollowersCount(AuthToken authToken, User selectedUser, GetFollowersCountObserver observer){
        Executor.execute(new GetFollowersCountTask(authToken, selectedUser, new GetFollowersCountHandler(observer)));
    }

    public void getFollowingCount(AuthToken authToken, User selectedUser, GetFollowingCountObserver observer) {
        Executor.execute(new GetFollowingCountTask(authToken, selectedUser, new GetFollowingCountHandler(observer)));
    }

    public void isFollower(AuthToken authToken, User currentUser, User selectedUser, IsFollowerObserver observer) {
        Executor.execute(new IsFollowerTask(authToken, currentUser, selectedUser, new IsFollowerHandler(observer)));
    }

    private class GetFollowingHandler extends PagedHandler<User> {
        public GetFollowingHandler(PagedObserver<User> observer) {
            super(observer);
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get following";
        }
    }

    private class GetFollowersHandler extends PagedHandler<User> {
        public GetFollowersHandler(PagedObserver<User> observer) {
            super(observer);
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get followers";
        }
    }

    private class UnfollowHandler extends BackgroundTaskHandler<UnfollowObserver> {
        public UnfollowHandler(UnfollowObserver observer){
            super(observer);
        }
        @Override
        protected void handleSuccessMessage(Message msg) {
            observer.getUnfollowSucceeded();
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to unfollow";
        }
    }

    private class FollowHandler extends BackgroundTaskHandler<FollowObserver> {
        public FollowHandler(FollowObserver observer){
            super(observer);
        }
        @Override
        protected void handleSuccessMessage(Message msg) {
            observer.getFollowSucceeded();
        }

        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to follow";
        }
    }

    private class GetFollowersCountHandler extends BackgroundTaskHandler<GetFollowersCountObserver> {
        public GetFollowersCountHandler(GetFollowersCountObserver observer) {
            super(observer);
        }
        @Override
        protected void handleSuccessMessage(Message msg) {
            int count = msg.getData().getInt(GetFollowersCountTask.COUNT_KEY);
            observer.getFollowersCountSucceeded(count);
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get followers count";
        }
    }

    private class GetFollowingCountHandler extends BackgroundTaskHandler<GetFollowingCountObserver> {
        public GetFollowingCountHandler(GetFollowingCountObserver observer){
            super(observer);
        }
        @Override
        protected void handleSuccessMessage(Message msg) {
            int count = msg.getData().getInt(GetFollowingCountTask.COUNT_KEY);
            observer.getFollowingCountSucceeded(count);
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get following count";
        }
    }

    private class IsFollowerHandler extends BackgroundTaskHandler<IsFollowerObserver> {
        public IsFollowerHandler(IsFollowerObserver observer) {
            super(observer);
        }
        @Override
        protected void handleSuccessMessage(Message msg) {
            boolean isFollower = msg.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
            observer.isFollowerSucceeded(isFollower);
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to determine following relationship";
        }
    }
}
