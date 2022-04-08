package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter extends PagedPresenter<Status>{

    public interface StoryView extends PagedView<Status>{}

    public StoryPresenter(StoryView view, AuthToken authToken, User user) {
        super(view, authToken, user);
    }

    @Override
    protected void getItems(AuthToken authToken, User user, int pageSize, Status lastItem) {
        new StatusService().getStory(authToken, user, pageSize, lastItem, this);
    }
}
