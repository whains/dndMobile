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

public class ChooseAlignment extends Fragment {

    String chosenAlignment = "";
    Boolean choosen = false;

    RelativeLayout lawfulgood;
    Boolean lawfulgoodOn = false;
    Integer lawfulgoodId = 5231;
    TextView lawfulgoodTextView;

    RelativeLayout neutralgood;
    Boolean neutralgoodOn = false;
    Integer neutralgoodID = 5232;
    TextView neutralgoodTextView;

    RelativeLayout chaoticgood;
    Boolean chaoticgoodOn = false;
    Integer chaoticgoodID = 5233;
    TextView chaoticgoodTextView;

    RelativeLayout lawfulneutral;
    Boolean lawfulneutralOn = false;
    Integer lawfulneutralID = 5234;
    TextView lawfulneutralTextView;

    RelativeLayout trueneutral;
    Boolean trueneutralOn = false;
    Integer trueneutralID = 5235;
    TextView trueneutralTextView;

    RelativeLayout chaoticneutral;
    Boolean chaoticneutralOn = false;
    Integer chaoticneutralID = 5236;
    TextView chaoticneutralTextView;

    RelativeLayout lawfulevil;
    Boolean lawfulevilOn = false;
    Integer lawfulevilID = 5237;
    TextView lawfulevilTextView;

    RelativeLayout neutralevil;
    Boolean neutralevilOn = false;
    Integer neutralevilID = 5238;
    TextView neutralevilTextView;

    RelativeLayout chaoticevil;
    Boolean chaoticevilOn = false;
    Integer chaoticevilID = 5239;
    TextView chaoticevilTextView;

    RelativeLayout encyclopedia;
    LinearLayout continueFloating;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.character_creation_choose_alignment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lawfulgoodTextView = getView().findViewById(R.id.lawfulgoodWords);
        lawfulgood = getView().findViewById(R.id.lawfulgood);
        lawfulgood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lawfulgoodOn && !choosen) {
                    lawfulgoodOn = true;
                    choosen = true;
                    chosenAlignment = "Lawful Good";
                    Toast.makeText(getContext(), chosenAlignment + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(lawfulgoodId);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.lawfulgood);
                    lawfulgood.addView(imageView, params);
                    lawfulgood.removeView(getView().findViewById(R.id.lawfulgoodWords));
                } else if (lawfulgoodOn) {
                    chosenAlignment= "";
                    lawfulgoodOn = false;
                    choosen = false;
                    lawfulgood.addView(lawfulgoodTextView);
                    lawfulgood.removeView(getView().findViewById(lawfulgoodId));
                }
            }
        });

        neutralgoodTextView = getView().findViewById(R.id.neutralgoodWords);
        neutralgood = getView().findViewById(R.id.neutralgood);
        neutralgood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!neutralgoodOn && !choosen) {
                    neutralgoodOn = true;
                    choosen = true;
                    chosenAlignment = "Neutral Good";
                    Toast.makeText(getContext(), chosenAlignment + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(neutralgoodID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.neutralgood);
                    neutralgood.addView(imageView, params);
                    neutralgood.removeView(getView().findViewById(R.id.neutralgoodWords));
                } else if (neutralgoodOn){
                    chosenAlignment = "";
                    neutralgoodOn = false;
                    choosen = false;
                    neutralgood.addView(neutralgoodTextView);
                    neutralgood.removeView(getView().findViewById(neutralgoodID));
                }
            }
        });

        chaoticgoodTextView = getView().findViewById(R.id.chaoticgoodWords);
        chaoticgood = getView().findViewById(R.id.chaoticgood);
        chaoticgood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chaoticgoodOn && !choosen) {
                    chaoticgoodOn = true;
                    choosen = true;
                    chosenAlignment= "Chaotic Good";
                    Toast.makeText(getContext(), chosenAlignment+ " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(chaoticgoodID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.chaoticgood);
                    chaoticgood.addView(imageView, params);
                    chaoticgood.removeView(getView().findViewById(R.id.chaoticgoodWords));
                } else if (chaoticgoodOn){
                    chosenAlignment = "";
                    chaoticgoodOn = false;
                    choosen = false;
                    chaoticgood.addView(chaoticgoodTextView);
                    chaoticgood.removeView(getView().findViewById(chaoticgoodID));
                }
            }
        });

        lawfulneutralTextView = getView().findViewById(R.id.lawfulneutralWords);
        lawfulneutral = getView().findViewById(R.id.lawfulneutral);
        lawfulneutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lawfulneutralOn && !choosen) {
                    lawfulneutralOn = true;
                    choosen = true;
                    chosenAlignment = "Lawful Neutral";
                    Toast.makeText(getContext(), chosenAlignment + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(lawfulneutralID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.lawfulneutral);
                    lawfulneutral.addView(imageView, params);
                    lawfulneutral.removeView(getView().findViewById(R.id.lawfulneutralWords));
                } else if (lawfulneutralOn){
                    chosenAlignment = "";
                    lawfulneutralOn = false;
                    choosen = false;
                    lawfulneutral.addView(lawfulneutralTextView);
                    lawfulneutral.removeView(getView().findViewById(lawfulneutralID));
                }
            }
        });

        trueneutralTextView = getView().findViewById(R.id.trueneutralWords);
        trueneutral = getView().findViewById(R.id.trueneutral);
        trueneutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!trueneutralOn && !choosen) {
                    trueneutralOn = true;
                    choosen = true;
                    chosenAlignment = "True Neutral";
                    Toast.makeText(getContext(), chosenAlignment + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(trueneutralID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.trueneutral);
                    trueneutral.addView(imageView, params);
                    trueneutral.removeView(getView().findViewById(R.id.trueneutralWords));
                } else if (trueneutralOn){
                    chosenAlignment = "";
                    trueneutralOn = false;
                    choosen = false;
                    trueneutral.addView(trueneutralTextView);
                    trueneutral.removeView(getView().findViewById(trueneutralID));
                }
            }
        });

        chaoticneutralTextView = getView().findViewById(R.id.chaoticneutralWords);
        chaoticneutral = getView().findViewById(R.id.chaoticneutral);
        chaoticneutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chaoticneutralOn && !choosen) {
                    chaoticneutralOn = true;
                    choosen = true;
                    chosenAlignment = "Chaotic Neutral";
                    Toast.makeText(getContext(), chosenAlignment + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(chaoticneutralID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.chaoticneutral);
                    chaoticneutral.addView(imageView, params);
                    chaoticneutral.removeView(getView().findViewById(R.id.chaoticneutralWords));
                } else if (chaoticneutralOn){
                    chosenAlignment = "";
                    chaoticneutralOn = false;
                    choosen = false;
                    chaoticneutral.addView(chaoticneutralTextView);
                    chaoticneutral.removeView(getView().findViewById(chaoticneutralID));
                }
            }
        });

        lawfulevilTextView = getView().findViewById(R.id.lawfulevilWords);
        lawfulevil = getView().findViewById(R.id.lawfulevil);
        lawfulevil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lawfulevilOn && !choosen) {
                    lawfulevilOn = true;
                    choosen = true;
                    chosenAlignment = "Lawful Evil";
                    Toast.makeText(getContext(), chosenAlignment + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(lawfulevilID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.lawfulevil);
                    lawfulevil.addView(imageView, params);
                    lawfulevil.removeView(getView().findViewById(R.id.lawfulevilWords));
                } else if (lawfulevilOn){
                    chosenAlignment = "";
                    lawfulevilOn = false;
                    choosen = false;
                    lawfulevil.addView(lawfulevilTextView);
                    lawfulevil.removeView(getView().findViewById(lawfulevilID));
                }
            }
        });

        neutralevilTextView = getView().findViewById(R.id.neutralevilWords);
        neutralevil = getView().findViewById(R.id.neutralevil);
        neutralevil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!neutralevilOn && !choosen) {
                    neutralevilOn = true;
                    choosen = true;
                    chosenAlignment = "Neutral Evil";
                    Toast.makeText(getContext(), chosenAlignment + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(neutralevilID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.neutralevil);
                    neutralevil.addView(imageView, params);
                    neutralevil.removeView(getView().findViewById(R.id.neutralevilWords));
                } else if (neutralevilOn){
                    chosenAlignment = "";
                    neutralevilOn = false;
                    choosen = false;
                    neutralevil.addView(neutralevilTextView);
                    neutralevil.removeView(getView().findViewById(neutralevilID));
                }
            }
        });

        chaoticevilTextView = getView().findViewById(R.id.chaoticevilWords);
        chaoticevil = getView().findViewById(R.id.chaoticevil);
        chaoticevil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chaoticevilOn && !choosen) {
                    chaoticevilOn = true;
                    choosen = true;
                    chosenAlignment = "Chaotic Evil";
                    Toast.makeText(getContext(), chosenAlignment + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(chaoticevilID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.chaoticevil);
                    chaoticevil.addView(imageView, params);
                    chaoticevil.removeView(getView().findViewById(R.id.chaoticevilWords));
                } else if (chaoticevilOn){
                    chosenAlignment = "";
                    chaoticevilOn = false;
                    choosen = false;
                    chaoticevil.addView(chaoticevilTextView);
                    chaoticevil.removeView(getView().findViewById(chaoticevilID));
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
                    creationMainActivity.switchToNameFragment(chosenAlignment);
                } else {
                    Toast.makeText(getContext(), "You must choose an alignment", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
