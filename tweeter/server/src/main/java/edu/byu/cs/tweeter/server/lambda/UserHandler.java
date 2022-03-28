package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.UserRequest;
import edu.byu.cs.tweeter.model.net.response.UserResponse;
import edu.byu.cs.tweeter.server.service.UserService;

public class UserHandler implements RequestHandler<UserRequest, UserResponse> {
    @Override
    public UserResponse handleRequest(UserRequest request, Context context) {
        UserService userService = new UserService(HandlerConfiguration.getInstance().getFactory());
        return userService.getUser(request);
    }
}
