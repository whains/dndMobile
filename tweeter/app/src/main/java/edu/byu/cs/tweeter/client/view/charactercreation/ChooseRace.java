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
import edu.byu.cs.tweeter.client.view.encyclopedia.EncyclopediaLandingPage;

public class ChooseRace extends Fragment {
    String chosenRace = "";
    Boolean choosen = false;

    RelativeLayout human;
    Boolean humanOn = false;
    Integer humanId = 1231;
    TextView humanTextView;

    RelativeLayout elf;
    Boolean elfOn = false;
    Integer elfID = 1232;
    TextView elfTextView;

    RelativeLayout halfelf;
    Boolean halfelfOn = false;
    Integer halfelfID = 1233;
    TextView halfelfTextView;

    RelativeLayout dwarf;
    Boolean dwarfOn = false;
    Integer dwarfID = 1234;
    TextView dwarfTextView;

    RelativeLayout halfling;
    Boolean halflingOn = false;
    Integer halflingID = 1235;
    TextView halflingTextView;

    RelativeLayout gnome;
    Boolean gnomeOn = false;
    Integer gnomeID = 1236;
    TextView gnomeTextView;

    RelativeLayout dragonborn;
    Boolean dragonbornOn = false;
    Integer dragonbornID = 1237;
    TextView dragonbornTextView;

    RelativeLayout tiefling;
    Boolean tieflingOn = false;
    Integer tieflingID = 1238;
    TextView tieflingTextView;

    RelativeLayout halforc;
    Boolean halforcOn = false;
    Integer halforcID = 1239;
    TextView halforcTextView;

    RelativeLayout aarakocra;
    Boolean aarakocraOn = false;
    Integer aarakocraID = 1240;
    TextView aarakocraTextView;

    RelativeLayout encyclopedia;
    LinearLayout continueFloating;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.character_creation_choose_race, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        humanTextView = getView().findViewById(R.id.humanWords);
        human = getView().findViewById(R.id.human);
        human.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!humanOn && !choosen) {
                    humanOn = true;
                    choosen = true;
                    chosenRace = "Human";
                    Toast.makeText(getContext(), chosenRace + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(humanId);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.human);
                    human.addView(imageView, params);
                    human.removeView(getView().findViewById(R.id.humanWords));
                } else if (humanOn) {
                    chosenRace = "";
                    humanOn = false;
                    choosen = false;
                    human.addView(humanTextView);
                    human.removeView(getView().findViewById(humanId));
                }
            }
        });

        elfTextView = getView().findViewById(R.id.elfWords);
        elf = getView().findViewById(R.id.elf);
        elf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!elfOn && !choosen) {
                    elfOn = true;
                    choosen = true;
                    chosenRace = "Elf";
                    Toast.makeText(getContext(), chosenRace + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(elfID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.elf);
                    elf.addView(imageView, params);
                    elf.removeView(getView().findViewById(R.id.elfWords));
                } else if (elfOn){
                    chosenRace = "";
                    elfOn = false;
                    choosen = false;
                    elf.addView(elfTextView);
                    elf.removeView(getView().findViewById(elfID));
                }
            }
        });

        halfelfTextView = getView().findViewById(R.id.halfelfWords);
        halfelf = getView().findViewById(R.id.halfelf);
        halfelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!halfelfOn && !choosen) {
                    halfelfOn = true;
                    choosen = true;
                    chosenRace = "Half-Elf";
                    Toast.makeText(getContext(), chosenRace + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(halfelfID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.halfelf);
                    halfelf.addView(imageView, params);
                    halfelf.removeView(getView().findViewById(R.id.halfelfWords));
                } else if (halfelfOn){
                    chosenRace = "";
                    halfelfOn = false;
                    choosen = false;
                    halfelf.addView(halfelfTextView);
                    halfelf.removeView(getView().findViewById(halfelfID));
                }
            }
        });

        dwarfTextView = getView().findViewById(R.id.dwardWords);
        dwarf = getView().findViewById(R.id.dwarf);
        dwarf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dwarfOn && !choosen) {
                    dwarfOn = true;
                    choosen = true;
                    chosenRace = "Dwarf";
                    Toast.makeText(getContext(), chosenRace + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(dwarfID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.dwarf);
                    dwarf.addView(imageView, params);
                    dwarf.removeView(getView().findViewById(R.id.dwardWords));
                } else if (dwarfOn){
                    chosenRace = "";
                    dwarfOn = false;
                    choosen = false;
                    dwarf.addView(dwarfTextView);
                    dwarf.removeView(getView().findViewById(dwarfID));
                }
            }
        });

        halflingTextView = getView().findViewById(R.id.halflingWords);
        halfling = getView().findViewById(R.id.halfling);
        halfling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!halflingOn && !choosen) {
                    halflingOn = true;
                    choosen = true;
                    chosenRace = "Halfling";
                    Toast.makeText(getContext(), chosenRace + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(halflingID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.halfling);
                    halfling.addView(imageView, params);
                    halfling.removeView(getView().findViewById(R.id.halflingWords));
                } else if (halflingOn){
                    chosenRace = "";
                    halflingOn = false;
                    choosen = false;
                    halfling.addView(halflingTextView);
                    halfling.removeView(getView().findViewById(halflingID));
                }
            }
        });

        gnomeTextView = getView().findViewById(R.id.gnomeWords);
        gnome = getView().findViewById(R.id.gnome);
        gnome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gnomeOn && !choosen) {
                    gnomeOn = true;
                    choosen = true;
                    chosenRace = "Gnome";
                    Toast.makeText(getContext(), chosenRace + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(gnomeID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.gnome);
                    gnome.addView(imageView, params);
                    gnome.removeView(getView().findViewById(R.id.gnomeWords));
                } else if (gnomeOn){
                    chosenRace = "";
                    gnomeOn = false;
                    choosen = false;
                    gnome.addView(gnomeTextView);
                    gnome.removeView(getView().findViewById(gnomeID));
                }
            }
        });

        dragonbornTextView = getView().findViewById(R.id.dragonbornWords);
        dragonborn = getView().findViewById(R.id.dragonborn);
        dragonborn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dragonbornOn && !choosen) {
                    dragonbornOn = true;
                    choosen = true;
                    chosenRace = "Dragonborn";
                    Toast.makeText(getContext(), chosenRace + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(dragonbornID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.dragonborn);
                    dragonborn.addView(imageView, params);
                    dragonborn.removeView(getView().findViewById(R.id.dragonbornWords));
                } else if (dragonbornOn){
                    chosenRace = "";
                    dragonbornOn = false;
                    choosen = false;
                    dragonborn.addView(dragonbornTextView);
                    dragonborn.removeView(getView().findViewById(dragonbornID));
                }
            }
        });

        tieflingTextView = getView().findViewById(R.id.tieflingWords);
        tiefling = getView().findViewById(R.id.tiefling);
        tiefling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tieflingOn && !choosen) {
                    tieflingOn = true;
                    choosen = true;
                    chosenRace = "Tiefling";
                    Toast.makeText(getContext(), chosenRace + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(tieflingID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.tiefling);
                    tiefling.addView(imageView, params);
                    tiefling.removeView(getView().findViewById(R.id.tieflingWords));
                } else if (tieflingOn){
                    chosenRace = "";
                    tieflingOn = false;
                    choosen = false;
                    tiefling.addView(tieflingTextView);
                    tiefling.removeView(getView().findViewById(tieflingID));
                }
            }
        });

        halforcTextView = getView().findViewById(R.id.halforcWords);
        halforc = getView().findViewById(R.id.halforc);
        halforc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!halforcOn && !choosen) {
                    halforcOn = true;
                    choosen = true;
                    chosenRace = "Half-Orc";
                    Toast.makeText(getContext(), chosenRace + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(halforcID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.halforc);
                    halforc.addView(imageView, params);
                    halforc.removeView(getView().findViewById(R.id.halforcWords));
                } else if (halforcOn){
                    chosenRace = "";
                    halforcOn = false;
                    choosen = false;
                    halforc.addView(halforcTextView);
                    halforc.removeView(getView().findViewById(halforcID));
                }
            }
        });

        aarakocraTextView = getView().findViewById(R.id.aarakocraWords);
        aarakocra = getView().findViewById(R.id.aarakocra);
        aarakocra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!aarakocraOn && !choosen) {
                    aarakocraOn = true;
                    choosen = true;
                    chosenRace = "Aarakocra";
                    Toast.makeText(getContext(), chosenRace + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(aarakocraID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.aarakocra);
                    aarakocra.addView(imageView, params);
                    aarakocra.removeView(getView().findViewById(R.id.aarakocraWords));
                } else if (aarakocraOn){
                    chosenRace = "";
                    aarakocraOn = false;
                    choosen = false;
                    aarakocra.addView(aarakocraTextView);
                    aarakocra.removeView(getView().findViewById(aarakocraID));
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
                    creationMainActivity.switchToClassFragment(chosenRace);
                } else {
                    Toast.makeText(getContext(), "You must choose a race", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}