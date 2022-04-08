package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.StoryRequest;
import edu.byu.cs.tweeter.model.net.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.AuthDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoStoryDAO;
import edu.byu.cs.tweeter.server.dao.factories.DynamoDBFactory;
import edu.byu.cs.tweeter.server.util.FakeData;
import edu.byu.cs.tweeter.server.util.Pair;

public class StatusServiceTest {
    private StatusService statusService;
    private StoryDAO storyDAO;
    private AuthDAO authDAO;
    private DynamoDBFactory dynamoDBFactory;
    private FakeData fakeData;
    private AuthToken authToken;

    @BeforeEach
    void setup() {
        storyDAO = Mockito.mock(DynamoStoryDAO.class);
        authDAO = Mockito.mock(AuthDAO.class);
        dynamoDBFactory = Mockito.mock(DynamoDBFactory.class);
        fakeData = new FakeData();
        authToken = new AuthToken();
        Pair<List<Status>, Boolean> statusList1 = fakeData.getPageOfStatus(null, 10);
        Pair<List<Status>, Boolean> statusList2 = fakeData.getPageOfStatus(statusList1.getFirst().get(9), 10);
        Pair<List<Status>, Boolean> statusList3 = fakeData.getPageOfStatus(statusList2.getFirst().get(9), 10);
        Pair<List<Status>, Boolean> statusList4 = fakeData.getPageOfStatus(statusList3.getFirst().get(9), 10);

        Mockito.doReturn(true).when(authDAO).validate(authToken);
        Mockito.doReturn(storyDAO).when(dynamoDBFactory).getStoryDAO();
        Mockito.doReturn(authDAO).when(dynamoDBFactory).getAuthDAO();
        Mockito.doReturn(statusList1).when(storyDAO).getStory("@jimmy", 10, null);
        Mockito.doReturn(statusList2).when(storyDAO).getStory("@jimmy", 10, statusList1.getFirst().get(9));
        Mockito.doReturn(statusList3).when(storyDAO).getStory("@jimmy", 10, statusList2.getFirst().get(9));
        Mockito.doReturn(statusList4).when(storyDAO).getStory("@jimmy", 10, statusList3.getFirst().get(9));
        Mockito.doThrow(RuntimeException.class).when(storyDAO).getStory("@wrongAlias", 10, null);

        statusService = Mockito.spy(new StatusService(dynamoDBFactory));
    }

    @Test
    void should_GetFirstPageOfStoryStatuses() {
        StoryRequest storyRequest = new StoryRequest(authToken, "@jimmy", 10, null);
        StoryResponse storyResponse = statusService.getStory(storyRequest);

        Mockito.verify(statusService).getStory(storyRequest);
        Mockito.verify(statusService, Mockito.times(1)).getStory(Mockito.any());
        Mockito.verify(storyDAO).getStory("@jimmy", 10, null);
        Mockito.verify(storyDAO, Mockito.times(1)).getStory(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
        Mockito.verify(authDAO).validate(authToken);
        Mockito.verify(authDAO, Mockito.times(1)).validate(Mockito.any());
        Mockito.verify(dynamoDBFactory, Mockito.times(1)).getAuthDAO();
        Mockito.verify(dynamoDBFactory, Mockito.times(1)).getStoryDAO();

        Pair<List<Status>, Boolean> expected = fakeData.getPageOfStatus(null, 10);
        Assertions.assertEquals(expected.getFirst().size(), storyResponse.getStatuses().size());
        for (int i = 0; i < expected.getFirst().size(); i++) {
            Assertions.assertEquals(expected.getFirst().get(i), storyResponse.getStatuses().get(i));
        }

        Assertions.assertEquals(expected.getFirst(), storyResponse.getStatuses());
        Assertions.assertEquals(expected.getSecond(), storyResponse.getHasMorePages());
        Assertions.assertTrue(storyResponse.getHasMorePages());
        Assertions.assertNull(storyResponse.getMessage());
    }

    @Test
    void should_GetFirstAndSubsequentPagesOfStatuses() {
        StoryRequest storyRequest = new StoryRequest(authToken, "@jimmy", 10, null);
        StoryResponse storyResponse = statusService.getStory(storyRequest);

        Mockito.verify(statusService).getStory(storyRequest);
        Mockito.verify(statusService, Mockito.times(1)).getStory(Mockito.any());
        Mockito.verify(storyDAO).getStory("@jimmy", 10, null);
        Mockito.verify(storyDAO, Mockito.times(1)).getStory(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
        Mockito.verify(authDAO).validate(authToken);
        Mockito.verify(authDAO, Mockito.times(1)).validate(Mockito.any());
        Mockito.verify(dynamoDBFactory, Mockito.times(1)).getAuthDAO();
        Mockito.verify(dynamoDBFactory, Mockito.times(1)).getStoryDAO();

        Pair<List<Status>, Boolean> expected = fakeData.getPageOfStatus(null, 10);
        Assertions.assertEquals(expected.getFirst().size(), storyResponse.getStatuses().size());
        for (int i = 0; i < expected.getFirst().size(); i++) {
            Assertions.assertEquals(expected.getFirst().get(i), storyResponse.getStatuses().get(i));
        }

        Assertions.assertEquals(expected.getFirst(), storyResponse.getStatuses());
        Assertions.assertEquals(expected.getSecond(), storyResponse.getHasMorePages());
        Assertions.assertTrue(storyResponse.getHasMorePages());
        Assertions.assertNull(storyResponse.getMessage());

        Status oldLastStatus = storyResponse.getStatuses().get(9);
        storyRequest = new StoryRequest(authToken, "@jimmy", 10, oldLastStatus);
        storyResponse = statusService.getStory(storyRequest);

        Mockito.verify(statusService).getStory(storyRequest);
        Mockito.verify(statusService, Mockito.times(2)).getStory(Mockito.any());
        Mockito.verify(storyDAO).getStory("@jimmy", 10, oldLastStatus);
        Mockito.verify(storyDAO, Mockito.times(2)).getStory(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
        Mockito.verify(authDAO, Mockito.times(2)).validate(Mockito.any());
        Mockito.verify(dynamoDBFactory, Mockito.times(2)).getAuthDAO();
        Mockito.verify(dynamoDBFactory, Mockito.times(2)).getStoryDAO();

        expected = fakeData.getPageOfStatus(expected.getFirst().get(9), 10);
        Assertions.assertEquals(expected.getFirst().size(), storyResponse.getStatuses().size());
        for (int i = 0; i < expected.getFirst().size(); i++) {
            Assertions.assertEquals(expected.getFirst().get(i), storyResponse.getStatuses().get(i));
        }

        Assertions.assertEquals(expected.getFirst(), storyResponse.getStatuses());
        Assertions.assertEquals(expected.getSecond(), storyResponse.getHasMorePages());
        Assertions.assertTrue(storyResponse.getHasMorePages());
        Assertions.assertNull(storyResponse.getMessage());

        oldLastStatus = storyResponse.getStatuses().get(9);
        storyRequest = new StoryRequest(authToken, "@jimmy", 10, oldLastStatus);
        storyResponse = statusService.getStory(storyRequest);

        Mockito.verify(statusService).getStory(storyRequest);
        Mockito.verify(statusService, Mockito.times(3)).getStory(Mockito.any());
        Mockito.verify(storyDAO).getStory("@jimmy", 10, oldLastStatus);
        Mockito.verify(storyDAO, Mockito.times(3)).getStory(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
        Mockito.verify(authDAO, Mockito.times(3)).validate(Mockito.any());
        Mockito.verify(dynamoDBFactory, Mockito.times(3)).getAuthDAO();
        Mockito.verify(dynamoDBFactory, Mockito.times(3)).getStoryDAO();

        expected = fakeData.getPageOfStatus(expected.getFirst().get(9), 10);
        Assertions.assertEquals(expected.getFirst().size(), storyResponse.getStatuses().size());
        for (int i = 0; i < expected.getFirst().size(); i++) {
            Assertions.assertEquals(expected.getFirst().get(i), storyResponse.getStatuses().get(i));
        }

        Assertions.assertEquals(expected.getFirst(), storyResponse.getStatuses());
        Assertions.assertEquals(expected.getSecond(), storyResponse.getHasMorePages());
        Assertions.assertTrue(storyResponse.getHasMorePages());
        Assertions.assertNull(storyResponse.getMessage());

        oldLastStatus = storyResponse.getStatuses().get(9);
        storyRequest = new StoryRequest(authToken, "@jimmy", 10, oldLastStatus);
        storyResponse = statusService.getStory(storyRequest);

        Mockito.verify(statusService).getStory(storyRequest);
        Mockito.verify(statusService, Mockito.times(4)).getStory(Mockito.any());
        Mockito.verify(storyDAO).getStory("@jimmy", 10, oldLastStatus);
        Mockito.verify(storyDAO, Mockito.times(4)).getStory(Mockito.anyString(), Mockito.anyInt(), Mockito.any());
        Mockito.verify(authDAO, Mockito.times(4)).validate(Mockito.any());
        Mockito.verify(dynamoDBFactory, Mockito.times(4)).getAuthDAO();
        Mockito.verify(dynamoDBFactory, Mockito.times(4)).getStoryDAO();

        expected = fakeData.getPageOfStatus(expected.getFirst().get(9), 10);
        Assertions.assertEquals(expected.getFirst().size(), storyResponse.getStatuses().size());
        for (int i = 0; i < expected.getFirst().size(); i++) {
            Assertions.assertEquals(expected.getFirst().get(i), storyResponse.getStatuses().get(i));
        }

        Assertions.assertEquals(expected.getFirst(), storyResponse.getStatuses());
        Assertions.assertEquals(expected.getSecond(), storyResponse.getHasMorePages());
        Assertions.assertTrue(storyResponse.getHasMorePages());
        Assertions.assertNull(storyResponse.getMessage());
    }

    @Test
    void should_throwRuntimeException_when_givenInvalidAuthToken() {
        AuthToken authToken2 = new AuthToken("Invalid");
        StoryRequest storyRequest = new StoryRequest(authToken2, "@jimmy", 10, null);
        Assertions.assertThrows(RuntimeException.class, () -> statusService.getStory(storyRequest));
    }

    @Test
    void should_throwRuntimeException_when_givenWrongAlias() {
        StoryRequest storyRequest = new StoryRequest(authToken, "@wrongAlias", 10, null);
        Assertions.assertThrows(RuntimeException.class, () -> statusService.getStory(storyRequest));
    }
}
