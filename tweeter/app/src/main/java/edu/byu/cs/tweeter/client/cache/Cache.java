package edu.byu.cs.tweeter.client.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Character;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * The Cache class stores globally accessible data.
 */
public class Cache {
    private static Cache instance = new Cache();

    public static Cache getInstance() {
        return instance;
    }

    /**
     * The currently logged-in user.
     */
    private User currUser;
    /**
     * The auth token for the current user session.
     */
    private AuthToken currUserAuthToken;

    static Map<String, Character> characters = new HashMap<>(); //Key = characterID.

    private Cache() {
        initialize();
    }

    private void initialize() {
        currUser = new User(null, null, null);
        currUserAuthToken = null;
    }

    public void clearCache() {
        initialize();
    }

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }

    public AuthToken getCurrUserAuthToken() {
        return currUserAuthToken;
    }

    public void setCurrUserAuthToken(AuthToken currUserAuthToken) {
        this.currUserAuthToken = currUserAuthToken;
    }

    public Character getCharacter(String characterID) {
        return characters.get(characterID);
    }

    public void addCharacter(Character character) {
        characters.put(character.getCharacterID(), character);
    }

    public Map<String, Character> getCharacters() {
        return characters;
    }

    public int numCharacters() {
        return characters.size();
    }
}
