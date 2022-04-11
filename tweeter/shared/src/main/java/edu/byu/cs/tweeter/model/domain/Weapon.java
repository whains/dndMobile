package edu.byu.cs.tweeter.model.domain;

import java.util.Random;

public class Weapon {
    String name = "";
    String damage = "";
    String damageType = "";
    String range = "";
    String description = "";

    int numRolls = 0;
    int die = 0;

    int attackBonus = 0;
    int damageBonus = 0;
    int specialAttack = 0;
    int specialDamage = 0;

    boolean isSimple = false;
    boolean isMartial = false;

    boolean isMelee = false;
    boolean isRanged = false;

    boolean hasFinesse = false;
    boolean isHeavy = false;
    boolean isLight = false;
    boolean hasLoading = false;
    boolean isThrown = false;
    boolean isTwoHanded = false;
    boolean isVersatile = false;
    boolean hasReach = false;

    public Weapon(String inputName, String inputDamage, String inputType, boolean simple, boolean melee) {
        name = inputName;
        damage = inputDamage;
        damageType = inputType;

        isSimple = simple;
        isMartial = !simple;
        isMelee = melee;
        isRanged = !melee;

        int D = inputDamage.indexOf('D');
        numRolls = Integer.parseInt(inputDamage.substring(0,D));
        die = Integer.parseInt(inputDamage.substring(D+1));
    }

    public void setRange(String input) { range = input; }

    public void setSpecialAttack(int input) { specialAttack = input; }

    public void setSpecialDamage(int input) { specialDamage = input; }

    public String getName() { return name + range; }

    public String getDescription() { return description; }

    public boolean getSimple() { return isSimple; }

    public boolean getMartial() { return isMartial; }

    public boolean getMelee() { return isMelee; }

    public boolean getRanged() { return isRanged; }

    public void setFinesse(boolean value) { hasFinesse = value; }

    public boolean hasFinesse() { return hasFinesse; }

    public void setBonus(int attack, int damage) {
        attackBonus = attack + specialAttack;
        damageBonus = damage + specialDamage;
    }

    public String printAttack() {
        if (attackBonus < 0) { return String.valueOf(attackBonus); }

        return "+" + attackBonus;
    }

    public String printDamage() {
        StringBuilder returnString = new StringBuilder();
        returnString.append(damage).append(" ");

        if (damageBonus < 0) { returnString.append("- ").append(-damageBonus); }
        else if (damageBonus > 0) { returnString.append("+ ").append(damageBonus); }

        returnString.append(" ").append(damageType);

        return returnString.toString();
    }

    public int getAttackBonus() { return attackBonus; }

    public int getDamageBonus() { return damageBonus; }

    public String getDamage() {
        return "On hit, the attack deals " + calculateDamage() + " " + damageType + " damage.";
    }

    private int calculateDamage() {
        Random rand = new Random();
        int damage = damageBonus;
        for (int i = 0; i < numRolls; i++) {
            damage += rand.nextInt(die) + 1;
        }



        return damage;
    }
}
