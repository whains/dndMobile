package edu.byu.cs.tweeter.model.domain;

public class Armor {
    String name;
    int baseAC;
    int AC;
    boolean light;
    boolean medium;
    boolean heavy;

    public Armor(String inputName, int baseAC, boolean isLight, boolean isMedium) {
        name = inputName;
        this.baseAC = baseAC;
        light = isLight;
        medium = isMedium;
        heavy = (!light && !medium);
    }

    public void setAC(int dexMod) {
        if (light) { AC = baseAC + dexMod; }
        else if (medium) {
            if (dexMod <= 2) { AC = baseAC + dexMod; }
            else { AC = baseAC + 2; }
        }
        else { AC = baseAC; }
    }

    public int getAC() { return AC; }
}
