package edu.byu.cs.tweeter.client.view.character;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.encyclopedia.EncyclopediaLandingPage;
import edu.byu.cs.tweeter.client.view.main.LandingPageActivity;
import edu.byu.cs.tweeter.client.view.main.ProfileDropdown;

public class Notes extends AppCompatActivity {
    Cache cache = Cache.getInstance();
    ImageView encyclopediaIcon;
    List<String> notes = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    String characterName;
    TextView characterNameNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        characterName = getIntent().getExtras().getString("characterName");

        setContentView(R.layout.notes_activitiy);

        encyclopediaIcon = findViewById(R.id.encyclopediaIcon);
        encyclopediaIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notes.this, EncyclopediaLandingPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        ImageView home = findViewById(R.id.logo);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Notes.this, LandingPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        ImageView profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Notes.this, ProfileDropdown.class);
                startActivity(intent);
            }
        });

        characterNameNotes = findViewById(R.id.characterNotesName);
        characterNameNotes.setText(characterName + "'s Notes:");

        cache.addNote("DANGER!", "Last session we found a HUGE dragon living in the swap by the castle make sure to not go too close!");
        cache.addNote("Amira", "Princess Amira is looking preety fine these days. I think I might have a curch on her I better be carful");
        cache.addNote("Kelsier's dungeons", "Quick Note on Kelsier's Dungeon: \nThat place is CREEPY we ran into zombies, skeletons, and I think" +
                "there may be an undead Tyranosaurus-rex somewhere in that dungeon. We explored everyhting on the right side in the last session but did not" +
                "find the end of the dungeon yet......I guess we will have to see what we are able to find out in the next session. \n\n REMEMBER: When you" +
                "can't look anymore below, look above (riddle given at the entrance of the dungeon).");
    }

    @Override
    public void onResume() {
        super.onResume();

        Set<String> keySet = cache.getNotes().keySet();
        Map<String, String> cacheNotes = cache.getNotes();
        titles.clear();
        notes.clear();
        for (String title: keySet) {
            notes.add(cacheNotes.get(title));
            titles.add(title);
        }

        RecyclerView recyclerView = findViewById(R.id.notesRecylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(Notes.this));

        Notes.notesAdapter adapter = new Notes.notesAdapter();
        recyclerView.setAdapter(adapter);
    }

    private class notesAdapter extends RecyclerView.Adapter<Notes.notesViewHolder> {

        @NotNull
        @Override
        public Notes.notesViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            return new Notes.notesViewHolder(getLayoutInflater().inflate(R.layout.note_option, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Notes.notesViewHolder holder, int position) {
            holder.bind(titles.get(position), notes.get(position));
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }
    }

    private class notesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView titleView;
        private final TextView noteView;
        private CircleImageView characterPicture;
        String note;
        String title;

        public notesViewHolder(@NonNull View characterView) {
            super(characterView);

            titleView = itemView.findViewById(R.id.noteTitle);
            noteView = itemView.findViewById(R.id.noteContent);
        }

        @Override
        public void onClick(View view) {
        }

        public void bind(String title, String note) {
            this.note = note;
            this.title = title;
            this.titleView.setText(title);
            this.noteView.setText(note);
        }
    }
}

