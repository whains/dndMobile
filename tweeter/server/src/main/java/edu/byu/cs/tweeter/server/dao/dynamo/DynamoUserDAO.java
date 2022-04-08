package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class DynamoUserDAO implements UserDAO {

    @Override
    public User login(String username, String password) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("users");

        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias", username);

        try {
            Item outcome = table.getItem(spec);
            if (outcome == null) {
                throw new RuntimeException("[BadRequest] Wrong alias");
            }
            String returnedPassword = (String) outcome.get("password");
            boolean isValidPassword = validatePassword(password, returnedPassword);
            if (isValidPassword) {
                String alias = (String) outcome.get("alias");
                String firstName = (String) outcome.get("firstName");
                String lastName = (String) outcome.get("lastName");
                String imageURL = (String) outcome.get("imageURL");
                return new User(firstName, lastName, alias, imageURL);
            } else {
                throw new RuntimeException("[BadRequest] Wrong Password");
            }
        }
        catch (Exception e) {
            if (e.getMessage().equals("[BadRequest] Wrong alias")) {
                throw new RuntimeException("[BadRequest] Wrong alias");
            } else if (e.getMessage().equals("[BadRequest] Wrong Password")) {
                throw new RuntimeException("[BadRequest] Wrong Password");
            } else {
                throw new RuntimeException("[InternalServerError] Could not Login User:");
            }
        }
    }

    @Override
    public User getUserByAlias(String alias) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("users");

        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias", alias);

        try {
            Item outcome = table.getItem(spec);
            if (outcome == null) {
                throw new RuntimeException("[BadRequest] Invalid alias");
            }
            String firstName = (String) outcome.get("firstName");
            String lastName = (String) outcome.get("lastName");
            String imageURL = (String) outcome.get("imageURL");
            return new User(firstName, lastName, alias, imageURL);
        } catch (Exception ex) {
            if (ex.getMessage().equals("[BadRequest] Invalid alias")) {
                throw new RuntimeException("[BadRequest] Invalid alias");
            }
            throw new RuntimeException("[InternalServerError] Could not get User from Database");
        }
    }

    @Override
    public User register(String firstName, String lastName, String alias, String password, String imageBytesBase64, String imageURL) {
        String encryptedPassword;
        try {
            encryptedPassword = generateStrongPasswordHash(password);
        } catch (Exception ex) {
            throw new RuntimeException("[InternalServerError] Problem encrpyting password");
        }

        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("users");

        Item item = new Item()
                .withPrimaryKey("alias", alias)
                .withString("firstName", firstName)
                .withString("lastName", lastName)
                .withString("imageURL", imageURL)
                .withString("password", encryptedPassword)
                .withString("followersCount", "0")
                .withString("followingCount", "0");
        try {
            PutItemOutcome outcome = table.putItem(item);
        } catch (Exception ex) {
            throw new RuntimeException("[InternalServerError] Problem uploading user registration to database");
        }
        return new User(firstName, lastName, alias, imageURL);
    }

    @Override
    public int getFollowersCount(String alias) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("users");
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias", alias);
        try {
            Item outcome = table.getItem(spec);
            if (outcome.equals(null)) {
                throw new RuntimeException("[InternalServerError] Invalid alias");
            }
            String stringNumber = (String) outcome.get("followersCount");
            return Integer.parseInt(stringNumber);
        } catch(Exception ex) {
            throw new RuntimeException("[InternalServerError] Problem getting " + alias + "followersCount.");
        }
    }

    @Override
    public int getFollowingCount(String alias) {
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("users");
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias", alias);
        try {
            Item outcome = table.getItem(spec);
            if (outcome.equals(null)) {
                throw new RuntimeException("[InternalServerError] Invalid alias");
            }
            String stringNumber = (String) outcome.get("followingCount");
            return Integer.parseInt(stringNumber);
        } catch(Exception ex) {
            throw new RuntimeException("[InternalServerError] Problem getting " + alias + "followingCount.");
        }

    }

    @Override
    public void updateFollowersCount(String alias, int toAdd) {
        int count = getFollowersCount(alias);
        count += toAdd;
        String countString = String.valueOf(count);
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("users");
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("alias", alias)
                .withUpdateExpression("set followersCount = :val1")
                .withValueMap(new ValueMap().withString(":val1", countString))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        } catch (Exception e) {
            throw new RuntimeException("[InternalServerError] Problem updating " + alias + "FollowerCount");
        }
    }

    @Override
    public void updateFollowingCount(String alias, int toAdd) {
        int count = getFollowingCount(alias);
        count += toAdd;
        String countString = String.valueOf(count);
        Table table = DynamoConfiguration.getDynamoDBInstance().getTable("users");
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("alias", alias)
                .withUpdateExpression("set followingCount = :val1")
                .withValueMap(new ValueMap().withString(":val1", countString))
                .withReturnValues(ReturnValue.UPDATED_NEW);
        try {
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        } catch (Exception e) {
            throw new RuntimeException("[InternalServerError] Problem updating " + alias + "FollowerCount");
        }
    }

    @Override
    public void batchUserWrite(List<User> users) {
        TableWriteItems items = new TableWriteItems("users");

        for (User user : users) {
            Item item = new Item()
                    .withPrimaryKey("alias", user.getAlias())
                    .withString("firstName", user.getFirstName())
                    .withString("lastName", user.getLastName())
                    .withString("imageURL", user.getImageUrl())
                    .withString("password", "1000:da0b5a9fa01ed8fdcfc0143d368bcc44:bb46401fcb58380ede5678d12dda70db9e1c52aeea9429945c2576fe1b0111c3c4fe678aba2b4577d9adbaff43b2f83be2e00411d75e969a39f04e35ef1ece95")
                    .withString("followersCount", "0")
                    .withString("followingCount", "1");
            items.addItemToPut(item);

            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems("users");
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

    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException  {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException  {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    private static String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
}
