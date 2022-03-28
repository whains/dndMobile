package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.dao.AuthDAO;

public class DynamoAuthDAO implements AuthDAO {

    @Override
    public AuthToken generateAuthToken(String alias) {
        UUID randomUUID = UUID.randomUUID();
        String tokenString = randomUUID.toString();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dateTime = dtf.format(now);

        long epochTimeToDie = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10);

        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("authTokens");

        try {
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("authToken", tokenString, "dateTime", dateTime)
                            .with("alias", alias)
                            .with("ttl", epochTimeToDie));

            return new AuthToken(tokenString, dateTime);
        } catch (Exception ex) {
            throw new RuntimeException("[InternalServerError] Could not add AuthToken to Database");
        }
    }

    @Override
    public boolean validate(AuthToken authToken) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("authTokens");
        System.out.println(authToken);

        GetItemSpec spec = new GetItemSpec().withPrimaryKey("authToken", authToken.getToken(),
                "dateTime", authToken.getDatetime());

        try {
            Item outcome = table.getItem(spec);
            if (outcome.equals(null)) {
                throw new RuntimeException("[InternalServerError] Invalid AuthToken");
            }

            String returnedToken= (String) outcome.get("authToken");
            return returnedToken.equals(authToken.getToken());
        }
        catch (Exception e) {
            throw new RuntimeException("[InternalServerError] Could not validate user");
        }
    }

    @Override
    public void deleteAuthToken(AuthToken authToken) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("authTokens");
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey("authToken", authToken.getToken(), "dateTime", authToken.getDatetime());
        try {
            table.deleteItem(deleteItemSpec);
        } catch(Exception ex) {
            throw new RuntimeException("[InternalServerError] Problem deleting AuthToken ");
        }
    }
}
