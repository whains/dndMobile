package edu.byu.cs.tweeter.server.dao;

public interface ImageDAO {
    String upload(String alias, String imageBytesBase64);
}
