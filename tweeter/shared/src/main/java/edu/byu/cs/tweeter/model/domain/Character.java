package edu.byu.cs.tweeter.model.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Character {
    final int ID_SIZE = 10;

    String characterID;
    String characterName = "No Name";
    String race;
    String firstClass;
    String alignment;
    String background;

    boolean inspired = false;

    int failedSaves = 0;
    int succeededSaves = 0;

    boolean success1;
    boolean success2;
    boolean success3;

    boolean failure1;
    boolean failure2;
    boolean failure3;

    int currentHP = 0;
    int maxHP = 0;
    int tempHP = 0;

    int walkingSpeed;
    int swimmingSpeed;
    int climbingSpeed;
    int flyingSpeed;

    Armor currentArmor = null;

    int AC = 10;
    int initiative = 0;
    boolean initiativeAdvantage = false;

    int artificerLevel = 0;
    int barbarianLevel = 0;
    int bardLevel = 0;
    int clericLevel = 0;
    int druidLevel = 0;
    int fighterLevel = 0;
    int monkLevel = 0;
    int paladinLevel = 0;
    int rangerLevel = 0;
    int rogueLevel = 0;
    int sorcererLevel = 0;
    int warlockLevel = 0;
    int wizardLevel = 0;
    int characterLevel = 0;

    int proficiencyBonus = 2;

    int classProficiencies = 0;

    score strengthScore = new score();
    score dexterityScore = new score();
    score constitutionScore = new score();
    score intelligenceScore = new score();
    score wisdomScore = new score();
    score charismaScore = new score();

    save strengthSave = new save();
    save dexteritySave = new save();
    save constitutionSave = new save();
    save intelligenceSave = new save();
    save wisdomSave = new save();
    save charismaSave = new save();

    skill acrobatics = new skill();
    skill animalHandling = new skill();
    skill arcana = new skill();
    skill athletics = new skill();
    skill deception = new skill();
    skill history = new skill();
    skill insight = new skill();
    skill intimidation = new skill();
    skill investigation = new skill();
    skill medicine = new skill();
    skill nature = new skill();
    skill perception = new skill();
    skill performance = new skill();
    skill persuasion = new skill();
    skill religion = new skill();
    skill sleightOfHand = new skill();
    skill stealth = new skill();
    skill survival = new skill();

    boolean useSimpleWeapons = false;
    boolean useMartialWeapons = false;

    int platinumPieces = 0;
    int goldPieces = 0;
    int electrumPieces = 0;
    int silverPieces = 0;
    int copperPieces = 0;

    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Weapon> weapons = new ArrayList<>();

    public Character() {
        Random rand = new Random();
        StringBuilder IDBuilder = new StringBuilder();

        for (int i = 0; i < ID_SIZE; i++) {
            int ascii = rand.nextInt(62);

            if (ascii < 10) {
                IDBuilder.append(ascii);
            }

            else if (ascii < 36) {
                IDBuilder.append((char) (ascii + 55));
            }

            else {
                IDBuilder.append((char) (ascii + 61));
            }

        }

        characterID = IDBuilder.toString();
    }

    public void setCharacterName(String name) { characterName = name; }

    public void setRace(String race) {
        this.race = race;
        if (race.equals("High Elf")) {
            perception.setCanProficient(true);
            perception.setIsProficient(true, false);
            walkingSpeed = 30;
            swimmingSpeed = walkingSpeed/2;
            climbingSpeed = walkingSpeed/2;
        }
    }

    public void setAlignment(String alignment) { this.alignment = alignment; }

    public void setBackground(String background) {
        this.background = background;
        if (background.equals("Noble")) {
            history.setCanProficient(true);
            history.setIsProficient(true, false);
            persuasion.setCanProficient(true);
            persuasion.setIsProficient(true, false);
        }

        else if (background.equals("Charlatan")) {
            deception.setCanProficient(true);
            deception.setIsProficient(true, false);
            sleightOfHand.setCanProficient(true);
            sleightOfHand.setIsProficient(true, false);
        }
    }

    public void setFirstClass(String Class) {
        firstClass = Class;

        switch (firstClass){
            case "Artificier":
                artificierUp();

                classProficiencies = 2;

                allowArtificierSkills();
                maxHP = 8 + getConstitutionMod();

                break;

            case "Barbarian":
                barbarianUp();

                classProficiencies = 2;
                maxHP = 12 + getConstitutionMod();

                strengthSave.setCanProficient(true);
                constitutionSave.setCanProficient(true);
                strengthSave.setIsProficient(true);
                constitutionSave.setIsProficient(true);

                allowBarbarianSkills();

                break;

            case "Bard":
                bardUp();

                classProficiencies = 3;

                dexteritySave.setCanProficient(true);
                charismaSave.setCanProficient(true);
                dexteritySave.setIsProficient(true);
                charismaSave.setIsProficient(true);

                allowBardSkills();
                maxHP = 8 + getConstitutionMod();

                break;

            case "Cleric":
                clericUp();

                classProficiencies = 2;

                wisdomSave.setCanProficient(true);
                charismaSave.setCanProficient(true);
                wisdomSave.setIsProficient(true);
                charismaSave.setIsProficient(true);

                allowClericSkills();
                maxHP = 8 + getConstitutionMod();

                break;

            case "Druid":
                druidUp();

                classProficiencies = 2;

                intelligenceSave.setCanProficient(true);
                wisdomSave.setCanProficient(true);
                intelligenceSave.setIsProficient(true);
                wisdomSave.setIsProficient(true);

                allowDruidSkills();
                maxHP = 8 + getConstitutionMod();

                break;

            case "Fighter":
                fighterUp();

                classProficiencies = 2;

                strengthSave.setCanProficient(true);
                constitutionSave.setCanProficient(true);
                strengthSave.setIsProficient(true);
                constitutionSave.setIsProficient(true);

                allowFighterSkills();
                maxHP = 10 + getConstitutionMod();

                break;

            case "Monk":
                monkUp();

                classProficiencies = 2;

                strengthSave.setCanProficient(true);
                dexteritySave.setCanProficient(true);
                strengthSave.setIsProficient(true);
                dexteritySave.setIsProficient(true);

                allowMonkSkills();
                maxHP = 8 + getConstitutionMod();

                break;

            case "Paladin":
                paladinUp();

                classProficiencies = 2;

                wisdomSave.setCanProficient(true);
                charismaSave.setCanProficient(true);
                wisdomSave.setIsProficient(true);
                charismaSave.setIsProficient(true);

                allowPaladinSkills();
                maxHP = 10 + getConstitutionMod();

                break;

            case "Ranger":
                rangerUp();

                classProficiencies = 3;

                strengthSave.setCanProficient(true);
                dexteritySave.setCanProficient(true);
                strengthSave.setIsProficient(true);
                dexteritySave.setIsProficient(true);

                allowRangerSkills();
                maxHP = 10 + getConstitutionMod();

                break;

            case "Rogue":
                rogueUp();

                classProficiencies = 4;

                dexteritySave.setCanProficient(true);
                intelligenceSave.setCanProficient(true);
                dexteritySave.setIsProficient(true);
                intelligenceSave.setIsProficient(true);

                allowRogueSkills();
                maxHP = 8 + getConstitutionMod();

                break;

            case "Sorcerer":
                sorcererUp();

                classProficiencies = 2;

                constitutionSave.setCanProficient(true);
                charismaSave.setCanProficient(true);
                constitutionSave.setIsProficient(true);
                charismaSave.setIsProficient(true);

                allowSorcererSkills();
                maxHP = 6 + getConstitutionMod();

                break;

            case "Warlock":
                warlockUp();

                classProficiencies = 2;

                wisdomSave.setCanProficient(true);
                charismaSave.setCanProficient(true);
                wisdomSave.setIsProficient(true);
                charismaSave.setIsProficient(true);

                allowWarlockSkills();
                maxHP = 8 + getConstitutionMod();

                break;

            case "Wizard":
                wizardUp();

                classProficiencies = 2;

                intelligenceSave.setCanProficient(true);
                wisdomSave.setCanProficient(true);
                intelligenceSave.setIsProficient(true);
                wisdomSave.setIsProficient(true);

                allowWizardSkills();
                maxHP = 6 + getConstitutionMod();

                break;
        }

        currentHP = maxHP;
    }


    public void setStrengthScore(int score) {
        strengthScore.setScore(score);
        setStrengthSave();
        setAthletics();

        for (Weapon weapon : weapons) {
            updateWeapon(weapon);
        }
    }

    public void setDexterityScore(int score) {
        dexterityScore.setScore(score);
        setDexteritySave();
        setAcrobatics();
        setSleightOfHand();
        setStealth();

        if (currentArmor == null) {
            AC = 10 + getDexterityMod();
        }

        initiative = getDexterityMod();

        for (Weapon weapon : weapons) {
            updateWeapon(weapon);
        }
    }

    public void setConstitutionScore(int score) {
        int oldMod = constitutionScore.getModifier();
        constitutionScore.setScore(score);
        setConstitutionSave();
        int modDif = constitutionScore.getModifier() - oldMod;
        maxHP += (modDif * characterLevel);
        currentHP += (modDif * characterLevel);
    }

    public void setIntelligenceScore(int score) {
        intelligenceScore.setScore(score);
        setIntelligenceSave();
        setArcana();
        setHistory();
        setInvestigation();
        setNature();
        setReligion();
    }

    public void setWisdomScore(int score) {
        wisdomScore.setScore(score);
        setWisdomSave();
        setAnimalHandling();
        setInsight();
        setMedicine();
        setPerception();
        setSurvival();
    }

    public void setCharismaScore(int score) {
        charismaScore.setScore(score);
        setCharismaSave();
        setDeception();
        setIntimidation();
        setPerformance();
        setPersuasion();
    }


    public String getCharacterID() { return characterID; }

    public String getName() { return characterName; }

    public String getRace() { return race; }

    public String getAlignment() { return alignment; }

    public String getBackground() { return characterName; }

    public String printClassLevels() {
        StringBuilder classLevel = new StringBuilder("Level " + characterLevel + " " + race);

        if (characterLevel == 0) {
            return classLevel.toString();
        }

        if (artificerLevel == characterLevel) {
            classLevel.append(" Artificier");
            return classLevel.toString();
        }

        if (barbarianLevel == characterLevel) {
            classLevel.append(" Barbarian");
            return classLevel.toString();
        }

        if (bardLevel == characterLevel) {
            classLevel.append(" Bard");
            return classLevel.toString();
        }

        if (clericLevel == characterLevel) {
            classLevel.append(" Cleric");
            return classLevel.toString();
        }

        if (druidLevel == characterLevel) {
            classLevel.append(" Druid");
            return classLevel.toString();
        }

        if (fighterLevel == characterLevel) {
            classLevel.append(" Fighter");
            return classLevel.toString();
        }

        if (monkLevel == characterLevel) {
            classLevel.append(" Monk");
            return classLevel.toString();
        }

        if (paladinLevel == characterLevel) {
            classLevel.append(" Paladin");
            return classLevel.toString();
        }

        if (rangerLevel == characterLevel) {
            classLevel.append(" Ranger");
            return classLevel.toString();
        }

        if (rogueLevel == characterLevel) {
            classLevel.append(" Rogue");
            return classLevel.toString();
        }

        if (sorcererLevel == characterLevel) {
            classLevel.append(" Sorcerer");
            return classLevel.toString();
        }

        if (warlockLevel == characterLevel) {
            classLevel.append(" Warlock");
            return classLevel.toString();
        }

        if (wizardLevel == characterLevel) {
            classLevel.append(" Wizard");
            return classLevel.toString();
        }

        classLevel.append(" multiclass");
        return classLevel.toString();
    }


    public int getStrengthScore() { return strengthScore.getScore(); }

    public int getDexterityScore() { return dexterityScore.getScore(); }

    public int getConstitutionScore() { return constitutionScore.getScore(); }

    public int getIntelligenceScore() { return intelligenceScore.getScore(); }

    public int getWisdomScore() { return wisdomScore.getScore(); }

    public int getCharismaScore() { return charismaScore.getScore(); }


    public int getStrengthMod() { return strengthScore.getModifier(); }

    public int getDexterityMod() { return dexterityScore.getModifier(); }

    public int getConstitutionMod() { return constitutionScore.getModifier(); }

    public int getIntelligenceMod() { return intelligenceScore.getModifier(); }

    public int getWisdomMod() { return wisdomScore.getModifier(); }

    public int getCharismaMod() { return charismaScore.getModifier(); }


    public int getStrengthSave() { return strengthSave.getValue(); }

    public int getDexteritySave() { return dexteritySave.getValue(); }

    public int getConstitutionSave() { return constitutionSave.getValue(); }

    public int getIntelligenceSave() { return intelligenceSave.getValue(); }

    public int getWisdomSave() { return wisdomSave.getValue(); }

    public int getCharismaSave() { return charismaSave.getValue(); }


    private void setStrengthSave() { strengthSave.setValue(strengthScore.getModifier()); }

    private void setDexteritySave() { dexteritySave.setValue(dexterityScore.getModifier()); }

    private void setConstitutionSave() { constitutionSave.setValue(constitutionScore.getModifier()); }

    private void setIntelligenceSave() { intelligenceSave.setValue(intelligenceScore.getModifier()); }

    private void setWisdomSave() { wisdomSave.setValue(wisdomScore.getModifier()); }

    private void setCharismaSave() { charismaSave.setValue(charismaScore.getModifier()); }


    private void setAcrobatics() { acrobatics.setValue(dexterityScore.getModifier()); }

    private void setAnimalHandling() { animalHandling.setValue(wisdomScore.getModifier()); }

    private void setArcana() { arcana.setValue(intelligenceScore.getModifier()); }

    private void setAthletics() { athletics.setValue(strengthScore.getModifier()); }

    private void setDeception() { deception.setValue(charismaScore.getModifier()); }

    private void setHistory() { history.setValue(intelligenceScore.getModifier()); }

    private void setInsight() { insight.setValue(wisdomScore.getModifier()); }

    private void setIntimidation() { intimidation.setValue(charismaScore.getModifier()); }

    private void setInvestigation() { investigation.setValue(intelligenceScore.getModifier()); }

    private void setMedicine() { medicine.setValue(wisdomScore.getModifier()); }

    private void setNature() { nature.setValue(intelligenceScore.getModifier()); }

    private void setPerception() { perception.setValue(wisdomScore.getModifier()); }

    private void setPerformance() { performance.setValue(charismaScore.getModifier()); }

    private void setPersuasion() { persuasion.setValue(charismaScore.getModifier()); }

    private void setReligion() { religion.setValue(intelligenceScore.getModifier()); }

    private void setSleightOfHand() { sleightOfHand.setValue(dexterityScore.getModifier()); }

    private void setStealth() { stealth.setValue(dexterityScore.getModifier()); }

    private void setSurvival() { survival.setValue(wisdomScore.getModifier()); }

    public void setProficient(String skill, boolean value) {
        boolean changed = false;

        switch (skill) {
            case "strength":
                strengthSave.setIsProficient(value);
                break;
            case "dexterity":
                dexteritySave.setIsProficient(value);
                break;
            case "constitution":
                constitutionSave.setIsProficient(value);
                break;
            case "intelligence":
                intelligenceSave.setIsProficient(value);
                break;
            case "wisdom":
                wisdomSave.setIsProficient(value);
                break;
            case "charisma":
                charismaSave.setIsProficient(value);
                break;

            case "acrobatics":
                changed = acrobatics.setIsProficient(value, true);
                break;
            case "animalHandling":
                changed = animalHandling.setIsProficient(value, true);
                break;
            case "arcana":
                changed = arcana.setIsProficient(value, true);
                break;
            case "athletics":
                changed = athletics.setIsProficient(value, true);
                break;
            case "deception":
                changed = deception.setIsProficient(value,true);
                break;
            case "history":
                changed = history.setIsProficient(value, true);
                break;
            case "insight":
                changed = insight.setIsProficient(value, true);
                break;
            case "intimidation":
                changed = intimidation.setIsProficient(value, true);
                break;
            case "investigation":
                changed = investigation.setIsProficient(value, true);
                break;
            case "medicine":
                changed = medicine.setIsProficient(value, true);
                break;
            case "nature":
                changed = nature.setIsProficient(value, true);
                break;
            case "perception":
                changed = perception.setIsProficient(value, true);
                break;
            case "performance":
                changed = performance.setIsProficient(value, true);
                break;
            case "persuasion":
                changed = persuasion.setIsProficient(value, true);
                break;
            case "religion":
                changed = religion.setIsProficient(value, true);
                break;
            case "sleightOfHand":
                changed = sleightOfHand.setIsProficient(value, true);
                break;
            case "stealth":
                changed = stealth.setIsProficient(value, true);
                break;
            case "survival":
                changed = survival.setIsProficient(value, true);
                break;
        }

        if (changed) {
            if (value) { classProficiencies--; } //proficiency added
            else { classProficiencies++; }  //proficiency removed

            if (classProficiencies == 0) { skillsFull(); }
            else {
                switch (firstClass) {
                    case "Artificier":
                        allowArtificierSkills();
                        break;

                    case "Barbarian":
                        allowBarbarianSkills();
                        break;

                    case "Bard":
                        allowBardSkills();
                        break;

                    case "Cleric":
                        allowClericSkills();
                        break;

                    case "Druid":
                        allowDruidSkills();
                        break;

                    case "Fighter":
                        allowFighterSkills();
                        break;

                    case "Monk":
                        allowMonkSkills();
                        break;

                    case "Paladin":
                        allowPaladinSkills();
                        break;

                    case "Ranger":
                        allowRangerSkills();
                        break;

                    case "Rogue":
                        allowRogueSkills();
                        break;

                    case "Sorcerer":
                        allowSorcererSkills();
                        break;

                    case "Warlock":
                        allowWarlockSkills();
                        break;

                    case "Wizard":
                        allowWizardSkills();
                        break;
                }
            }
        }
    }

    public boolean getProficient(String skill) {
        switch (skill) {
            case "strength":
                return strengthSave.isProficient();
            case "dexterity":
                return dexteritySave.isProficient();
            case "constitution":
                return constitutionSave.isProficient();
            case "intelligence":
                return intelligenceSave.isProficient();
            case "wisdom":
                return wisdomSave.isProficient();
            case "charisma":
                return charismaSave.isProficient();

            case "acrobatics":
                return acrobatics.isProficient();
            case "animalHandling":
                return animalHandling.isProficient();
            case "arcana":
                return arcana.isProficient();
            case "athletics":
                return athletics.isProficient();
            case "deception":
                return deception.isProficient();
            case "history":
                return history.isProficient();
            case "insight":
                return insight.isProficient();
            case "intimidation":
                return intimidation.isProficient();
            case "investigation":
                return investigation.isProficient();
            case "medicine":
                return medicine.isProficient();
            case "nature":
                return nature.isProficient();
            case "perception":
                return perception.isProficient();
            case "performance":
                return performance.isProficient();
            case "persuasion":
                return persuasion.isProficient();
            case "religion":
                return religion.isProficient();
            case "sleightOfHand":
                return sleightOfHand.isProficient();
            case "stealth":
                return stealth.isProficient();
            case "survival":
                return survival.isProficient();
        }

        return false;
    }


    public int getAcrobatics() { return acrobatics.getValue(); }

    public int getAnimalHandling() { return animalHandling.getValue(); }

    public int getArcana() { return arcana.getValue(); }

    public int getAthletics() { return athletics.getValue(); }

    public int getDeception() { return deception.getValue(); }

    public int getHistory() { return history.getValue(); }

    public int getInsight() { return insight.getValue(); }

    public int getIntimidation() { return intimidation.getValue(); }

    public int getInvestigation() { return investigation.getValue(); }

    public int getMedicine() { return medicine.getValue(); }

    public int getNature() { return nature.getValue(); }

    public int getPerception() { return perception.getValue(); }

    public int getPerformance() { return performance.getValue(); }

    public int getPersuasion() { return persuasion.getValue(); }

    public int getReligion() { return religion.getValue(); }

    public int getSleightOfHand() { return sleightOfHand.getValue(); }

    public int getStealth() { return stealth.getValue(); }

    public int getSurvival() { return survival.getValue(); }


    public void artificierUp() {
        levelUp();
        artificerLevel++;
    }

    public void barbarianUp() {
        levelUp();
        barbarianLevel++;
        useSimpleWeapons = true;
        useMartialWeapons = true;
    }

    public void bardUp() {
        levelUp();
        bardLevel++;
    }

    public void clericUp() {
        levelUp();
        bardLevel++;
    }

    public void druidUp() {
        levelUp();
        druidLevel++;
    }

    public void fighterUp() {
        levelUp();
        fighterLevel++;
        useSimpleWeapons = true;
        useMartialWeapons = true;
    }

    public void monkUp() {
        levelUp();
        monkLevel++;
        useSimpleWeapons = true;
    }

    public void paladinUp() {
        levelUp();
        paladinLevel++;
        useSimpleWeapons = true;
        useMartialWeapons = true;
    }

    public void rangerUp() {
        levelUp();
        rangerLevel++;
        useSimpleWeapons = true;
        useMartialWeapons = true;
    }

    public void rogueUp() {
        levelUp();
        rogueLevel++;
    }

    public void sorcererUp() {
        levelUp();
        sorcererLevel++;
    }

    public void warlockUp() {
        levelUp();
        warlockLevel++;
        useSimpleWeapons = true;
    }

    public void wizardUp() {
        levelUp();
        wizardLevel++;
    }


    private void levelUp() {
        characterLevel++;
        int oldBonus = proficiencyBonus;

        if (characterLevel < 5) { proficiencyBonus = 2; }
        else if (characterLevel < 9) { proficiencyBonus = 3; }
        else if (characterLevel < 13) { proficiencyBonus = 4; }
        else if (characterLevel < 17) { proficiencyBonus = 5; }
        else { proficiencyBonus = 6; }

        if (proficiencyBonus - oldBonus > 0) {
            if (strengthSave.isProficient()) { setStrengthSave(); }
            if (dexteritySave.isProficient()) { setDexteritySave(); }
            if (constitutionSave.isProficient()) { setConstitutionSave(); }
            if (intelligenceSave.isProficient()) { setIntelligenceSave(); }
            if (wisdomSave.isProficient()) { setWisdomSave(); }
            if (charismaSave.isProficient()) { setCharismaSave(); }

            if ((bardLevel > 1) || acrobatics.isProficient()) { setAcrobatics(); }
            if ((bardLevel > 1) || animalHandling.isProficient()) { setAnimalHandling(); }
            if ((bardLevel > 1) || arcana.isProficient()) { setArcana(); }
            if ((bardLevel > 1) || athletics.isProficient()) { setAthletics(); }
            if ((bardLevel > 1) || deception.isProficient()) { setDeception(); }
            if ((bardLevel > 1) || history.isProficient()) { setHistory(); }
            if ((bardLevel > 1) || insight.isProficient()) { setInsight(); }
            if ((bardLevel > 1) || intimidation.isProficient()) { setIntimidation(); }
            if ((bardLevel > 1) || investigation.isProficient()) { setInvestigation(); }
            if ((bardLevel > 1) || medicine.isProficient()) { setMedicine(); }
            if ((bardLevel > 1) || nature.isProficient()) { setNature(); }
            if ((bardLevel > 1) || perception.isProficient()) { setPerception(); }
            if ((bardLevel > 1) || performance.isProficient()) { setPerformance(); }
            if ((bardLevel > 1) || persuasion.isProficient()) { setPersuasion(); }
            if ((bardLevel > 1) || religion.isProficient()) { setReligion(); }
            if ((bardLevel > 1) || sleightOfHand.isProficient()) { setSleightOfHand(); }
            if ((bardLevel > 1) || stealth.isProficient()) { setStealth(); }
            if ((bardLevel > 1) || survival.isProficient()) { setSurvival(); }

            for (Weapon weapon : weapons) {
                updateWeapon(weapon);
            }
        }
    }

    public int getProficiency() { return proficiencyBonus; }


    private void allowArtificierSkills() {
        arcana.setCanProficient(true);
        history.setCanProficient(true);
        investigation.setCanProficient(true);
        medicine.setCanProficient(true);
        nature.setCanProficient(true);
        perception.setCanProficient(true);
        sleightOfHand.setCanProficient(true);
    }

    private void allowBarbarianSkills() {
        animalHandling.setCanProficient(true);
        athletics.setCanProficient(true);
        intimidation.setCanProficient(true);
        nature.setCanProficient(true);
        perception.setCanProficient(true);
        survival.setCanProficient(true);
    }

    private void allowBardSkills() { allowAllSkills(); }

    private void allowClericSkills() {
        history.setCanProficient(true);
        insight.setCanProficient(true);
        medicine.setCanProficient(true);
        persuasion.setCanProficient(true);
        religion.setCanProficient(true);
    }

    private void allowDruidSkills() {
        animalHandling.setCanProficient(true);
        arcana.setCanProficient(true);
        insight.setCanProficient(true);
        medicine.setCanProficient(true);
        nature.setCanProficient(true);
        perception.setCanProficient(true);
        religion.setCanProficient(true);
        survival.setCanProficient(true);
    }

    private void allowFighterSkills() {
        acrobatics.setCanProficient(true);
        animalHandling.setCanProficient(true);
        athletics.setCanProficient(true);
        history.setCanProficient(true);
        insight.setCanProficient(true);
        intimidation.setCanProficient(true);
        perception.setCanProficient(true);
        survival.setCanProficient(true);
    }

    private void allowMonkSkills() {
        acrobatics.setCanProficient(true);
        athletics.setCanProficient(true);
        history.setCanProficient(true);
        insight.setCanProficient(true);
        religion.setCanProficient(true);
        stealth.setCanProficient(true);
    }

    private void allowPaladinSkills() {
        athletics.setCanProficient(true);
        insight.setCanProficient(true);
        intimidation.setCanProficient(true);
        medicine.setCanProficient(true);
        persuasion.setCanProficient(true);
        religion.setCanProficient(true);
    }

    private void allowRangerSkills() {
        animalHandling.setCanProficient(true);
        athletics.setCanProficient(true);
        insight.setCanProficient(true);
        investigation.setCanProficient(true);
        nature.setCanProficient(true);
        perception.setCanProficient(true);
        stealth.setCanProficient(true);
        survival.setCanProficient(true);
    }

    private void allowRogueSkills() {
        acrobatics.setCanProficient(true);
        athletics.setCanProficient(true);
        deception.setCanProficient(true);
        insight.setCanProficient(true);
        intimidation.setCanProficient(true);
        investigation.setCanProficient(true);
        perception.setCanProficient(true);
        performance.setCanProficient(true);
        persuasion.setCanProficient(true);
        sleightOfHand.setCanProficient(true);
        stealth.setCanProficient(true);
    }

    private void allowSorcererSkills() {
        arcana.setCanProficient(true);
        deception.setCanProficient(true);
        insight.setCanProficient(true);
        intimidation.setCanProficient(true);
        persuasion.setCanProficient(true);
        religion.setCanProficient(true);
    }

    private void allowWarlockSkills() {
        arcana.setCanProficient(true);
        deception.setCanProficient(true);
        history.setCanProficient(true);
        intimidation.setCanProficient(true);
        investigation.setCanProficient(true);
        nature.setCanProficient(true);
        religion.setCanProficient(true);
    }

    private void allowWizardSkills() {
        arcana.setCanProficient(true);
        history.setCanProficient(true);
        insight.setCanProficient(true);
        investigation.setCanProficient(true);
        medicine.setCanProficient(true);
        religion.setCanProficient(true);
    }

    private void skillsFull() {
        acrobatics.skillsFull();
        animalHandling.skillsFull();
        arcana.skillsFull();
        athletics.skillsFull();
        deception.skillsFull();
        history.skillsFull();
        insight.skillsFull();
        intimidation.skillsFull();
        investigation.skillsFull();
        medicine.skillsFull();
        nature.skillsFull();
        perception.skillsFull();
        performance.skillsFull();
        persuasion.skillsFull();
        religion.skillsFull();
        sleightOfHand.skillsFull();
        stealth.skillsFull();
        survival.skillsFull();
    }

    private void allowAllSkills() {
        acrobatics.setCanProficient(true);
        animalHandling.setCanProficient(true);
        arcana.setCanProficient(true);
        athletics.setCanProficient(true);
        deception.setCanProficient(true);
        history.setCanProficient(true);
        insight.setCanProficient(true);
        intimidation.setCanProficient(true);
        investigation.setCanProficient(true);
        medicine.setCanProficient(true);
        nature.setCanProficient(true);
        perception.setCanProficient(true);
        performance.setCanProficient(true);
        persuasion.setCanProficient(true);
        religion.setCanProficient(true);
        sleightOfHand.setCanProficient(true);
        stealth.setCanProficient(true);
        survival.setCanProficient(true);
    }


    public void addPlatinumPieces(int pieces) { platinumPieces += pieces; }

    public void addGoldPieces(int pieces) { goldPieces += pieces; }

    public void addElectrumPieces(int pieces) { electrumPieces += pieces; }

    public void addSilverPieces(int pieces) { silverPieces += pieces; }

    public void addCopperPieces(int pieces) { copperPieces += pieces; }

    public void exchangePieces() {
        silverPieces += copperPieces / 10;
        copperPieces = copperPieces % 10;

        electrumPieces += silverPieces / 5;
        silverPieces = silverPieces % 5;

        goldPieces += electrumPieces / 2;
        electrumPieces = electrumPieces % 2;

        platinumPieces += goldPieces / 10;
        goldPieces = goldPieces % 10;
    }


    public int getPlatinumPieces() { return platinumPieces; }

    public int getGoldPieces() { return goldPieces; }

    public int getElectrumPieces() { return electrumPieces; }

    public int getSilverPieces() { return silverPieces; }

    public int getCopperPieces() { return copperPieces; }


    public void addItem(Item item) { items.add(item); }

    public ArrayList<Item> getItems() { return items; }

    public void addWeapon(Weapon weapon) { weapons.add(updateWeapon(weapon)); }

    public ArrayList<Weapon> getWeapons() { return weapons; }

    private Weapon updateWeapon(Weapon weapon) {
        int skillBonus;
        int attackBonus = 0;

        if (weapon.getRanged()) {
            skillBonus = getDexterityMod();
        }

        else {
            skillBonus = getStrengthMod();

            if (weapon.hasFinesse() && (skillBonus < getDexterityMod())) {
                skillBonus = getDexterityMod();
            }
        }

        if ((weapon.getSimple() && useSimpleWeapons) || (weapon.getMartial() && useMartialWeapons)) {
            attackBonus += proficiencyBonus;
        }

        attackBonus += skillBonus;
        weapon.setBonus(attackBonus, skillBonus);

        return weapon;
    }

    public int getAC() { return AC; }

    public int getInitiative() { return initiative; }

    public String printInitiative() {
        StringBuilder initiativeString = new StringBuilder();
        if (initiative < 0) { initiativeString.append(initiative); }
        else { initiativeString.append("+").append(initiative); }

        return initiativeString.toString();
    }

    public int getCurrentHP() { return currentHP; }

    public int getMaxHP() { return maxHP; }

    public int getTemporaryHP() { return tempHP; }

    public void addMaxHP(int value) {
        maxHP += value;
        currentHP += value;
    }

    public void equipArmor(Armor armor) {
        if (currentArmor != null) { currentArmor.setAC(0); }
        armor.setAC(getDexterityMod());
        currentArmor = armor;
        AC = currentArmor.getAC();
    }

    public int getWalkingSpeed() { return walkingSpeed; }

    public int getSwimmingSpeed() { return swimmingSpeed; }

    public int getClimbingSpeed() { return climbingSpeed; }

    public int getFlyingSpeed() { return flyingSpeed; }

    public int getSuccesses() { return succeededSaves; }

    public int getfailures() { return failedSaves; }

    public boolean getSuccess1() { return success1; }

    public boolean getSuccess2() { return success2; }

    public boolean getSuccess3() { return success3; }

    public boolean getFailure1() { return failure1; }

    public boolean getFailure2() { return failure2; }

    public boolean getFailure3() { return failure3; }

    public void setSuccess1() {
        success1 = !success1;
        if (success1) { succeededSaves++; }
        else { succeededSaves--; }
    }

    public void setSuccess2() {
        success2 = !success2;
        if (success2) { succeededSaves++; }
        else { succeededSaves--; }
    }

    public void setSuccess3() {
        success3 = !success3;
        if (success3) { succeededSaves++; }
        else { succeededSaves--; }
    }

    public void setFailure1() {
        failure1 = !failure1;
        if (failure1) { failedSaves++; }
        else { failedSaves--; }
    }

    public void setFailure2() {
        failure2 = !failure2;
        if (failure2) { failedSaves++; }
        else { failedSaves--; }
    }

    public void setFailure3() {
        failure3 = !failure3;
        if (failure3) { failedSaves++; }
        else { failedSaves--; }
    }

    public boolean getInspiration() { return inspired; }

    public void setInspiration() { inspired = !inspired; }

    private static class score {
        int score = 0;
        int modifier = 0;

        public void setScore(int value) {
            score = value;
            modifier = (score - 10)/2;
        }

        public int getScore() { return score; }

        public int getModifier() { return modifier; }
    }

    private class save {
        boolean canProficient = false;
        boolean isProficient = false;
        int value = 0;

        public void setValue(int modifier) {
            value = modifier;
            if (isProficient) { value += proficiencyBonus; }
        }

        public void setCanProficient(boolean value) { canProficient = value; }

        public void setIsProficient(boolean value) {
            boolean wasProficient = isProficient;
            isProficient = (canProficient && value);

            if (wasProficient && !isProficient) {
                this.value -= proficiencyBonus;
            }

            else if (!wasProficient && isProficient) {
                this.value += proficiencyBonus;
            }
        }
        public int getValue() { return value; }

        public boolean isProficient() { return isProficient; }
    }

    private class skill {
        boolean classSkill = false;
        boolean canProficient = false;
        boolean isProficient = false;
        boolean isExpert = false;
        int value = 0;

        public void setValue(int modifier) {
            value = modifier;
            if (isProficient) { value += proficiencyBonus; }
            if (isExpert) { value += proficiencyBonus; }
            if ((bardLevel > 1) && !isProficient) { value += (proficiencyBonus/2); }
        }

        public void setCanProficient(boolean value) { this.canProficient = value; }

        public boolean setIsProficient(boolean value, boolean Class) {
            if (classSkill || (value)) {
                classSkill = Class;
                boolean wasProficient = isProficient;
                isProficient = (canProficient && value);

                if (wasProficient && !isProficient) {
                    this.value -= proficiencyBonus;
                    setIsExpert(false);
                    return true;
                }

                if (!wasProficient && isProficient) {
                    this.value += proficiencyBonus;
                    return true;
                }
            }

            return false;
        }

        public void setIsExpert(boolean value) {
            boolean wasExpert = isExpert;
            isExpert = ((isProficient || isExpert) && value);

            if (wasExpert && !isExpert) {
                this.value -= proficiencyBonus;
            }

            else if (!wasExpert && isExpert) {
                this.value += proficiencyBonus;
            }

        }

        public int getValue() { return value; }

        public boolean isProficient() { return isProficient; }

        public boolean isExpert() { return isExpert; }

        public void skillsFull() { canProficient = isProficient; }
    }
}