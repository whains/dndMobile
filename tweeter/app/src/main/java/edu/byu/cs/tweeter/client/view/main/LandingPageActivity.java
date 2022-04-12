package edu.byu.cs.tweeter.client.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.character.baseActivity;
import edu.byu.cs.tweeter.client.view.character.characterMain;
import edu.byu.cs.tweeter.client.view.charactercreation.CharacterCreationLandingPage;
import edu.byu.cs.tweeter.client.view.charactercreation.ChooseRace;
import edu.byu.cs.tweeter.client.view.encyclopedia.EncyclopediaLandingPage;
import edu.byu.cs.tweeter.model.domain.Character;

public class LandingPageActivity extends AppCompatActivity {

    public static final String CURRENT_USER_KEY = "CurrentUser";
    Cache cache = Cache.getInstance();
    ArrayList<Character> characters = new ArrayList<>();
    ImageView encyclopediaIcon;
    ImageView notificationIcon;

    FragmentManager fm;
    Fragment fragment;

    public  ArrayList<Character> getCharacters() {
        return characters;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (cache.numCharacters() == 0) {
            Character Jandar = new Character();
            Jandar.setCharacterName("Jandar, the Forgotten");
            Jandar.setRace("High Elf");
            Jandar.setFirstClass("Ranger");
            Jandar.setBackground("Noble");
            Jandar.setAlignment("Lawful Good");

            Jandar.setStrengthScore(10);
            Jandar.setDexterityScore(20);
            Jandar.setConstitutionScore(14);
            Jandar.setIntelligenceScore(14);
            Jandar.setWisdomScore(15);
            Jandar.setCharismaScore(8);

            Jandar.setProficient("animalHandling", true);
            Jandar.setProficient("insight", true);
            Jandar.setProficient("survival", true);

            for (int i = 0; i < 9; i++) {
                Jandar.rangerUp();
            }

            /*
            Character Jack = new Character();
            Jack.setCharacterName("Jack D'al");
            Jack.setRace("Half-Elf");
            Jack.setFirstClass("Fighter");
            Jack.setBackground("Entertainer");
            Jack.setAlignment("Chaotic Neutral");

            Jack.setStrengthScore(13);
            Jack.setDexterityScore(13);
            Jack.setConstitutionScore(13);
            Jack.setIntelligenceScore(13);
            Jack.setWisdomScore(13);
            Jack.setCharismaScore(13);

            Jack.barbarianUp();
            Jack.bardUp();
            Jack.clericUp();
            Jack.druidUp();
            Jack.monkUp();
            Jack.paladinUp();
            Jack.rangerUp();
            Jack.rogueUp();
            Jack.sorcererUp();
            Jack.warlockUp();
            Jack.wizardUp();

            Jack.setProficient("acrobatics", true);
            Jack.setProficient("arcana", true);
            Jack.setProficient("athletics", true);
            Jack.setProficient("deception", true);
            Jack.setProficient("history", true);
            Jack.setProficient("insight", true);
            Jack.setProficient("intimidation", true);
            Jack.setProficient("nature", true);
            Jack.setProficient("perception", true);
            Jack.setProficient("persuasion", true);
            Jack.setProficient("religion", true);
            Jack.setProficient("stealth", true);
            */

            Character Aldor = new Character();
            Aldor.setCharacterName("Aldor Dianto");
            Aldor.setRace("Human");
            Aldor.setFirstClass("Warlock");
            Aldor.setBackground("Charlatan");
            Aldor.setAlignment("Lawful Evil");

            Aldor.setStrengthScore(11);
            Aldor.setDexterityScore(13);
            Aldor.setConstitutionScore(20);
            Aldor.setIntelligenceScore(11);
            Aldor.setWisdomScore(10);
            Aldor.setCharismaScore(20);

            Aldor.setProficient("arcana", true);
            Aldor.setProficient("intimidation", true);

            cache.addCharacter(Jandar);
            //cache.addCharacter(Jack);
            cache.addCharacter(Aldor);


            characters.add(Jandar);
            //characters.add(Jack);
            characters.add(Aldor);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        /*findViewById(R.id.newCharacter).setOnClickListener(view -> {
            Character newCharacter = new Character();
            characters.add(newCharacter);
            cache.addCharacter(newCharacter);
            Intent intent = new Intent(LandingPageActivity.this, characterMain.class);
            intent.putExtra("characterID", newCharacter.getCharacterID());
            startActivity(intent);
        });*/
        encyclopediaIcon = findViewById(R.id.encyclopediaIcon);
        encyclopediaIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, EncyclopediaLandingPage.class);
                startActivity(intent);
            }
        });

        notificationIcon = findViewById(R.id.notification);
        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getSupportFragmentManager();
                fragment = new Notification();
                    fm.beginTransaction()
                            .setCustomAnimations(R.anim.enter_top_to_bottom, R.anim.exit_top_bottom,
                                    R.anim.enter_bottom_to_top, R.anim.exit_bottom_top)
                            .add(R.id.fragment_decider, fragment)
                            .commit();
            }
        });

        ImageView profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPageActivity.this, ProfileDropdown.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingPageActivity.this, CharacterCreationLandingPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        //characters = (ArrayList) cache.getCharacters().values();

        RecyclerView recyclerView = findViewById(R.id.characterRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(LandingPageActivity.this));

        characterAdapter adapter = new characterAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void closeNotifications() {
        fm.beginTransaction()
                .setCustomAnimations(R.anim.enter_bottom_to_top, R.anim.exit_bottom_top)
                .remove(fragment)
                .commit();
    }

    private class characterAdapter extends RecyclerView.Adapter<characterViewHolder> {

        @NotNull
        @Override
        public characterViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            return new characterViewHolder(getLayoutInflater().inflate(R.layout.character_option, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull characterViewHolder holder, int position) {
            holder.bind(characters.get(position));
        }

        @Override
        public int getItemCount() {
            return characters.size();
        }
    }

    private class characterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView characterName;
        private final TextView characterDetails;
        Character character;

        public characterViewHolder(@NonNull View characterView) {
            super(characterView);
            itemView.setOnClickListener(this);

            characterName = itemView.findViewById(R.id.characterName);
            characterDetails = itemView.findViewById(R.id.characterDetails);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LandingPageActivity.this, baseActivity.class);
            intent.putExtra("characterID", character.getCharacterID());
            startActivity(intent);
        }

        public void bind(Character character) {
            this.character = character;
            characterName.setText(character.getName());
            characterDetails.setText(character.printClassLevels());
        }
    }

}