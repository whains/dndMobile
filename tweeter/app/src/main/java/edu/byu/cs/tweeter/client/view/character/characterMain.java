package edu.byu.cs.tweeter.client.view.character;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.Character;

public class characterMain extends Fragment {
    Cache cache = Cache.getInstance();
    Character thisCharacter;
    View view;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.character_main, container, false);
        thisCharacter = cache.getCharacter(container.getTag().toString());

        TextView proficiency = view.findViewById(R.id.proficiency_bonus);
        proficiency.setText("+" + thisCharacter.getProficiency());

        setRadioButtons();
        setAbilities();
        setSaves();
        setSkills();

        view.findViewById(R.id.inspirationMarker).setOnClickListener(new View.OnClickListener() {
            boolean marked = false;
            @Override
            public void onClick(View view) {
                marked = !marked;
                if (marked) { view.findViewById(R.id.inspirationTrue).setVisibility(View.VISIBLE); }
                else { view.findViewById(R.id.inspirationTrue).setVisibility(View.INVISIBLE); }
            }
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static characterMain newInstance() {
        return new characterMain();
    }

    private void setRadioButtons() {
        setSaveButton(view.findViewById(R.id.strengthSave), "strength");
        setSaveButton(view.findViewById(R.id.dexteritySave), "dexterity");
        setSaveButton(view.findViewById(R.id.constitutionSave), "constitution");
        setSaveButton(view.findViewById(R.id.intelligenceSave), "intelligence");
        setSaveButton(view.findViewById(R.id.wisdomSave), "wisdom");
        setSaveButton(view.findViewById(R.id.charismaSave), "charisma");

        setSkillButton(view.findViewById(R.id.acrobaticsButton), "acrobatics");
        setSkillButton(view.findViewById(R.id.animal_handlingButton), "animalHandling");
        setSkillButton(view.findViewById(R.id.arcanaButton), "arcana");
        setSkillButton(view.findViewById(R.id.athleticsButton), "athletics");
        setSkillButton(view.findViewById(R.id.deceptionButton), "deception");
        setSkillButton(view.findViewById(R.id.historyButton), "history");
        setSkillButton(view.findViewById(R.id.insightButton), "insight");
        setSkillButton(view.findViewById(R.id.intimidationButton), "intimidation");
        setSkillButton(view.findViewById(R.id.investigationButton), "investigation");
        setSkillButton(view.findViewById(R.id.medicineButton), "medicine");
        setSkillButton(view.findViewById(R.id.natureButton), "nature");
        setSkillButton(view.findViewById(R.id.perceptionButton), "perception");
        setSkillButton(view.findViewById(R.id.performanceButton), "performance");
        setSkillButton(view.findViewById(R.id.persuasionButton), "persuasion");
        setSkillButton(view.findViewById(R.id.religionButton), "religion");
        setSkillButton(view.findViewById(R.id.sleight_of_handButton), "sleightOfHand");
        setSkillButton(view.findViewById(R.id.stealthButton), "stealth");
        setSkillButton(view.findViewById(R.id.survivalButton), "survival");
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
        setAbility(view.findViewById(R.id.strengthScore), view.findViewById(R.id.strengthModifier), thisCharacter.getStrengthScore(), thisCharacter.getStrengthMod());
        setAbility(view.findViewById(R.id.dexterityScore), view.findViewById(R.id.dexterityModifier), thisCharacter.getDexterityScore(), thisCharacter.getDexterityMod());
        setAbility(view.findViewById(R.id.constitutionScore), view.findViewById(R.id.constitutionModifier), thisCharacter.getConstitutionScore(), thisCharacter.getConstitutionMod());
        setAbility(view.findViewById(R.id.intelligenceScore), view.findViewById(R.id.intelligenceModifier), thisCharacter.getIntelligenceScore(), thisCharacter.getIntelligenceMod());
        setAbility(view.findViewById(R.id.wisdomScore), view.findViewById(R.id.wisdomModifier), thisCharacter.getWisdomScore(), thisCharacter.getWisdomMod());
        setAbility(view.findViewById(R.id.charismaScore), view.findViewById(R.id.charismaModifier), thisCharacter.getCharismaScore(), thisCharacter.getCharismaMod());
    }

    private void setSaves() {
        setModifierView(view.findViewById(R.id.strength_save), thisCharacter.getStrengthSave());
        setModifierView(view.findViewById(R.id.dexterity_save), thisCharacter.getDexteritySave());
        setModifierView(view.findViewById(R.id.constitution_save), thisCharacter.getConstitutionSave());
        setModifierView(view.findViewById(R.id.intelligence_save), thisCharacter.getIntelligenceSave());
        setModifierView(view.findViewById(R.id.wisdom_save), thisCharacter.getWisdomSave());
        setModifierView(view.findViewById(R.id.charisma_save), thisCharacter.getCharismaSave());
    }

    private void setSkills() {
        setModifierView(view.findViewById(R.id.acrobatics), thisCharacter.getAcrobatics());
        setModifierView(view.findViewById(R.id.animal_handling), thisCharacter.getAnimalHandling());
        setModifierView(view.findViewById(R.id.arcana), thisCharacter.getArcana());
        setModifierView(view.findViewById(R.id.athletics), thisCharacter.getAthletics());
        setModifierView(view.findViewById(R.id.deception), thisCharacter.getDeception());
        setModifierView(view.findViewById(R.id.history), thisCharacter.getHistory());
        setModifierView(view.findViewById(R.id.insight), thisCharacter.getInsight());
        setModifierView(view.findViewById(R.id.intimidation), thisCharacter.getIntimidation());
        setModifierView(view.findViewById(R.id.investigation), thisCharacter.getInvestigation());
        setModifierView(view.findViewById(R.id.medicine), thisCharacter.getMedicine());
        setModifierView(view.findViewById(R.id.nature), thisCharacter.getNature());
        setModifierView(view.findViewById(R.id.perception), thisCharacter.getPerception());
        setModifierView(view.findViewById(R.id.performance), thisCharacter.getPerformance());
        setModifierView(view.findViewById(R.id.persuasion), thisCharacter.getPersuasion());
        setModifierView(view.findViewById(R.id.religion), thisCharacter.getReligion());
        setModifierView(view.findViewById(R.id.sleight_of_hand), thisCharacter.getSleightOfHand());
        setModifierView(view.findViewById(R.id.stealth), thisCharacter.getStealth());
        setModifierView(view.findViewById(R.id.survival), thisCharacter.getSurvival());
    }

    @SuppressLint("SetTextI18n")
    private void setModifierView(TextView textView, int value) {
        if (value >= 0) { textView.setText("+" + value); }
        else { textView.setText(String.valueOf(value)); }

        textView.setOnClickListener(view -> openRoller(value));
    }

    @SuppressLint("SetTextI18n")
    private void setAbility(TextView score, TextView modifier, int scoreValue, int modValue) {
        score.setText(String.valueOf(scoreValue));
        setModifierView(modifier, modValue);
    }

    private void openRoller(int mod) {
        int[] rolls = {1};
        Dialog dialog = new Dialog(getContext());
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

        return rollString(roll, mod);
    }

    private String rollString(int roll, int mod){
        StringBuilder string = new StringBuilder();
        string.append("You rolled ");
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
