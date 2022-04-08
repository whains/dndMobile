package edu.byu.cs.tweeter.client.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class IntegrationTest {
    private AuthenticatePresenter.AuthenticateView loginViewMock;
    private MainPresenter.MainView mainViewMock;
    private LoginPresenter loginPresenterSpy;
    private MainPresenter mainPresenterSpy;
    private StoryPresenter.StoryView storyViewMock;
    private StoryPresenter storyPresenterSpy;
    private final String testPost = "This is the test post! Number 2. @Jewels https://byu.edu";
    private final String alias = "@Wesley";
    private final String password = "pass";
    private CountDownLatch countDownLatch;

    @Before
    public void setup() {
        loginViewMock = Mockito.mock(AuthenticatePresenter.AuthenticateView.class);
        loginPresenterSpy = Mockito.spy(new LoginPresenter(loginViewMock));
        mainViewMock = Mockito.mock(MainPresenter.MainView.class);
        storyViewMock = Mockito.mock(StoryPresenter.StoryView.class);

        resetCountDownLatch();

        Answer<Void> loggedInAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                invocation.callRealMethod();
                decrementCountDownLatch();
                return null;
            }
        };

        Mockito.doAnswer(loggedInAnswer).when(loginPresenterSpy)
                .authenticateSucceeded(Mockito.any(), Mockito.any());

    }

    private void resetCountDownLatch() {
        countDownLatch = new CountDownLatch(1);
    }

    private void decrementCountDownLatch() {
        countDownLatch.countDown();
    }

    private void awaitCountDownLatch() throws InterruptedException {
        countDownLatch.await();
        resetCountDownLatch();
    }

    @Test
    public void should_loginUserPostStatusAndGetStatusInUserStory() throws InterruptedException, MalformedURLException, ParseException {
        loginPresenterSpy.login(alias, password);
        awaitCountDownLatch();

        User correctUser = new User("Wesley", "Fairbanks",
                alias, "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        User receivedUser = Cache.getInstance().getCurrUser();
        AuthToken authToken = Cache.getInstance().getCurrUserAuthToken();

        assertEquals(correctUser, receivedUser);
        assertEquals(correctUser.getName(), receivedUser.getName());
        assertEquals(correctUser.getAlias(), receivedUser.getAlias());
        assertEquals(correctUser.getImageUrl(), receivedUser.getImageUrl());

        Mockito.verify(loginPresenterSpy).login(alias, password);
        Mockito.verify(loginPresenterSpy, Mockito.times(1)).login(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(loginPresenterSpy).validateLogin(alias, password);
        Mockito.verify(loginPresenterSpy, Mockito.times(1)).validateLogin(Mockito.anyString(), Mockito.anyString());

        Mockito.verify(loginPresenterSpy).authenticateSucceeded(authToken, correctUser);
        Mockito.verify(loginPresenterSpy, Mockito.times(1)).authenticateSucceeded(Mockito.any(), Mockito.any());
        Mockito.verify(loginViewMock).navigateToUser(correctUser);
        Mockito.verify(loginViewMock, Mockito.times(1)).navigateToUser(Mockito.any());
        Mockito.verify(loginViewMock, Mockito.times(2)).clearErrorMessage();
        Mockito.verify(loginViewMock, Mockito.times(2)).clearInfoMessage();
        Mockito.verify(loginViewMock).displayInfoMessage("Logging In...");
        Mockito.verify(loginViewMock).displayInfoMessage("Hello " + correctUser.getName());

        mainPresenterSpy = Mockito.spy(new MainPresenter(mainViewMock, authToken));
        resetCountDownLatch();
        Answer<Void> postedStatusAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                invocation.callRealMethod();
                decrementCountDownLatch();
                return null;
            }
        };

        Mockito.doAnswer(postedStatusAnswer).when(mainPresenterSpy).postStatusSucceeded(Mockito.anyString());

        Status expectedStatus = mainPresenterSpy.createStatus(testPost);
        Mockito.doReturn(expectedStatus).when(mainPresenterSpy).createStatus(testPost);

        mainPresenterSpy.postStatus(testPost);
        awaitCountDownLatch();

        Mockito.verify(mainPresenterSpy).postStatus(testPost);
        Mockito.verify(mainPresenterSpy, Mockito.times(1)).postStatus(Mockito.any());
        Mockito.verify(mainPresenterSpy, Mockito.times(2)).createStatus(testPost);
        Mockito.verify(mainPresenterSpy, Mockito.times(2)).createStatus(Mockito.any());

        Mockito.verify(mainPresenterSpy).postStatusSucceeded("Successfully Posted!");
        Mockito.verify(mainPresenterSpy, Mockito.times(1)).postStatusSucceeded(Mockito.anyString());
        Mockito.verify(mainViewMock).displayInfoMessage("Posting Status...");
        Mockito.verify(mainViewMock).displayInfoMessage("Successfully Posted!");
        Mockito.verify(mainViewMock, Mockito.times(2)).displayInfoMessage(Mockito.anyString());

        storyPresenterSpy = Mockito.spy(new StoryPresenter(storyViewMock, authToken, receivedUser));

        resetCountDownLatch();
        Answer<Void> getStoryAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                invocation.callRealMethod();
                decrementCountDownLatch();
                return null;
            }
        };
        Mockito.doAnswer(getStoryAnswer).when(storyPresenterSpy).getItemsSucceeded(Mockito.any(), Mockito.anyBoolean());

        storyPresenterSpy.loadMoreItems();
        awaitCountDownLatch();

        Mockito.verify(storyPresenterSpy, Mockito.times(1)).loadMoreItems();
        Mockito.verify(storyPresenterSpy).getItems(authToken, correctUser, 10, null);
        Mockito.verify(storyPresenterSpy, Mockito.times(1))
                .getItems(Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.any());

        Mockito.verify(storyPresenterSpy, Mockito.times(1)).getItemsSucceeded(Mockito.any(), Mockito.anyBoolean());
        assertTrue(storyPresenterSpy.hasMorePages);
        assertFalse(storyPresenterSpy.isLoading);
        assertEquals(10, storyPresenterSpy.items.size());
        assertNotNull(storyPresenterSpy.lastItem);
        assertEquals(expectedStatus, storyPresenterSpy.items.get(0));
        assertEquals(expectedStatus.getPost(), storyPresenterSpy.items.get(0).getPost());
        assertEquals(expectedStatus.getDate(), storyPresenterSpy.items.get(0).getDate());
        assertEquals(expectedStatus.getDatetime(), storyPresenterSpy.items.get(0).getDatetime());
        assertEquals(expectedStatus.getMentions(), storyPresenterSpy.items.get(0).getMentions());
        assertEquals(expectedStatus.getUrls(), storyPresenterSpy.items.get(0).getUrls());
        assertEquals(expectedStatus.getTimeStamp(), storyPresenterSpy.items.get(0).getTimeStamp());
        assertEquals(expectedStatus.getUser(), storyPresenterSpy.items.get(0).getUser());
        assertEquals(expectedStatus.getUser().getAlias(), storyPresenterSpy.items.get(0).getUser().getAlias());
        assertEquals(expectedStatus.getUser().getName(), storyPresenterSpy.items.get(0).getUser().getName());
        assertEquals(expectedStatus.getUser().getImageUrl(), storyPresenterSpy.items.get(0).getUser().getImageUrl());
    }
}
