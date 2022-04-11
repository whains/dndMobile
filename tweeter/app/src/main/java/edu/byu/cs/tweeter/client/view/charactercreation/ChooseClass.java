package edu.byu.cs.tweeter.client.view.charactercreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.view.character.characterSpells;
import edu.byu.cs.tweeter.client.view.encyclopedia.EncyclopediaLandingPage;

public class ChooseClass extends Fragment {

    String chosenClass = "";
    Boolean choosen = false;

    RelativeLayout barabarian;
    Boolean barabarianOn = false;
    Integer barabarianId = 2231;
    TextView barabarianTextView;

    RelativeLayout cleric;
    Boolean clericOn = false;
    Integer clericID = 2232;
    TextView clericTextView;

    RelativeLayout druid;
    Boolean druidOn = false;
    Integer druidID = 2233;
    TextView druidTextView;

    RelativeLayout fighter;
    Boolean fighterOn = false;
    Integer fighterID = 2234;
    TextView fighterTextView;

    RelativeLayout monk;
    Boolean monkOn = false;
    Integer monkID = 2235;
    TextView monkTextView;

    RelativeLayout paladin;
    Boolean paladinOn = false;
    Integer paladinID = 2236;
    TextView paladinTextView;

    RelativeLayout rogue;
    Boolean rogueOn = false;
    Integer rogueID = 2237;
    TextView rogueTextView;

    RelativeLayout warlock;
    Boolean warlockOn = false;
    Integer warlockID = 2238;
    TextView warlockTextView;

    RelativeLayout wizard;
    Boolean wizardOn = false;
    Integer wizardID = 2239;
    TextView wizardTextView;

    RelativeLayout bard;
    Boolean bardOn = false;
    Integer bardID = 2235;
    TextView bardTextView;

    RelativeLayout encyclopedia;
    LinearLayout continueFloating;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.character_creation_choose_class, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        barabarianTextView = getView().findViewById(R.id.barbarianWords);
        barabarian = getView().findViewById(R.id.barbarian);
        barabarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!barabarianOn && !choosen) {
                    barabarianOn = true;
                    choosen = true;
                    chosenClass = "Barbarian";
                    Toast.makeText(getContext(), chosenClass + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(barabarianId);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.barbarian);
                    barabarian.addView(imageView, params);
                    barabarian.removeView(getView().findViewById(R.id.barbarianWords));
                } else if (barabarianOn) {
                    chosenClass = "";
                    barabarianOn = false;
                    choosen = false;
                    barabarian.addView(barabarianTextView);
                    barabarian.removeView(getView().findViewById(barabarianId));
                }
            }
        });

        clericTextView = getView().findViewById(R.id.clericWords);
        cleric = getView().findViewById(R.id.cleric);
        cleric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clericOn && !choosen) {
                    clericOn = true;
                    choosen = true;
                    chosenClass = "Cleric";
                    Toast.makeText(getContext(), chosenClass + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(clericID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.cleric);
                    cleric.addView(imageView, params);
                    cleric.removeView(getView().findViewById(R.id.clericWords));
                } else if (clericOn){
                    chosenClass = "";
                    clericOn = false;
                    choosen = false;
                    cleric.addView(clericTextView);
                    cleric.removeView(getView().findViewById(clericID));
                }
            }
        });

        druidTextView = getView().findViewById(R.id.druidWords);
        druid = getView().findViewById(R.id.druid);
        druid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!druidOn && !choosen) {
                    druidOn = true;
                    choosen = true;
                    chosenClass = "Druid";
                    Toast.makeText(getContext(), chosenClass + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(druidID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.druid);
                    druid.addView(imageView, params);
                    druid.removeView(getView().findViewById(R.id.druidWords));
                } else if (druidOn){
                    chosenClass = "";
                    druidOn = false;
                    choosen = false;
                    druid.addView(druidTextView);
                    druid.removeView(getView().findViewById(druidID));
                }
            }
        });

        fighterTextView = getView().findViewById(R.id.fighterWords);
        fighter = getView().findViewById(R.id.fighter);
        fighter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fighterOn && !choosen) {
                    fighterOn = true;
                    choosen = true;
                    chosenClass = "Fighter";
                    Toast.makeText(getContext(), chosenClass + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(fighterID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.fighter);
                    fighter.addView(imageView, params);
                    fighter.removeView(getView().findViewById(R.id.fighterWords));
                } else if (fighterOn){
                    chosenClass = "";
                    fighterOn = false;
                    choosen = false;
                    fighter.addView(fighterTextView);
                    fighter.removeView(getView().findViewById(fighterID));
                }
            }
        });

        monkTextView = getView().findViewById(R.id.monkWords);
        monk = getView().findViewById(R.id.monk);
        monk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!monkOn && !choosen) {
                    monkOn = true;
                    choosen = true;
                    chosenClass = "Monk";
                    Toast.makeText(getContext(), chosenClass + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(monkID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.monk);
                    monk.addView(imageView, params);
                    monk.removeView(getView().findViewById(R.id.monkWords));
                } else if (monkOn){
                    chosenClass = "";
                    monkOn = false;
                    choosen = false;
                    monk.addView(monkTextView);
                    monk.removeView(getView().findViewById(monkID));
                }
            }
        });

        paladinTextView = getView().findViewById(R.id.paladinWords);
        paladin = getView().findViewById(R.id.paladin);
        paladin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!paladinOn && !choosen) {
                    paladinOn = true;
                    choosen = true;
                    chosenClass = "Paladin";
                    Toast.makeText(getContext(), chosenClass + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(paladinID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.paladin);
                    paladin.addView(imageView, params);
                    paladin.removeView(getView().findViewById(R.id.paladinWords));
                } else if (paladinOn){
                    chosenClass = "";
                    paladinOn = false;
                    choosen = false;
                    paladin.addView(paladinTextView);
                    paladin.removeView(getView().findViewById(paladinID));
                }
            }
        });

        rogueTextView = getView().findViewById(R.id.rogueWords);
        rogue = getView().findViewById(R.id.rogue);
        rogue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!rogueOn && !choosen) {
                    rogueOn = true;
                    choosen = true;
                    chosenClass = "Rogue";
                    Toast.makeText(getContext(), chosenClass + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(rogueID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.rogue);
                    rogue.addView(imageView, params);
                    rogue.removeView(getView().findViewById(R.id.rogueWords));
                } else if (rogueOn){
                    chosenClass = "";
                    rogueOn = false;
                    choosen = false;
                    rogue.addView(rogueTextView);
                    rogue.removeView(getView().findViewById(rogueID));
                }
            }
        });

        warlockTextView = getView().findViewById(R.id.warlockWords);
        warlock = getView().findViewById(R.id.warlock);
        warlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!warlockOn && !choosen) {
                    warlockOn = true;
                    choosen = true;
                    chosenClass = "Warlock";
                    Toast.makeText(getContext(), chosenClass + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(warlockID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.warlock);
                    warlock.addView(imageView, params);
                    warlock.removeView(getView().findViewById(R.id.warlockWords));
                } else if (warlockOn){
                    chosenClass = "";
                    warlockOn = false;
                    choosen = false;
                    warlock.addView(warlockTextView);
                    warlock.removeView(getView().findViewById(warlockID));
                }
            }
        });

        wizardTextView = getView().findViewById(R.id.wizardWords);
        wizard = getView().findViewById(R.id.wizard);
        wizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wizardOn && !choosen) {
                    wizardOn = true;
                    choosen = true;
                    chosenClass = "Wizard";
                    Toast.makeText(getContext(), chosenClass + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(wizardID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.wizard);
                    wizard.addView(imageView, params);
                    wizard.removeView(getView().findViewById(R.id.wizardWords));
                } else if (wizardOn){
                    chosenClass = "";
                    wizardOn = false;
                    choosen = false;
                    wizard.addView(wizardTextView);
                    wizard.removeView(getView().findViewById(wizardID));
                }
            }
        });

        bardTextView = getView().findViewById(R.id.bardWords);
        bard = getView().findViewById(R.id.bard);
        bard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bardOn && !choosen) {
                    bardOn = true;
                    choosen = true;
                    chosenClass = "Bard";
                    Toast.makeText(getContext(), chosenClass + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(bardID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.bard);
                    bard.addView(imageView, params);
                    bard.removeView(getView().findViewById(R.id.bardWords));
                } else if (bardOn){
                    chosenClass = "";
                    bardOn = false;
                    choosen = false;
                    bard.addView(bardTextView);
                    bard.removeView(getView().findViewById(bardID));
                }
            }
        });

        encyclopedia = getView().findViewById(R.id.chooseRaceEncyclopedia);
        encyclopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EncyclopediaLandingPage.class);
                startActivity(intent);
            }
        });

        continueFloating = getView().findViewById(R.id.raceContniue);
        continueFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choosen) {
                    CreationMainActivity creationMainActivity = (CreationMainActivity) getActivity();
                    creationMainActivity.switchToBackgroundFragment(chosenClass);
                } else {
                    Toast.makeText(getContext(), "You must choose a class", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
