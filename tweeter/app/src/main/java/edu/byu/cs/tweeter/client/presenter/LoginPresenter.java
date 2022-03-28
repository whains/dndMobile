package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;

public class LoginPresenter extends AuthenticatePresenter {

    public LoginPresenter(AuthenticateView view) {
        super(view);
    }

    public void login(String alias, String password) {
        try {
            view.clearErrorMessage();
            view.clearInfoMessage();
            validateLogin(alias, password);
            view.displayInfoMessage("Logging In...");
            new UserService().login(alias, password, this);
        } catch (Exception e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    public void validateLogin(String alias, String password) {
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }
}
