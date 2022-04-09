package edu.byu.cs.tweeter.client.view.character;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.encyclopedia.EncyclopediaLandingPage;
import edu.byu.cs.tweeter.client.view.login.LoginSectionsPagerAdapter;
import edu.byu.cs.tweeter.client.view.main.LandingPageActivity;
import edu.byu.cs.tweeter.model.domain.Character;

public class baseActivity extends AppCompatActivity {
    Cache cache = Cache.getInstance();
    Character thisCharacter;
    private static FragmentManager manager;
    ImageView encyclopediaIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_base);

        thisCharacter = cache.getCharacter(getIntent().getExtras().getString("characterID"));
        CharacterSectionsPagerAdapter c = new CharacterSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.character_viewer);
        viewPager.setAdapter(c);
        TabLayout tabs = findViewById(R.id.characterTabs);
        tabs.setupWithViewPager(viewPager);
        encyclopediaIcon = findViewById(R.id.encyclopediaIcon);
        encyclopediaIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baseActivity.this, EncyclopediaLandingPage.class);
                startActivity(intent);
            }
        });
    }



}
