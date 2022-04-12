package edu.byu.cs.tweeter.model.domain;

public class Item {
    String name = "";
    String description = "";

    public Item(String inputName) {
        name = inputName;
    }

    public void addDescription(String inputDescription) {
        description = inputDescription;
    }

    public String getName() { return name; }

    public String getDescription() { return description; }
}
