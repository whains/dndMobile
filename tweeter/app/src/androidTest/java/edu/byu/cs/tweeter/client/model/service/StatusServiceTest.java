package edu.byu.cs.tweeter.client.model.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.presenter.StoryPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class StatusServiceTest {
    private ServerFacade serverFacadeSpy;
    private StatusService statusServiceSpy;
    private StoryPresenter storyPresenterSpy;
    private StoryPresenter.StoryView storyViewMock;
    private CountDownLatch countDownLatch;

    private FakeData fakeData = new FakeData();
    private AuthToken authToken;
    private User user;

    @Before
    public void setup() throws MalformedURLException {
        authToken = fakeData.getAuthToken();
        user = fakeData.getFirstUser();
        storyViewMock = Mockito.mock(StoryPresenter.StoryView.class);
        serverFacadeSpy = Mockito.spy(ServerFacade.class);

        StoryPresenter storyPresenter = new StoryPresenter(storyViewMock, authToken, user);
        storyPresenterSpy = Mockito.spy(storyPresenter);

        statusServiceSpy = Mockito.spy(StatusService.class);
        resetCountDownLatch();

        Answer<Void> storyRetrievedAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                invocation.callRealMethod();
                decrementCountDownLatch();
                return null;
            }
        };

        Mockito.doAnswer(storyRetrievedAnswer).when(storyPresenterSpy)
                .getItemsSucceeded(Mockito.any(), Mockito.anyBoolean());
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
    public void testGetStoryOnce() throws InterruptedException, MalformedURLException {
        Status status = new Status();
        statusServiceSpy.getStory(authToken, user, 10, status, storyPresenterSpy);
        awaitCountDownLatch();

        assertTrue(storyPresenterSpy.hasMorePages);
        assertFalse(storyPresenterSpy.isLoading);

        Mockito.verify(storyPresenterSpy).getItemsSucceeded(Mockito.any(), Mockito.anyBoolean());
        Mockito.verify(storyPresenterSpy, Mockito.times(1)).getItemsSucceeded(Mockito.any(), Mockito.anyBoolean());
        Mockito.verify(statusServiceSpy, Mockito.times(1)).getStory(authToken, user, 10, status, storyPresenterSpy);
        Mockito.verify(storyViewMock, Mockito.times(1)).addItems(Mockito.any());

        List<Status> expectedStatuses = fakeData.getFakeStatuses().subList(0, 10);
        assertEquals(expectedStatuses.size(), storyPresenterSpy.items.size());
        for (int i = 0; i < expectedStatuses.size(); i++) {
            assertEquals(expectedStatuses.get(i), storyPresenterSpy.items.get(i));
        }

        assertEquals(expectedStatuses.get(9), storyPresenterSpy.lastItem);
    }

    @Test
    public void testGetStoryUntilGotAllStatuses() throws InterruptedException, MalformedURLException {
        Status lastStatus = new Status();
        statusServiceSpy.getStory(authToken, user, 10, lastStatus, storyPresenterSpy);
        awaitCountDownLatch();

        assertTrue(storyPresenterSpy.hasMorePages);
        assertFalse(storyPresenterSpy.isLoading);

        Mockito.verify(storyPresenterSpy, Mockito.times(1)).getItemsSucceeded(Mockito.any(), Mockito.anyBoolean());
        Mockito.verify(statusServiceSpy, Mockito.times(1)).getStory(authToken, user, 10, lastStatus, storyPresenterSpy);
        Mockito.verify(storyViewMock, Mockito.times(1)).addItems(Mockito.any());

        List<Status> expectedStatuses = fakeData.getFakeStatuses().subList(0, 10);
        assertEquals(expectedStatuses.size(), storyPresenterSpy.items.size());
        for (int i = 0; i < expectedStatuses.size(); i++) {
            assertEquals(expectedStatuses.get(i), storyPresenterSpy.items.get(i));
        }

        assertEquals(expectedStatuses.get(9), storyPresenterSpy.lastItem);

        expectedStatuses = fakeData.getFakeStatuses();

        for (int i = 0; i < 4; i++) {
            lastStatus = storyPresenterSpy.lastItem;
            statusServiceSpy.getStory(authToken, user, 10, lastStatus, storyPresenterSpy);
            awaitCountDownLatch();

            if (i < 3) {
                assertTrue(storyPresenterSpy.hasMorePages);
            } else {
                assertFalse(storyPresenterSpy.hasMorePages);
            }
            assertFalse(storyPresenterSpy.isLoading);

            Mockito.verify(storyPresenterSpy, Mockito.times(i + 2)).getItemsSucceeded(Mockito.any(), Mockito.anyBoolean());
            Mockito.verify(statusServiceSpy, Mockito.times(1)).getStory(authToken, user, 10, lastStatus, storyPresenterSpy);
            Mockito.verify(storyViewMock, Mockito.times(i + 2)).addItems(Mockito.any());

            if (i < 3) {
                expectedStatuses = fakeData.getFakeStatuses().subList((10*i + 10), (10 * i + 20));
            } else {
                expectedStatuses = fakeData.getFakeStatuses().subList((10*i + 10), 42);
            }
            assertEquals(expectedStatuses.size(), storyPresenterSpy.items.size());
            for (int j = 0; j < expectedStatuses.size(); j++) {
                assertEquals(expectedStatuses.get(j), storyPresenterSpy.items.get(j));
                if ((j + 1) == expectedStatuses.size()) {
                    assertEquals(expectedStatuses.get(j), storyPresenterSpy.lastItem);
                }
            }
        }
    }
}
