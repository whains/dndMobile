package edu.byu.cs.tweeter.client.view.charactercreation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.view.character.characterSpells;

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
    Integer aarakocraID = 1235;
    TextView aarakocraTextView;

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
                    Toast.makeText(getContext(), chosenRace, Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getContext(), chosenRace, Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getContext(), chosenRace, Toast.LENGTH_LONG).show();
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
    }

    public  static characterSpells newInstance() {
        characterSpells fragment = new characterSpells();
        return fragment;
    }
}