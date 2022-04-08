package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class DynamoConfiguration {
    private static DynamoDB dynamoDB;

    public DynamoConfiguration() {}

    public static DynamoDB getDynamoDBInstance() {
        if (dynamoDB == null) {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                    .withRegion("us-east-2")
                    .build();

            dynamoDB = new DynamoDB(client);
        }
        return dynamoDB;

    }
}
