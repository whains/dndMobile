package edu.byu.cs.tweeter.client.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

/**
 * IMPORTANT: THESE TESTS WERE FOR MILESTONE 2 AND THUS WITH THE CHANGES THEY NO LONGER WORK!
 *
 */
public class MainPresenterTest {

    private MainPresenter.MainView mockMainView;
    private StatusService mockStatusService;
    private MainPresenter mainPresenterSpy;
    private AuthToken authToken;
    private String examplePost = "This is an example post! @amy https://byu.edu";
    private Status testStatus;
    private String alias = "@jim";

    @Before
    public void setup() throws MalformedURLException, ParseException {
        mockMainView = Mockito.mock(MainPresenter.MainView.class);
        mockStatusService = Mockito.mock(StatusService.class);
        authToken = new AuthToken();
        mainPresenterSpy = Mockito.spy(new MainPresenter(mockMainView, authToken));

        List<String> urls = new ArrayList<>();
        urls.add("https://byu.edu");
        List<String> mentions = new ArrayList<>();
        mentions.add("@amy");
        String formattedDateTime;
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");
        formattedDateTime = statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));

        //testStatus = new Status(examplePost, Cache.getInstance().getCurrUser(), formattedDateTime, urls,mentions);
        alias = Cache.getInstance().getCurrUser().getAlias();

        Mockito.doReturn(mockStatusService).when(mainPresenterSpy).getStatusService();
        //Mockito.doReturn(alias).when(Cache.getInstance().getCurrUser().getAlias());
    }

    @Test
    public void testPostStatus_postStatusSucceeded() throws MalformedURLException, ParseException {
        Answer<Void> postStatusSucceededAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.PostStatusObserver observer = invocation.getArgument(2);
                observer.postStatusSucceeded("Successfully Posted!");
                return null;
            }
        };
        Mockito.doAnswer(postStatusSucceededAnswer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        mainPresenterSpy.postStatus(examplePost);

        Mockito.verify(mockMainView).displayInfoMessage("Posting Status...");
        Mockito.verify(mainPresenterSpy).createStatus(examplePost);
        Mockito.verify(mockMainView).displayInfoMessage("Successfully Posted!");

        /**
         * This test checks that the parameters passed to the StatusService are correct by
         * checking the authToken and mainPresenterSpy, and then in setup it creates
         * the testStatus Status to compare to the one that is generated during the postStatus
         * method in MainPresenter. Hence, it checks each of the paramters and will fail if
         * one of them is not correct.
         */
        Mockito.verify(mockStatusService).postStatus(authToken, testStatus, alias, mainPresenterSpy);

    }

    @Test
    public void testPostStatus_postStatusFailed() throws MalformedURLException, ParseException {
        Answer<Void> postStatusFailedAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.PostStatusObserver observer = invocation.getArgument(2);
                observer.handleFailed("Failed to post status: Post Status Failed Message");
                return null;
            }
        };
        Mockito.doAnswer(postStatusFailedAnswer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        mainPresenterSpy.postStatus(examplePost);

        Mockito.verify(mockMainView).displayInfoMessage("Posting Status...");
        Mockito.verify(mainPresenterSpy).createStatus(examplePost);
        Mockito.verify(mockStatusService).postStatus(authToken, testStatus, alias, mainPresenterSpy);
        Mockito.verify(mockMainView).displayErrorMessage("Failed to post status: Post Status Failed Message");
    }

    @Test
    public void testPostStatus_postStatusThrewException() throws MalformedURLException, ParseException {
        Answer<Void> postStatusThrewExceptionAnswer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.PostStatusObserver observer = invocation.getArgument(2);
                observer.handleFailed("Failed to post status because of exception: Exception Example");
                return null;
            }
        };
        Mockito.doAnswer(postStatusThrewExceptionAnswer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        mainPresenterSpy.postStatus(examplePost);

        Mockito.verify(mockMainView).displayInfoMessage("Posting Status...");
        Mockito.verify(mainPresenterSpy).createStatus(examplePost);
        Mockito.verify(mockStatusService).postStatus(authToken, testStatus, alias, mainPresenterSpy);
        Mockito.verify(mockMainView).displayErrorMessage("Failed to post status because of exception: Exception Example");
    }
}
