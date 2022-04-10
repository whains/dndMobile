package edu.byu.cs.tweeter.client.view.encyclopedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.encyclopedia.classes.Barbarian;
import edu.byu.cs.tweeter.client.view.encyclopedia.monsters.Animals;
import edu.byu.cs.tweeter.client.view.encyclopedia.monsters.Dinosaurs;
import edu.byu.cs.tweeter.client.view.encyclopedia.monsters.Dragons;
import edu.byu.cs.tweeter.client.view.encyclopedia.monsters.Elementals;
import edu.byu.cs.tweeter.client.view.encyclopedia.monsters.Giant;
import edu.byu.cs.tweeter.client.view.encyclopedia.races.Elf;
import edu.byu.cs.tweeter.client.view.encyclopedia.races.HalfElf;
import edu.byu.cs.tweeter.client.view.encyclopedia.races.Human;
import edu.byu.cs.tweeter.client.view.main.LandingPageActivity;

public class EncyclopediaLandingPage extends AppCompatActivity {
    Cache cache = Cache.getInstance();

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> listGroup;
    List<String> childList;
    Map<String,List<String>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encyclopedia_landing_page);

        initGroupData();
        createCollection();
        expandableListView = findViewById(R.id.encyclopediaExpandableListView);
        expandableListAdapter = new EncyclopediaExpandableListAdapter(this, listGroup, listItem);
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
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i, i1).toString();
                if (selected.equals("Human")) {
                    Intent intent = new Intent(EncyclopediaLandingPage.this, Human.class);
                    startActivity(intent);
                } else if (selected.equals("Elf")) {
                    Intent intent = new Intent(EncyclopediaLandingPage.this, Elf.class);
                    startActivity(intent);
                } else if (selected.equals("Half-Elf")) {
                    Intent intent = new Intent(EncyclopediaLandingPage.this, HalfElf.class);
                    startActivity(intent);
                } else if (selected.equals("Barbarian")) {
                    Intent intent = new Intent(EncyclopediaLandingPage.this, Barbarian.class);
                    startActivity(intent);
                } else if (selected.equals("Animals")) {
                    Intent intent = new Intent(EncyclopediaLandingPage.this, Animals.class);
                    startActivity(intent);
                } else if (selected.equals("Dinosaurs")) {
                    Intent intent = new Intent(EncyclopediaLandingPage.this, Dinosaurs.class);
                    startActivity(intent);
                } else if (selected.equals("Dragons")) {
                    Intent intent = new Intent(EncyclopediaLandingPage.this, Dragons.class);
                    startActivity(intent);
                } else if (selected.equals("Elementals")) {
                    Intent intent = new Intent(EncyclopediaLandingPage.this, Elementals.class);
                    startActivity(intent);
                } else if (selected.equals("Giants")) {
                    Intent intent = new Intent(EncyclopediaLandingPage.this, Giant.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    private void createCollection() {
        String[] races = {"Human", "Elf", "Half-Elf", "Dwarf", "Halfling", "Dragonborn", "Gnome", "Tiefling", "Half-Orc"};
        String[] classes = {"Barbarian", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Rogue", "Warlock", "Wizard"};
        String[] monsters = {"Animals", "Dinosaurs", "Dragons", "Elementals", "Goblins", "Humanoids", "Giants", "Plants", "Undead"};
        String[] weapons = {"Melee", "Ranged"};
        String[] equipment = {"View All"};
        listItem = new HashMap<>();
        for (String group: listGroup) {
            if (group.equals("Races")){
                loadChild(races);
            } else if (group.equals("Classes")){
                loadChild(classes);
            } else if (group.equals("Monsters")){
                loadChild(monsters);
            } else if (group.equals("Weapons")){
                loadChild(weapons);
            } else {
                loadChild(equipment);
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
        listGroup.add("Races");
        listGroup.add("Classes");
        listGroup.add("Monsters");
        listGroup.add("Weapons");
        listGroup.add("Equipment");
    }


}
