package edu.byu.cs.tweeter.client.view.encyclopedia.monsters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.view.encyclopedia.EncyclopediaLandingPage;
import edu.byu.cs.tweeter.client.view.encyclopedia.weapons.Melee;
import edu.byu.cs.tweeter.client.view.main.LandingPageActivity;

public class Animals extends AppCompatActivity {
    ImageView encyclopediaIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monsters_animals);

        encyclopediaIcon = findViewById(R.id.encyclopediaIcon);
        encyclopediaIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Animals.this, EncyclopediaLandingPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        ImageView home = findViewById(R.id.logo);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Animals.this, LandingPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
