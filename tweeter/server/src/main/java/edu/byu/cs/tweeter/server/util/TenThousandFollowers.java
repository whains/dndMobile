package edu.byu.cs.tweeter.server.util;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.lambda.HandlerConfiguration;

public class TenThousandFollowers {
    public static void main(String[] args) throws Exception {
        int NUM_USERS = 10000;
        String FOLLOW_TARGET = "@King";

        UserDAO userDAO = HandlerConfiguration.getInstance().getFactory().getUserDAO();
        FollowDAO followDAO = HandlerConfiguration.getInstance().getFactory().getFollowDAO();

        List<User> users = new ArrayList<>();
        String imageURL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

        for (int i = 1; i <= NUM_USERS; i++) {
            String firstName = "Peasant";
            String lastName = String.valueOf(i);
            String alias = "@peasant" + i;

            User user = new User(firstName, lastName, alias, imageURL);
            users.add(user);
        }

        //userDAO.batchUserWrite(users);
        //followDAO.batchFollowWrite(FOLLOW_TARGET, users);
    }
}