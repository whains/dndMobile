package edu.byu.cs.tweeter.client.model.service;

import android.os.Message;

import edu.byu.cs.tweeter.client.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.executor.Executor;
import edu.byu.cs.tweeter.client.model.service.handler.BackgroundTaskHandler;
import edu.byu.cs.tweeter.client.model.service.handler.PagedHandler;
import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService {
    public interface PostStatusObserver extends ServiceObserver {
        void postStatusSucceeded(String message);
    }

    public void getFeed(AuthToken authToken, User user, int limit, Status lastStatus, PagedObserver observer) {
        Executor.execute(new GetFeedTask(authToken, user, limit, lastStatus, new GetFeedHandler(observer)));
    }

    public void getStory(AuthToken authToken, User user, int limit, Status lastStatus, PagedObserver observer){
        Executor.execute(new GetStoryTask(authToken, user, limit, lastStatus, new GetStoryHandler(observer)));
    }

    public void postStatus(AuthToken authToken, Status newStatus, String alias, PostStatusObserver observer) {
        Executor.execute(new PostStatusTask(authToken, newStatus, alias, new PostStatusHandler(observer)));
    }

    private class GetFeedHandler extends PagedHandler<Status> {
        public GetFeedHandler(PagedObserver<Status> observer) {
            super(observer);
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get feed";
        }
    }

    private class GetStoryHandler extends PagedHandler<Status> {
        public  GetStoryHandler(PagedObserver<Status> observer) {
            super(observer);
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to get story";
        }
    }

    private class PostStatusHandler extends BackgroundTaskHandler<PostStatusObserver> {
        public PostStatusHandler(PostStatusObserver observer) {
            super(observer);
        }
        @Override
        protected void handleSuccessMessage(Message msg) {
            observer.postStatusSucceeded("Successfully Posted!");
        }
        @Override
        protected String getFailedMessagePrefix() {
            return "Failed to post status";
        }
    }
}
