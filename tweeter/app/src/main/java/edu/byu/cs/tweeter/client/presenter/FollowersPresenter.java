package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowersPresenter extends PagedPresenter<User>{
    public interface FollowersView extends PagedView<User> {}

    public FollowersPresenter(FollowersView view, AuthToken authToken, User targetUser) {
        super(view, authToken, targetUser);
    }

    @Override
    protected void getItems(AuthToken authToken, User user, int pageSize, User lastItem) {
        new FollowService().getFollowers(authToken, user, pageSize, lastItem, this);
    }
}
