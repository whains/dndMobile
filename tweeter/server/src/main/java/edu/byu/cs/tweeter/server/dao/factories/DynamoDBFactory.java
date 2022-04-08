package edu.byu.cs.tweeter.server.dao.factories;

import edu.byu.cs.tweeter.server.dao.AuthDAO;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.ImageDAO;
import edu.byu.cs.tweeter.server.dao.PostStatusQueueClient;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.UpdateFeedQueueClient;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoAuthDAO;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoFeedDAO;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoFollowDAO;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoStoryDAO;
import edu.byu.cs.tweeter.server.dao.dynamo.DynamoUserDAO;
import edu.byu.cs.tweeter.server.dao.dynamo.S3DAO;
import edu.byu.cs.tweeter.server.dao.dynamo.SQSPostStatusQueueClient;
import edu.byu.cs.tweeter.server.dao.dynamo.SQSUpdateFeedQueueClient;

public class DynamoDBFactory implements DAOFactory{
    @Override
    public UserDAO getUserDAO() {
        return new DynamoUserDAO();
    }

    @Override
    public FollowDAO getFollowDAO() {
        return new DynamoFollowDAO();
    }

    @Override
    public AuthDAO getAuthDAO() {
        return new DynamoAuthDAO();
    }

    @Override
    public FeedDAO getFeedDAO() {
        return new DynamoFeedDAO();
    }

    @Override
    public StoryDAO getStoryDAO() {
        return new DynamoStoryDAO();
    }

    @Override
    public ImageDAO getImageDAO() {
        return new S3DAO();
    }

    @Override
    public PostStatusQueueClient getPostStatusQueueClient() {
        return new SQSPostStatusQueueClient();
    }

    @Override
    public UpdateFeedQueueClient getUpdateFeedQueueClient() {
        return new SQSUpdateFeedQueueClient();
    }
}
