package edu.byu.cs.tweeter.client.view.charactercreation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.model.domain.Character;

public class CreationMainActivity extends AppCompatActivity {
    private FragmentManager fm = getSupportFragmentManager();
    private Character newCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_creation_main_activity);

        newCharacter = new Character();

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
        ChooseClass mapFragment = new ChooseClass();
        fm.beginTransaction()
                .replace(R.id.fragment_decider, mapFragment)
                .commit();
    }

    public void switchToBackgroundFragment(String firstClass) {
        newCharacter.setFirstClass(firstClass);
        ChooseClass mapFragment = new ChooseClass();
        fm.beginTransaction()
                .replace(R.id.fragment_decider, mapFragment)
                .commit();
    }
}
