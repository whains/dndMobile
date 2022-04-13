package edu.byu.cs.tweeter.client.view.character;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.charactercreation.CreationMainActivity;
import edu.byu.cs.tweeter.client.view.main.LandingPageActivity;
import edu.byu.cs.tweeter.model.domain.Character;

public class characterDetails  extends Fragment {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> listGroup;
    List<String> childList;
    Map<String,List<String>> listItem;

    Cache cache = Cache.getInstance();
    Character thisCharacter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        thisCharacter = cache.getCharacter(container.getTag().toString());
        return inflater.inflate(R.layout.character_details, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initGroupData();
        createCollection();
        expandableListView = getView().findViewById(R.id.detailsExpandableListView);
        expandableListAdapter = new CharacterDetailsExpandableListAdapter(getContext(), listGroup, listItem);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1 && i != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        TextView name = getView().findViewById(R.id.detailsName);
        name.setText(thisCharacter.getName());

        TextView levelAndClass = getView().findViewById(R.id.detailsLevelAndClass);
        levelAndClass.setText(thisCharacter.printClassLevels());

        TextView backgroundAndMore = getView().findViewById(R.id.detailsRaceBackgroundAlignment);
        backgroundAndMore.setText(thisCharacter.getRace() + " " + thisCharacter.getBackground() + " " + thisCharacter.getAlignment());

        CircleImageView characterPicture = getView().findViewById(R.id.detailsPicture);
        String race = thisCharacter.getRace();
        if (race.equals("Human")) {
            characterPicture.setImageResource(R.drawable.humanc);
        } else if (race.equals("Elf")) {
            characterPicture.setImageResource(R.drawable.elfc);
        } else if (race.equals("Half-Elf")) {
            characterPicture.setImageResource(R.drawable.halfelfc);
        } else if (race.equals("Dwarf")) {
            characterPicture.setImageResource(R.drawable.dwarfc);
        } else if (race.equals("Halfling")) {
            characterPicture.setImageResource(R.drawable.halfling);
        } else if (race.equals("Dragonborn")) {
            characterPicture.setImageResource(R.drawable.dragonbornc);
        } else if (race.equals("Gnome")) {
            characterPicture.setImageResource(R.drawable.gnomec);
        } else if (race.equals("Half-Orc")) {
            characterPicture.setImageResource(R.drawable.halforcc);
        } else if (race.equals("Tiefling")) {
            characterPicture.setImageResource(R.drawable.tieflingc);
        } else if (race.equals("Aarakocra")) {
            characterPicture.setImageResource(R.drawable.aarakocra);
        } else {
            characterPicture.setImageResource(R.drawable.paladincc);
        }

        expandableListView = getView().findViewById(R.id.detailsExpandableListView);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i, i1).toString();
                if (selected.equals("Notes")) {
                    Intent intent = new Intent(getContext(), Notes.class);
                    intent.putExtra("characterName", thisCharacter.getName());
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    public static characterDetails newInstance() {
        return new characterDetails();
    }

    private void createCollection() {
        String[] races = {"Physical Details"};
        String[] classes = {"Character Traits"};
        String[] monsters = {"Backstory"};
        String[] weapons = {"Notes"};
        listItem = new HashMap<>();
        for (String group: listGroup) {
            if (group.equals("Physical Details")){
                loadChild(races);
            } else if (group.equals("Character Traits")){
                loadChild(classes);
            } else if (group.equals("Backstory")){
                loadChild(monsters);
            } else if (group.equals("Notes")){
                loadChild(weapons);
            }
            listItem.put(group, childList);
        }

    }

    private void loadChild(String[] names) {
        childList = new ArrayList<>();
        for (String name: names) {
            childList.add(name);
        }
    }

    private void initGroupData() {
        listGroup = new ArrayList<>();
        listGroup.add("Physical Details");
        listGroup.add("Character Traits");
        listGroup.add("Backstory");
        listGroup.add("Notes");
    }
}