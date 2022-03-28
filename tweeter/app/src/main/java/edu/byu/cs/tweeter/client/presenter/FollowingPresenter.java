package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowingPresenter extends PagedPresenter<User>  {

    public interface FollowingView extends PagedView<User> { }

    public FollowingPresenter(FollowingView view, AuthToken authToken, User targetUser) {
        super(view, authToken, targetUser);
    }

    @Override
    protected void getItems(AuthToken authToken, User user, int pageSize, User lastItem) {
        new FollowService().getFollowing(authToken, user, pageSize, lastItem, this);
    }
}
