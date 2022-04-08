package edu.byu.cs.tweeter.server.dao.dynamo;


import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.util.Pair;

public class DynamoStoryDAO implements StoryDAO {

    @Override
    public Pair<List<Status>, Boolean> getStory(String alias, int limit, Status lastStatus) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("story");

        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#alias", "alias");

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(":handle", alias);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#alias = :handle")
                .withNameMap(nameMap).withValueMap(valueMap).withScanIndexForward(false)
                .withMaxResultSize(limit);

        ItemCollection<QueryOutcome> items;
        Iterator<Item> iterator;
        Item item;
        List<Status> statuses = new ArrayList<>();

        Map<String, AttributeValue> map;

        PrimaryKey primaryKey = null;
        if (lastStatus != null) {
            primaryKey = new PrimaryKey("alias", lastStatus.getUser().getAlias(), "timeStamp", lastStatus.getTimeStamp());
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
                        item.getString("alias"),
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
            throw new RuntimeException("[InternalServerError] Problem getting story statuses");
        }
    }

    @Override
    public boolean postStatusToStory(Status status, String alias) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("story");
        User statusUser = status.getUser();

        Item item = new Item()
                .withPrimaryKey("alias", statusUser.getAlias(), "timeStamp", status.getTimeStamp())
                .withString("firstName", statusUser.getFirstName())
                .withString("lastName", statusUser.getLastName())
                .withString("imageURL", statusUser.getImageUrl())
                .withString("post", status.getPost())
                .withList("mentions", status.getMentions())
                .withList("urls", status.getUrls())
                .withString("dateTime", status.getDatetime());

        try {
            PutItemOutcome outcome = table.putItem(item);
            if (outcome == null) {
                return false;
            } else {
                return true;
            }
        } catch(Exception ex) {
            throw new RuntimeException("[InternalServerError] Problem posting status to story it really is this one: "+ alias + ex.getMessage());
        }
    }
}
