package edu.byu.cs.tweeter.server.service;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;
import edu.byu.cs.tweeter.model.net.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.dao.factories.DAOFactory;
import edu.byu.cs.tweeter.server.util.Pair;


public class FollowService {
    DAOFactory daoFactory;

    public FollowService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public FollowingResponse getFollowees(FollowingRequest request) {
        if (daoFactory.getAuthDAO().validate(request.getAuthToken())) {
            Pair<List<User>, Boolean> pair = daoFactory.getFollowDAO().getFollowees(request.getFollowerAlias(), request.getLimit(), request.getLastFolloweeAlias());
            FollowingResponse  followingResponse = new FollowingResponse(pair.getFirst(), pair.getSecond());
            return followingResponse;
        } else {
            throw new RuntimeException("[InternalServerError] Could not Authenticate");
        }
    }

    public FollowersResponse getFollowers(FollowersRequest request) {
        if (daoFactory.getAuthDAO().validate(request.getAuthToken())) {
            Pair<List<User>, Boolean> pair = daoFactory.getFollowDAO().getFollowers(request.getFolloweeAlias(), request.getLimit(), request.getLastFollowerAlias());
            FollowersResponse  followersResponse = new FollowersResponse(pair.getFirst(), pair.getSecond());
            return followersResponse;
        } else {
            throw new RuntimeException("[InternalServerError] Could not Authenticate");
        }
    }

    public FollowersCountResponse getFollowersCount(FollowersCountRequest request) {
        if (daoFactory.getAuthDAO().validate(request.getAuthToken())) {
            int count = daoFactory.getUserDAO().getFollowersCount(request.getUser().getAlias());
            return new FollowersCountResponse(count);
        } else {
            throw new RuntimeException("[InternalServerError] Could not Authenticate");
        }
    }

    public FollowingCountResponse getFollowingCount(FollowingCountRequest request) {
        if (daoFactory.getAuthDAO().validate(request.getAuthToken())) {
            int count = daoFactory.getUserDAO().getFollowingCount(request.getUser().getAlias());
            return new FollowingCountResponse(count);
        } else {
            throw new RuntimeException("[InternalServerError] Could not Authenticate");
        }
    }

    public FollowResponse follow(FollowRequest request) {
        if (daoFactory.getAuthDAO().validate(request.getAuthToken())) {
            User currentUser = daoFactory.getUserDAO().getUserByAlias(request.getCurrentUserAlias());
            System.out.println(currentUser.toString());
            User followee = daoFactory.getUserDAO().getUserByAlias(request.getFolloweeAlias());
            System.out.println(followee.toString());
            boolean isSuccess = daoFactory.getFollowDAO().follow(request.getCurrentUserAlias(), currentUser, request.getFolloweeAlias(), followee);
            System.out.println(isSuccess);
            if (isSuccess) {
                daoFactory.getUserDAO().updateFollowingCount(request.getCurrentUserAlias(), 1);
                System.out.println("Update one");
                daoFactory.getUserDAO().updateFollowersCount(request.getFolloweeAlias(), 1);
                System.out.println("Update two");
            }
            return new FollowResponse(isSuccess);
        } else {
            throw new RuntimeException("[InternalServerError] Could not Authenticate");
        }
    }

    public UnfollowResponse unfollow(UnfollowRequest request) {
        if (daoFactory.getAuthDAO().validate(request.getAuthToken())) {
            boolean isSuccess = daoFactory.getFollowDAO().unfollow(request.getCurrentUserAlias(), request.getFolloweeAlias());
            System.out.println(isSuccess);
            if (isSuccess) {
                daoFactory.getUserDAO().updateFollowingCount(request.getCurrentUserAlias(), -1);
                System.out.println("Update one");
                daoFactory.getUserDAO().updateFollowersCount(request.getFolloweeAlias(), -1);
                System.out.println("Update two");
            }
            return new UnfollowResponse(isSuccess);
        } else {
            throw new RuntimeException("[InternalServerError] Could not Authenticate");
        }
    }

    public IsFollowerResponse isFollower(IsFollowerRequest request) {
        if (daoFactory.getAuthDAO().validate(request.getAuthToken())) {
            boolean isFollower = daoFactory.getFollowDAO().isFollower(request.getFollowerAlias(), request.getFolloweeAlias());
            return new IsFollowerResponse(isFollower);
        } else {
            throw new RuntimeException("[InternalServerError] Could not Authenticate");
        }
    }
}
