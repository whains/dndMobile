package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.util.Pair;

public class DynamoFeedDAO implements FeedDAO {

    @Override
    public Pair<List<Status>, Boolean> getFeed(String alias, int limit, Status lastStatus) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("feed");

        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#ownerAlias", "ownerAlias");

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":handle", alias);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#ownerAlias = :handle")
                .withNameMap(nameMap).withValueMap(valueMap).withScanIndexForward(false)
                .withMaxResultSize(limit);

        ItemCollection<QueryOutcome> items;
        Iterator<Item> iterator;
        Item item;
        List<Status> statuses = new ArrayList<>();

        Map<String, AttributeValue> map;

        PrimaryKey primaryKey = null;
        if (lastStatus != null) {
            primaryKey = new PrimaryKey("ownerAlias", alias, "timeStamp", lastStatus.getTimeStamp());
        }

        try {
            if (primaryKey == null) {
                items = table.query(querySpec);
            } else {
                items = table.query(querySpec.withExclusiveStartKey(primaryKey));
            }

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                User user = new User(item.getString("firstName"),
                        item.getString("lastName"),
                        item.getString("authorAlias"),
                        item.getString("imageURL"));
                List<String> urls = item.getList("urls");
                List<String> mentions = item.getList("mentions");
                String dateTime = item.getString("dateTime");
                String post = item.getString("post");
                long timeStamp = item.getLong("timeStamp");
                statuses.add(new Status(post, user, dateTime, urls, mentions, timeStamp));
            }
            map = items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey();
            return new Pair<>(statuses, map != null);
        } catch (Exception e) {
            throw new RuntimeException("[InternalServerError] Problem getting feed statuses");
        }
    }

    @Override
    public boolean postStatusToUserFeed(Status status, String alias) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("feed");
        User statusUser = status.getUser();

        Item item = new Item()
                .withPrimaryKey("ownerAlias", statusUser.getAlias(), "timeStamp", status.getTimeStamp())
                .withString("authorAlias", statusUser.getAlias())
                .withString("firstName", statusUser.getFirstName())
                .withString("lastName", statusUser.getLastName())
                .withString("imageURL", statusUser.getImageUrl())
                .withString("post", status.getPost())
                .withString("dateTime", status.getDatetime())
                .withList("mentions", status.getMentions())
                .withList("urls", status.getUrls());

        try {
            PutItemOutcome outcome = table.putItem(item);
            if (outcome == null) {
                return false;
            } else {
                return true;
            }
        } catch(Exception ex) {
            throw new RuntimeException("[InternalServerError] Problem posting status to feed: "+ alias + ex.getMessage());
        }
    }

    @Override
    public boolean batchFeedWrite(Status status, List<User> followers) {
        User statusUser = status.getUser();
        List<Item> items = new ArrayList<>();
        for (User follower: followers) {
            Item item = new Item()
                    .withPrimaryKey("ownerAlias", follower.getAlias(), "timeStamp", status.getTimeStamp() )
                    .withString("authorAlias", statusUser.getAlias())
                    .withString("firstName", statusUser.getFirstName())
                    .withString("lastName", statusUser.getLastName())
                    .withString("imageURL", statusUser.getImageUrl())
                    .withString("post", status.getPost())
                    .withString("dateTime", status.getDatetime())
                    .withList("mentions", status.getMentions())
                    .withList("urls", status.getUrls());
            items.add(item);
        }

        TableWriteItems forumTableWriteItems = new TableWriteItems("feed")
                .withItemsToPut(items);

        try {
            BatchWriteItemOutcome outcome = DynamoConfiguration.getDynamoDBInstance().batchWriteItem(forumTableWriteItems);
            while (outcome.getUnprocessedItems().size() > 0) {
                Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
                outcome = DynamoConfiguration.getDynamoDBInstance().batchWriteItemUnprocessed(unprocessedItems);
            }
            return true;
        } catch(Exception ex) {
            throw new RuntimeException("[InternalServerError] Problem posting status to feed(BULK): "+ ex.getMessage());
        }
    }


}
