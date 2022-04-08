package edu.byu.cs.tweeter.client.model.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowersCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.util.FakeData;

public class ServerFacadeTests {
    private static final String REGISTER_URL = "/register";
    private static final String GET_FOLLOWERS_URL = "/followers";
    private static final String FOLLOWERS_COUNT_URL = "/followersCount";
    private static final String FOLLOWING_COUNT_URL = "/followingCount";

    private ServerFacade serverFacade = new ServerFacade();
    private FakeData fakeData = new FakeData();;

    @Test
    public void registerIntegrationTest() throws IOException, TweeterRemoteException {
        RegisterRequest registerRequest = new RegisterRequest("Jimmy", "John", "@Jim", "password", "image");
        RegisterResponse expectedResponse = new RegisterResponse(fakeData.getFirstUser(), fakeData.getAuthToken());
        RegisterResponse actualResponse = serverFacade.register(registerRequest, REGISTER_URL);

        Assertions.assertEquals(expectedResponse, actualResponse);
        Assertions.assertEquals(expectedResponse.getUser(), actualResponse.getUser());
        Assertions.assertEquals(expectedResponse.getAuthToken(), actualResponse.getAuthToken());
        Assertions.assertTrue(actualResponse.isSuccess());
        Assertions.assertNull(actualResponse.getMessage());
    }

    @Test
    public void getFollowersIntegrationTest() throws IOException, TweeterRemoteException {
        FollowersRequest followersRequest = new FollowersRequest(fakeData.getAuthToken(), "@jim", 10, "");
        List<User> expectedUsers = fakeData.getFakeUsers().subList(0, 10);
        FollowersResponse expectedResponse = new FollowersResponse(expectedUsers, true);
        FollowersResponse actualResponse = serverFacade.getFollowers(followersRequest, GET_FOLLOWERS_URL);

        Assertions.assertEquals(expectedResponse, actualResponse);
        Assertions.assertEquals(expectedResponse.getFollowers(), actualResponse.getFollowers());
        Assertions.assertEquals(expectedResponse.getHasMorePages(), actualResponse.getHasMorePages());
        Assertions.assertTrue(actualResponse.isSuccess());
        Assertions.assertNull(actualResponse.getMessage());

        for (int i = 0; i < 2; i++) {
            String lastUserAlias = expectedUsers.get(9).getAlias();
            if (i == 0) {
                expectedUsers = fakeData.getFakeUsers().subList(10, 20);
                expectedResponse = new FollowersResponse(expectedUsers, true);
            } else {
                expectedUsers = fakeData.getFakeUsers().subList(20, 21);
                expectedResponse = new FollowersResponse(expectedUsers, false);
            }
            followersRequest = new FollowersRequest(fakeData.getAuthToken(), "@jim", 10, lastUserAlias);
            actualResponse = serverFacade.getFollowers(followersRequest, GET_FOLLOWERS_URL);

            Assertions.assertEquals(expectedResponse, actualResponse);
            Assertions.assertEquals(expectedResponse.getFollowers(), actualResponse.getFollowers());
            Assertions.assertEquals(expectedResponse.getHasMorePages(), actualResponse.getHasMorePages());
            Assertions.assertTrue(actualResponse.isSuccess());
            Assertions.assertNull(actualResponse.getMessage());
        }

    }

    @Test
    public void getFollowersCountIntegrationTest() throws IOException, TweeterRemoteException {
        FollowersCountRequest followersCountRequest = new FollowersCountRequest(fakeData.getFirstUser(), fakeData.getAuthToken());
        FollowersCountResponse expectedResponse = new FollowersCountResponse(20);
        FollowersCountResponse actualResponse = serverFacade.getFollowersCount(followersCountRequest, FOLLOWERS_COUNT_URL);

        Assertions.assertEquals(expectedResponse, actualResponse);
        Assertions.assertEquals(expectedResponse.getCount(), actualResponse.getCount());
        Assertions.assertTrue(actualResponse.isSuccess());
        Assertions.assertNull(actualResponse.getMessage());
    }

    @Test
    public void getFollowingCountIntegrationTest() throws IOException, TweeterRemoteException {
        FollowingCountRequest followingCountRequest = new FollowingCountRequest(fakeData.getFirstUser(), fakeData.getAuthToken());
        FollowingCountResponse expectedResponse = new FollowingCountResponse(20);
        FollowingCountResponse actualResponse = serverFacade.getFollowingCount(followingCountRequest, FOLLOWING_COUNT_URL);

        Assertions.assertEquals(expectedResponse, actualResponse);
        Assertions.assertEquals(expectedResponse.getCount(), actualResponse.getCount());
        Assertions.assertTrue(actualResponse.isSuccess());
        Assertions.assertNull(actualResponse.getMessage());
    }
}
