package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.request.UserRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.model.net.response.UserResponse;
import edu.byu.cs.tweeter.server.dao.factories.DAOFactory;
import edu.byu.cs.tweeter.server.lambda.HandlerConfiguration;
import edu.byu.cs.tweeter.server.util.FakeData;

public class UserService {
    DAOFactory daoFactory;
    public UserService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public LoginResponse login(LoginRequest request) {
        try {
            User user = daoFactory.getUserDAO().login(request.getUsername(), request.getPassword());
            AuthToken authToken = daoFactory.getAuthDAO().generateAuthToken(request.getUsername());
            return new LoginResponse(user, authToken);
        } catch (Exception ex) {
            return new LoginResponse(ex.getMessage());
        }
    }

    /*public static void main(String[] args) {
        AuthToken authToken = new AuthToken("d4691f2f-32aa-421d-804d-e61ce656f134", "2021/12/03 08:37:50");
        UserService userService = new UserService(HandlerConfiguration.getInstance().getFactory());
        boolean answer = userService.validate(authToken);
        return;
    }*/

    public boolean validate(AuthToken authToken) {
        return daoFactory.getAuthDAO().validate(authToken);
    }

    public RegisterResponse register(RegisterRequest request) {
        try {
            try {
                User user = daoFactory.getUserDAO().getUserByAlias(request.getAlias());;
                if (user != null) {
                    throw new RuntimeException("[BadRequest] Alias is already taken");
                }
            } catch(Exception ex) {
                if (ex.getMessage().equals("[BadRequest] Alias is already taken")) {
                    throw new RuntimeException("[BadRequest] Alias is already taken");
                } else if (!ex.getMessage().equals("[BadRequest] Invalid alias")) {
                    throw new RuntimeException("[InternalServerError] Problem verifying if alias is taken");
                }
            }
            String imageURL = daoFactory.getImageDAO().upload(request.getAlias(), request.getImageBytesBase64());
            User user = daoFactory.getUserDAO().register(request.getFirstName(), request.getLastName(),
                    request.getAlias(), request.getPassword(), request.getImageBytesBase64(), imageURL);
            AuthToken authToken = daoFactory.getAuthDAO().generateAuthToken(user.getAlias());
            return new RegisterResponse(user, authToken);
        } catch(Exception ex) {
            return new RegisterResponse(ex.getMessage());
        }
    }

    public LogoutResponse logout(LogoutRequest request) {
        try {
            if (validate(request.getAuthToken())) {
                daoFactory.getAuthDAO().deleteAuthToken(request.getAuthToken());
                return new LogoutResponse(true);
            } else {
                throw new RuntimeException("[InternalServerError] Could not Authenticate");
            }
        } catch(Exception exception) {
            return new LogoutResponse(exception.getMessage());
        }

    }

    public UserResponse getUser(UserRequest request) {
        if (validate(request.getAuthToken())) {
            User user = daoFactory.getUserDAO().getUserByAlias(request.getAlias());
            return new UserResponse(user);
        } else {
            throw new RuntimeException("[InternalServerError] Could not Authenticate");
        }
    }

    /**
     * Returns the dummy user to be returned by the login operation.
     * This is written as a separate method to allow mocking of the dummy user.
     *
     * @return a dummy user.
     */
    User getDummyUser() {
        return getFakeData().getFirstUser();
    }

    /**
     * Returns the dummy auth token to be returned by the login operation.
     * This is written as a separate method to allow mocking of the dummy auth token.
     *
     * @return a dummy auth token.
     */
    AuthToken getDummyAuthToken() {
        return getFakeData().getAuthToken();
    }

    /**
     * Returns the {@link FakeData} object used to generate dummy users and auth tokens.
     * This is written as a separate method to allow mocking of the {@link FakeData}.
     *
     * @return a {@link FakeData} instance.
     */
    FakeData getFakeData() {
        return new FakeData();
    }

}
