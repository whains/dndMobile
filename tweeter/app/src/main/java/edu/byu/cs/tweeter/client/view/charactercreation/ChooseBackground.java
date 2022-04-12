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

public class ChooseBackground extends Fragment {

    String chosenBackground = "";
    Boolean choosen = false;

    RelativeLayout acolyte;
    Boolean acolyteOn = false;
    Integer acolyteId = 3231;
    TextView acolyteTextView;

    RelativeLayout charlatan;
    Boolean charlatanOn = false;
    Integer charlatanID = 3232;
    TextView charlatanTextView;

    RelativeLayout criminal;
    Boolean criminalOn = false;
    Integer criminalID = 3233;
    TextView criminalTextView;

    RelativeLayout hermit;
    Boolean hermitOn = false;
    Integer hermitID = 3234;
    TextView hermitTextView;

    RelativeLayout noble;
    Boolean nobleOn = false;
    Integer nobleID = 3235;
    TextView nobleTextView;

    RelativeLayout outlander;
    Boolean outlanderOn = false;
    Integer outlanderID = 3236;
    TextView outlanderTextView;

    RelativeLayout sage;
    Boolean sageOn = false;
    Integer sageID = 3237;
    TextView sageTextView;

    RelativeLayout sailor;
    Boolean sailorOn = false;
    Integer sailorID = 3238;
    TextView sailorTextView;

    RelativeLayout soldier;
    Boolean soldierOn = false;
    Integer soldierID = 3239;
    TextView soldierTextView;

    RelativeLayout urchin;
    Boolean urchinOn = false;
    Integer urchinID = 3240;
    TextView urchinTextView;

    RelativeLayout encyclopedia;
    LinearLayout continueFloating;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.character_creation_choose_background, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        acolyteTextView = getView().findViewById(R.id.acolyteWords);
        acolyte = getView().findViewById(R.id.acolyte);
        acolyte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!acolyteOn && !choosen) {
                    acolyteOn = true;
                    choosen = true;
                    chosenBackground = "Acolyte";
                    Toast.makeText(getContext(), chosenBackground + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(acolyteId);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.acolyte);
                    acolyte.addView(imageView, params);
                    acolyte.removeView(getView().findViewById(R.id.acolyteWords));
                } else if (acolyteOn) {
                    chosenBackground = "";
                    acolyteOn = false;
                    choosen = false;
                    acolyte.addView(acolyteTextView);
                    acolyte.removeView(getView().findViewById(acolyteId));
                }
            }
        });

        charlatanTextView = getView().findViewById(R.id.charlatanWords);
        charlatan = getView().findViewById(R.id.charlatan);
        charlatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!charlatanOn && !choosen) {
                    charlatanOn = true;
                    choosen = true;
                    chosenBackground = "Charlatan";
                    Toast.makeText(getContext(), chosenBackground + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(charlatanID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.charlatan);
                    charlatan.addView(imageView, params);
                    charlatan.removeView(getView().findViewById(R.id.charlatanWords));
                } else if (charlatanOn){
                    chosenBackground = "";
                    charlatanOn = false;
                    choosen = false;
                    charlatan.addView(charlatanTextView);
                    charlatan.removeView(getView().findViewById(charlatanID));
                }
            }
        });

        criminalTextView = getView().findViewById(R.id.criminalWords);
        criminal = getView().findViewById(R.id.criminal);
        criminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!criminalOn && !choosen) {
                    criminalOn = true;
                    choosen = true;
                    chosenBackground = "Criminal";
                    Toast.makeText(getContext(), chosenBackground + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(criminalID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.criminal);
                    criminal.addView(imageView, params);
                    criminal.removeView(getView().findViewById(R.id.criminalWords));
                } else if (criminalOn){
                    chosenBackground = "";
                    criminalOn = false;
                    choosen = false;
                    criminal.addView(criminalTextView);
                    criminal.removeView(getView().findViewById(criminalID));
                }
            }
        });

        hermitTextView = getView().findViewById(R.id.hermitWords);
        hermit = getView().findViewById(R.id.hermit);
        hermit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hermitOn && !choosen) {
                    hermitOn = true;
                    choosen = true;
                    chosenBackground = "Hermit";
                    Toast.makeText(getContext(), chosenBackground + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(hermitID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.hermit);
                    hermit.addView(imageView, params);
                    hermit.removeView(getView().findViewById(R.id.hermitWords));
                } else if (hermitOn){
                    chosenBackground = "";
                    hermitOn = false;
                    choosen = false;
                    hermit.addView(hermitTextView);
                    hermit.removeView(getView().findViewById(hermitID));
                }
            }
        });

        nobleTextView = getView().findViewById(R.id.nobleWords);
        noble = getView().findViewById(R.id.noble);
        noble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nobleOn && !choosen) {
                    nobleOn = true;
                    choosen = true;
                    chosenBackground = "Noble";
                    Toast.makeText(getContext(), chosenBackground + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(nobleID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.noble);
                    noble.addView(imageView, params);
                    noble.removeView(getView().findViewById(R.id.nobleWords));
                } else if (nobleOn){
                    chosenBackground = "";
                    nobleOn = false;
                    choosen = false;
                    noble.addView(nobleTextView);
                    noble.removeView(getView().findViewById(nobleID));
                }
            }
        });

        outlanderTextView = getView().findViewById(R.id.outlanderWords);
        outlander = getView().findViewById(R.id.outlander);
        outlander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!outlanderOn && !choosen) {
                    outlanderOn = true;
                    choosen = true;
                    chosenBackground = "Outlander";
                    Toast.makeText(getContext(), chosenBackground + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(outlanderID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.outlander);
                    outlander.addView(imageView, params);
                    outlander.removeView(getView().findViewById(R.id.outlanderWords));
                } else if (outlanderOn){
                    chosenBackground = "";
                    outlanderOn = false;
                    choosen = false;
                    outlander.addView(outlanderTextView);
                    outlander.removeView(getView().findViewById(outlanderID));
                }
            }
        });

        sageTextView = getView().findViewById(R.id.sageWords);
        sage = getView().findViewById(R.id.sage);
        sage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sageOn && !choosen) {
                    sageOn = true;
                    choosen = true;
                    chosenBackground = "Sage";
                    Toast.makeText(getContext(), chosenBackground + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(sageID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.sage);
                    sage.addView(imageView, params);
                    sage.removeView(getView().findViewById(R.id.sageWords));
                } else if (sageOn){
                    chosenBackground = "";
                    sageOn = false;
                    choosen = false;
                    sage.addView(sageTextView);
                    sage.removeView(getView().findViewById(sageID));
                }
            }
        });

        sailorTextView = getView().findViewById(R.id.sailorWords);
        sailor = getView().findViewById(R.id.sailor);
        sailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sailorOn && !choosen) {
                    sailorOn = true;
                    choosen = true;
                    chosenBackground = "Sailor";
                    Toast.makeText(getContext(), chosenBackground + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(sailorID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.sailor);
                    sailor.addView(imageView, params);
                    sailor.removeView(getView().findViewById(R.id.sailorWords));
                } else if (sailorOn){
                    chosenBackground = "";
                    sailorOn = false;
                    choosen = false;
                    sailor.addView(sailorTextView);
                    sailor.removeView(getView().findViewById(sailorID));
                }
            }
        });

        soldierTextView = getView().findViewById(R.id.soldierWords);
        soldier = getView().findViewById(R.id.soldier);
        soldier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!soldierOn && !choosen) {
                    soldierOn = true;
                    choosen = true;
                    chosenBackground = "Soldier";
                    Toast.makeText(getContext(), chosenBackground + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(soldierID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.soldier);
                    soldier.addView(imageView, params);
                    soldier.removeView(getView().findViewById(R.id.soldierWords));
                } else if (soldierOn){
                    chosenBackground = "";
                    soldierOn = false;
                    choosen = false;
                    soldier.addView(soldierTextView);
                    soldier.removeView(getView().findViewById(soldierID));
                }
            }
        });

        urchinTextView = getView().findViewById(R.id.urchinWords);
        urchin = getView().findViewById(R.id.urchin);
        urchin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!urchinOn && !choosen) {
                    urchinOn = true;
                    choosen = true;
                    chosenBackground = "Urchin";
                    Toast.makeText(getContext(), chosenBackground + " Chosen", Toast.LENGTH_SHORT).show();
                    ImageView imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(R.drawable.ic_check);
                    imageView.setId(urchinID);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT );
                    params.addRule(RelativeLayout.BELOW, R.id.urchin);
                    urchin.addView(imageView, params);
                    urchin.removeView(getView().findViewById(R.id.urchinWords));
                } else if (urchinOn){
                    chosenBackground = "";
                    urchinOn = false;
                    choosen = false;
                    urchin.addView(urchinTextView);
                    urchin.removeView(getView().findViewById(urchinID));
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
                    creationMainActivity.switchToAlignmentFragment(chosenBackground);
                } else {
                    Toast.makeText(getContext(), "You must choose a background", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}