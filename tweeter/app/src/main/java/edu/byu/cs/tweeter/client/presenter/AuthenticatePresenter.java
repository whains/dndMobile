package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class AuthenticatePresenter extends Presenter<AuthenticatePresenter.AuthenticateView> implements UserService.AuthenticateObserver {
    public interface AuthenticateView extends Presenter.View {
        void navigateToUser(User user);
        void clearErrorMessage();
        void clearInfoMessage();
    }

    public AuthenticatePresenter(AuthenticateView view) {
        super(view);
    }

    @Override
    public void authenticateSucceeded(AuthToken authToken, User user) {
        view.navigateToUser(user);
        view.clearErrorMessage();
        view.clearInfoMessage();
        view.displayInfoMessage("Hello " + user.getName());
    }

    @Override
    public void handleFailed(String message) {
        view.displayInfoMessage(message);
    }
}
