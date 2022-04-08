package edu.byu.cs.tweeter.client.view.character;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.login.LoginSectionsPagerAdapter;
import edu.byu.cs.tweeter.model.domain.Character;

public class baseActivity extends AppCompatActivity {
    Cache cache = Cache.getInstance();
    Character thisCharacter;
    private static FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_base);

        thisCharacter = cache.getCharacter(getIntent().getExtras().getString("characterID"));

        CharacterSectionsPagerAdapter c = new CharacterSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.character_viewer);
        viewPager.setAdapter(c);
        //TabLayout tabs = findViewById(R.id.loginTabs);
        //tabs.setupWithViewPager(viewPager);
    }



}
