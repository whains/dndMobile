package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.FollowDAO;
import edu.byu.cs.tweeter.server.util.Pair;

public class DynamoFollowDAO implements FollowDAO {

    @Override
    public Pair<List<User>, Boolean> getFollowees(String followerAlias, int limit, String lastFolloweeAlias) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("follows");

        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#follower", "follower_handle");

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":handle", followerAlias);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#follower = :handle")
                .withNameMap(nameMap).withValueMap(valueMap).withScanIndexForward(true)
                .withMaxResultSize(limit);

        ItemCollection<QueryOutcome> items;
        Iterator<Item> iterator;
        Item item;
        List<User> users = new ArrayList<>();

        Map<String, AttributeValue> map;
        PrimaryKey primaryKey = null;
        if (lastFolloweeAlias != null) {
            primaryKey = new PrimaryKey("follower_handle", followerAlias, "followee_handle", lastFolloweeAlias);
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
                users.add(new User(item.getString("followee_firstName"),
                        item.getString("followee_lastName"),
                        item.getString("followee_handle"),
                        item.getString("followee_imageURL")));
            }
            map = items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey();
            return new Pair<>(users, map != null);
        } catch (Exception e) {
            throw new RuntimeException("[InternalServerError] Problem getting followees");
        }
    }

    @Override
    public Pair<List<User>, Boolean> getFollowers(String followeeAlias, int limit, String lastFollowerAlias) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("follows");
        Index followIndex = table.getIndex("follows_index");

        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#followee", "followee_handle");

        HashMap<String, Object> valueMap2 = new HashMap<>();
        valueMap2.put(":handle", followeeAlias);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#followee = :handle")
                .withNameMap(nameMap).withValueMap(valueMap2).withScanIndexForward(true)
                .withMaxResultSize(limit);

        ItemCollection<QueryOutcome> items;
        Iterator<Item> iterator;
        Item item;
        List<User> users = new ArrayList<>();

        Map<String, AttributeValue> map;
        PrimaryKey primaryKey = null;
        if (lastFollowerAlias != null) {
            primaryKey = new PrimaryKey("followee_handle", followeeAlias, "follower_handle", lastFollowerAlias);
        }

        try {
            if (primaryKey == null) {
                items = followIndex.query(querySpec);
            } else {
                items = followIndex.query(querySpec.withExclusiveStartKey(primaryKey));
            }

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                users.add(new User(item.getString("follower_firstName"),
                        item.getString("follower_lastName"),
                        item.getString("follower_handle"),
                        item.getString("follower_imageURL")));
            }
            map = items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey();
            return new Pair<>(users, map != null);
        } catch (Exception e) {
            throw new RuntimeException("[InternalServerError] Problem getting followers");
        }
    }

    @Override
    public boolean follow(String currentUserAlias, User currentUser, String followeeAlias, User followee) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("follows");
        try {
            PutItemOutcome outcome = table
                    .putItem(new Item()
                            .withPrimaryKey("follower_handle", currentUserAlias, "followee_handle", followeeAlias)
                            .withString("follower_firstName", currentUser.getFirstName())
                            .withString("follower_lastName", currentUser.getLastName())
                            .withString("follower_imageURL", currentUser.getImageUrl())
                            .withString("followee_firstName", followee.getFirstName())
                            .withString("followee_lastName", followee.getLastName())
                            .withString("followee_imageURL", followee.getImageUrl())
                    );
            if (outcome != null) {
                return true;
            } else {
                return false;
            }
        } catch(Exception ex) {
            throw new RuntimeException("[InternalServerError] Problem following " + followeeAlias + ex.getMessage());
        }
    }

    @Override
    public boolean unfollow(String currentUserAlias, String followeeAlias) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("follows");
        System.out.println(currentUserAlias + "other: " + followeeAlias);
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey("follower_handle", currentUserAlias, "followee_handle", followeeAlias);
        try {
            table.deleteItem(deleteItemSpec);
            return true;
        } catch(Exception ex) {
            throw new RuntimeException("[InternalServerError] Problem unfollowing " + followeeAlias + ex.getMessage());
        }
    }

    @Override
    public boolean isFollower(String followerAlias, String followeeAlias) {
        System.out.println("follower: " + followerAlias + "followee: " + followeeAlias);
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("follows");
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("follower_handle", followerAlias, "followee_handle", followeeAlias);

        try {
            Item outcome = table.getItem(spec);
            if (outcome == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException("[InternalServerError] Problem finding if IsFollower in Database");
        }
    }

    @Override
    public void batchFollowWrite(String target, List<User> users) {
        TableWriteItems items = new TableWriteItems("follows");

        for (User user : users) {
            Item item = new Item()
                    .withPrimaryKey("follower_handle", user.getAlias(), "followee_handle", target)
                    .withString("follower_firstName", user.getFirstName())
                    .withString("follower_lastName", user.getLastName())
                    .withString("follower_imageURL", user.getImageUrl())
                    .withString("followee_firstName", "THE")
                    .withString("followee_lastName", "King")
                    .withString("followee_imageURL", user.getImageUrl());
            items.addItemToPut(item);

            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems("follows");
            }
        }

        // Write any leftover items
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWrite(items);
        }
    }

    private void loopBatchWrite(TableWriteItems items) {
        BatchWriteItemOutcome outcome = DynamoConfiguration.getDynamoDBInstance().batchWriteItem(items);
        while (outcome.getUnprocessedItems().size() > 0) {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            outcome = DynamoConfiguration.getDynamoDBInstance().batchWriteItemUnprocessed(unprocessedItems);
        }
    }

    @Override
    public List<String> getAllFollowers(String alias) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("follows");
        Index followIndex = table.getIndex("follows_index");

        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#followee", "followee_handle");

        HashMap<String, Object> valueMap2 = new HashMap<>();
        valueMap2.put(":handle", alias);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#followee = :handle")
                .withNameMap(nameMap).withValueMap(valueMap2).withScanIndexForward(false);

        ItemCollection<QueryOutcome> items;
        Iterator<Item> iterator;
        Item item;
        List<String> followers = new ArrayList<>();

        try {
            items = followIndex.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                followers.add(item.getString("follower_handle"));
            }
            return followers;
        } catch (Exception e) {
            throw new RuntimeException("[InternalServerError] Problem getting all followers for posting status");
        }
    }
}
