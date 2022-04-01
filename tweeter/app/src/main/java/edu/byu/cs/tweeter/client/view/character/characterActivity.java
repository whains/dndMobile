package edu.byu.cs.tweeter.client.view.character;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import edu.byu.cs.client.R;

public class characterActivity extends AppCompatActivity {
    private static FragmentManager manager;
    private Fragment characterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_page);

        manager = getSupportFragmentManager();

        characterFragment = manager.findFragmentById(R.id.characterPage);

        manager.beginTransaction().add(R.id.characterPage, characterFragment).commit();
    }
}
