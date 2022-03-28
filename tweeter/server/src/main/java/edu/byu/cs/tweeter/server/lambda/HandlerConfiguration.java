package edu.byu.cs.tweeter.server.lambda;

import edu.byu.cs.tweeter.server.dao.factories.DAOFactory;
import edu.byu.cs.tweeter.server.dao.factories.DynamoDBFactory;

public class HandlerConfiguration {
    private static HandlerConfiguration configuration;

    private HandlerConfiguration() {}

    public static HandlerConfiguration getInstance() {
        if (configuration == null) {
            configuration = new HandlerConfiguration();
        }
        return configuration;
    }

    public DAOFactory getFactory() {
        return new DynamoDBFactory();
    }
}
