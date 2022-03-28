package edu.byu.cs.tweeter.server.dao.factories;

import edu.byu.cs.tweeter.server.dao.AuthDAO;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.dao.ImageDAO;
import edu.byu.cs.tweeter.server.dao.PostStatusQueueClient;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.UpdateFeedQueueClient;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public interface DAOFactory {
    public UserDAO getUserDAO();
    public FollowDAO getFollowDAO();
    public AuthDAO getAuthDAO();
    public FeedDAO getFeedDAO();
    public StoryDAO getStoryDAO();
    public ImageDAO getImageDAO();
    public PostStatusQueueClient getPostStatusQueueClient();
    public UpdateFeedQueueClient getUpdateFeedQueueClient();
}
