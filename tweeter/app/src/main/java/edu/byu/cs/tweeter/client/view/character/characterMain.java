package edu.byu.cs.tweeter.client.view.character;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.Character;

public class characterMain extends AppCompatActivity {
    Cache cache = Cache.getInstance();
    Character thisCharacter;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_main);

        thisCharacter = cache.getCharacter(getIntent().getExtras().getString("characterID"));

        TextView characterName = findViewById(R.id.character_name);
        characterName.setText(thisCharacter.getName());

        TextView proficiency = findViewById(R.id.proficiency_bonus);
        proficiency.setText("+" + thisCharacter.getProficiency());

        setRadioButtons();
        setAbilities();
        setSaves();
        setSkills();

        findViewById(R.id.inspirationMarker).setOnClickListener(new View.OnClickListener() {
            boolean marked = false;
            @Override
            public void onClick(View view) {
                marked = !marked;
                if (marked) { findViewById(R.id.inspirationTrue).setVisibility(View.VISIBLE); }
                else { findViewById(R.id.inspirationTrue).setVisibility(View.INVISIBLE); }
            }
        });
    }

    private void setRadioButtons() {
        setSaveButton(findViewById(R.id.strengthSave), "strength");
        setSaveButton(findViewById(R.id.dexteritySave), "dexterity");
        setSaveButton(findViewById(R.id.constitutionSave), "constitution");
        setSaveButton(findViewById(R.id.intelligenceSave), "intelligence");
        setSaveButton(findViewById(R.id.wisdomSave), "wisdom");
        setSaveButton(findViewById(R.id.charismaSave), "charisma");

        setSkillButton(findViewById(R.id.acrobaticsButton), "acrobatics");
        setSkillButton(findViewById(R.id.animal_handlingButton), "animalHandling");
        setSkillButton(findViewById(R.id.arcanaButton), "arcana");
        setSkillButton(findViewById(R.id.athleticsButton), "athletics");
        setSkillButton(findViewById(R.id.deceptionButton), "deception");
        setSkillButton(findViewById(R.id.historyButton), "history");
        setSkillButton(findViewById(R.id.insightButton), "insight");
        setSkillButton(findViewById(R.id.intimidationButton), "intimidation");
        setSkillButton(findViewById(R.id.investigationButton), "investigation");
        setSkillButton(findViewById(R.id.medicineButton), "medicine");
        setSkillButton(findViewById(R.id.natureButton), "nature");
        setSkillButton(findViewById(R.id.perceptionButton), "perception");
        setSkillButton(findViewById(R.id.performanceButton), "performance");
        setSkillButton(findViewById(R.id.persuasionButton), "persuasion");
        setSkillButton(findViewById(R.id.religionButton), "religion");
        setSkillButton(findViewById(R.id.sleight_of_handButton), "sleightOfHand");
        setSkillButton(findViewById(R.id.stealthButton), "stealth");
        setSkillButton(findViewById(R.id.survivalButton), "survival");
    }

    private void setSaveButton(RadioButton radioButton, String save) {
        AtomicBoolean checked = new AtomicBoolean(thisCharacter.getProficient(save));
        radioButton.setChecked(checked.get());

        radioButton.setOnClickListener(view -> {
            if (!checked.get()) {
                thisCharacter.setProficient(save, true);
                checked.set(thisCharacter.getProficient(save));
                radioButton.setChecked(checked.get());
                if (checked.get()) { setSaves(); }
            }
        });
    }

    private void setSkillButton(RadioButton radioButton, String skill) {
        AtomicBoolean checked = new AtomicBoolean(thisCharacter.getProficient(skill));
        radioButton.setChecked(checked.get());

        radioButton.setOnClickListener(view -> {
            boolean wasChecked = checked.get();
            thisCharacter.setProficient(skill, !checked.get());
            checked.set(thisCharacter.getProficient(skill));
            radioButton.setChecked(checked.get());
            if (wasChecked ^ checked.get()) { setSkills(); }
        });
    }

    private void setAbilities() {
        setAbility(findViewById(R.id.strengthScore), findViewById(R.id.strengthModifier), thisCharacter.getStrengthScore(), thisCharacter.getStrengthMod());
        setAbility(findViewById(R.id.dexterityScore), findViewById(R.id.dexterityModifier), thisCharacter.getDexterityScore(), thisCharacter.getDexterityMod());
        setAbility(findViewById(R.id.constitutionScore), findViewById(R.id.constitutionModifier), thisCharacter.getConstitutionScore(), thisCharacter.getConstitutionMod());
        setAbility(findViewById(R.id.intelligenceScore), findViewById(R.id.intelligenceModifier), thisCharacter.getIntelligenceScore(), thisCharacter.getIntelligenceMod());
        setAbility(findViewById(R.id.wisdomScore), findViewById(R.id.wisdomModifier), thisCharacter.getWisdomScore(), thisCharacter.getWisdomMod());
        setAbility(findViewById(R.id.charismaScore), findViewById(R.id.charismaModifier), thisCharacter.getCharismaScore(), thisCharacter.getCharismaMod());
    }

    private void setSaves() {
        setModifierView(findViewById(R.id.strength_save), thisCharacter.getStrengthSave());
        setModifierView(findViewById(R.id.dexterity_save), thisCharacter.getDexteritySave());
        setModifierView(findViewById(R.id.constitution_save), thisCharacter.getConstitutionSave());
        setModifierView(findViewById(R.id.intelligence_save), thisCharacter.getIntelligenceSave());
        setModifierView(findViewById(R.id.wisdom_save), thisCharacter.getWisdomSave());
        setModifierView(findViewById(R.id.charisma_save), thisCharacter.getCharismaSave());
    }

    private void setSkills() {
        setModifierView(findViewById(R.id.acrobatics), thisCharacter.getAcrobatics());
        setModifierView(findViewById(R.id.animal_handling), thisCharacter.getAnimalHandling());
        setModifierView(findViewById(R.id.arcana), thisCharacter.getArcana());
        setModifierView(findViewById(R.id.athletics), thisCharacter.getAthletics());
        setModifierView(findViewById(R.id.deception), thisCharacter.getDeception());
        setModifierView(findViewById(R.id.history), thisCharacter.getHistory());
        setModifierView(findViewById(R.id.insight), thisCharacter.getInsight());
        setModifierView(findViewById(R.id.intimidation), thisCharacter.getIntimidation());
        setModifierView(findViewById(R.id.investigation), thisCharacter.getInvestigation());
        setModifierView(findViewById(R.id.medicine), thisCharacter.getMedicine());
        setModifierView(findViewById(R.id.nature), thisCharacter.getNature());
        setModifierView(findViewById(R.id.perception), thisCharacter.getPerception());
        setModifierView(findViewById(R.id.performance), thisCharacter.getPerformance());
        setModifierView(findViewById(R.id.persuasion), thisCharacter.getPersuasion());
        setModifierView(findViewById(R.id.religion), thisCharacter.getReligion());
        setModifierView(findViewById(R.id.sleight_of_hand), thisCharacter.getSleightOfHand());
        setModifierView(findViewById(R.id.stealth), thisCharacter.getStealth());
        setModifierView(findViewById(R.id.survival), thisCharacter.getSurvival());
    }

    @SuppressLint("SetTextI18n")
    private void setModifierView(TextView textView, int value) {
        if (value >= 0) { textView.setText("+" + value); }
        else { textView.setText(String.valueOf(value)); }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRoller(value);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setAbility(TextView score, TextView modifier, int scoreValue, int modValue) {
        score.setText(String.valueOf(scoreValue));
        setModifierView(modifier, modValue);
    }

    private void openRoller(int mod) {
        int[] rolls = {1};
        Dialog dialog = new Dialog(characterMain.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dice_results);

        TextView firstRoll = dialog.findViewById(R.id.firstRoll);
        firstRoll.setText(d20(mod));

        TextView secondRoll = dialog.findViewById(R.id.secondRoll);
        secondRoll.setText("");

        TextView thirdRoll = dialog.findViewById(R.id.thirdRoll);
        thirdRoll.setText("");

        dialog.findViewById(R.id.rollAgain).setOnClickListener(view -> {
            if (rolls[0] == 1) {
                rolls[0]++;
                secondRoll.setText(d20(mod));
            }

            else if (rolls[0] == 2) {
                rolls[0]++;
                thirdRoll.setText(d20(mod));
            }
        });

        dialog.findViewById(R.id.closeRoller).setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    private String d20(int mod) {
        Random rand = new Random();
        int roll = rand.nextInt(20) + 1;

        return "Y" + rollString(roll, mod);
    }

    private String rollString(int roll, int mod){
        StringBuilder string = new StringBuilder();
        string.append("ou rolled ");
        string.append(roll);

        if (mod >= 0) {
            string.append(" + ");
            string.append(mod);
        }

        else {
            string.append(" - ");
            string.append(-mod);
        }

        string.append(" = ");
        string.append(roll + mod);

        return string.toString();
    }
}
