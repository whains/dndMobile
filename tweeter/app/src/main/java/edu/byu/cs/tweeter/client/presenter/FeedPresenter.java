package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter extends PagedPresenter<Status> {

    public interface FeedView extends PagedView<Status> {}

    public FeedPresenter(FeedView view, AuthToken authToken, User user) {
        super(view, authToken, user);
    }

    @Override
    protected void getItems(AuthToken authToken, User user, int pageSize, Status lastItem) {
        new StatusService().getFeed(authToken, user, pageSize, lastItem, this);
    }

    public void displayName(String userName) {
        view.displayInfoMessage("You selected '" + userName + "'.");
    }
}
