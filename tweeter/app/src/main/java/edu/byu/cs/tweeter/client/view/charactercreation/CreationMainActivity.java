package edu.byu.cs.tweeter.client.view.charactercreation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.character.characterMain;
import edu.byu.cs.tweeter.client.view.encyclopedia.EncyclopediaLandingPage;
import edu.byu.cs.tweeter.client.view.encyclopedia.classes.Druid;
import edu.byu.cs.tweeter.client.view.encyclopedia.weapons.Melee;
import edu.byu.cs.tweeter.client.view.main.LandingPageActivity;
import edu.byu.cs.tweeter.model.domain.Character;

public class CreationMainActivity extends AppCompatActivity {
    private FragmentManager fm = getSupportFragmentManager();
    private Character newCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_creation_main_activity);

        newCharacter = new Character();

        ImageView encyclopediaIcon = findViewById(R.id.encyclopediaIcon);
        encyclopediaIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreationMainActivity.this, EncyclopediaLandingPage.class);
                startActivity(intent);
            }
        });

        ImageView home = findViewById(R.id.logo);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreationMainActivity.this, LandingPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //Iconify.with(new FontAwesomeModule());

        Fragment fragment = fm.findFragmentById(R.id.fragment_decider);

        if (fragment == null) {
            fragment  = new ChooseRace();
            fm.beginTransaction()
                    .add(R.id.fragment_decider, fragment)
                    .commit();
        }
    }

    public void switchToClassFragment(String race) {
        newCharacter.setRace(race);
        ChooseClass classFragment = new ChooseClass();
        fm.beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                .replace(R.id.fragment_decider, classFragment)
                .commit();
    }

    public void switchToBackgroundFragment(String firstClass) {
        newCharacter.setFirstClass(firstClass);
        ChooseBackground mapFragment = new ChooseBackground();
        fm.beginTransaction()
        .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                R.anim.enter_left_to_right, R.anim.exit_left_to_right)
            .replace(R.id.fragment_decider, mapFragment)
                .commit();
    }


    public void switchToAlignmentFragment(String background) {
        newCharacter.setBackground(background);
        ChooseAlignment mapFragment = new ChooseAlignment();
        fm.beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                .replace(R.id.fragment_decider, mapFragment)
                .commit();
    }

    public void switchToNameFragment(String alignment) {
        newCharacter.setAlignment(alignment);
        ChooseName mapFragment = new ChooseName();
        fm.beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right, R.anim.exit_left_to_right)
                .replace(R.id.fragment_decider, mapFragment)
                .commit();
    }

    public void createCharacter(String name) {
        newCharacter.setCharacterName(name);
        Cache cache = Cache.getInstance();
        cache.addCharacter(newCharacter);
        cache.addNotification("Created New Character!");
        cache.addSubNotification(name + ": Level 1 " + newCharacter.getRace() + " " + newCharacter.getFirstClass() +
                " " + newCharacter.getBackground());
        /*Intent intent = new Intent(CreationMainActivity.this, characterMain.class);
        intent.putExtra("characterID", newCharacter.getCharacterID());
        startActivity(intent);*/
        Intent intent = new Intent(CreationMainActivity.this, LandingPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
