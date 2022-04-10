package edu.byu.cs.tweeter.client.view.encyclopedia;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import edu.byu.cs.client.R;

public class EncyclopediaExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private Map<String, List<String>> names;
    private List<String> groups;

    public EncyclopediaExpandableListAdapter(Context context, List<String> listGroup, Map<String, List<String>> listItem) {
        this.context = context;
        this.groups = listGroup;
        this.names = listItem;
    }

    @Override
    public int getGroupCount() {
        return names.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return names.get(groups.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return groups.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return names.get(groups.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String group = groups.get(i);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.encyclopedia_item, null);

        }
        TextView item = view.findViewById(R.id.group);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(group);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String name = names.get(groups.get(i)).get(i1);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.child_encyclopedia_item, null);

        }
        TextView item = view.findViewById(R.id.child);
        ImageView background = view.findViewById(R.id.childBackground);
        if (name.equals("Human")) {
            background.setBackgroundResource(R.drawable.human);
        } else if (name.equals("Elf")) {
            background.setBackgroundResource(R.drawable.elf);
        } else if (name.equals("Half-Elf")) {
            background.setBackgroundResource(R.drawable.halfelf);
        } else if (name.equals("Dwarf")) {
            background.setBackgroundResource(R.drawable.dwarf);
        } else if (name.equals("Halfling")) {
            background.setBackgroundResource(R.drawable.halfling);
        } else if (name.equals("Dragonborn")) {
            background.setBackgroundResource(R.drawable.dragonborn);
        } else if (name.equals("Gnome")) {
            background.setBackgroundResource(R.drawable.gnome);
        } else if (name.equals("Tiefling")) {
            background.setBackgroundResource(R.drawable.tiefling);
        } else if (name.equals("Half-Orc")) {
            background.setBackgroundResource(R.drawable.halforc);
        } else if (name.equals("Barbarian")) {
            background.setBackgroundResource(R.drawable.barabarian);
        } else if (name.equals("Druid")) {
            background.setBackgroundResource(R.drawable.druid);
        } else if (name.equals("Monk")) {
            background.setBackgroundResource(R.drawable.monk);
        } else if (name.equals("Rogue")) {
            background.setBackgroundResource(R.drawable.rogue);
        } else if (name.equals("Wizard")) {
            background.setBackgroundResource(R.drawable.wizard);
        } else if (name.equals("Cleric")) {
            background.setBackgroundResource(R.drawable.cleric);
        } else if (name.equals("Fighter")) {
            background.setBackgroundResource(R.drawable.fighter);
        } else if (name.equals("Paladin")) {
            background.setBackgroundResource(R.drawable.paladin);
        } else if (name.equals("Warlock")) {
            background.setBackgroundResource(R.drawable.warlock);
        } else if (name.equals("Animals")) {
            background.setBackgroundResource(R.drawable.animal);
        } else if (name.equals("Dinosaurs")) {
            background.setBackgroundResource(R.drawable.dinosaur);
        } else if (name.equals("Dragons")) {
            background.setBackgroundResource(R.drawable.dragon);
        } else if (name.equals("Elementals")) {
            background.setBackgroundResource(R.drawable.elemental);
        } else if (name.equals("Giants")) {
            background.setBackgroundResource(R.drawable.giant);
        }  else if (name.equals("Goblins")) {
            background.setBackgroundResource(R.drawable.goblin);
        }  else if (name.equals("Humanoids")) {
            background.setBackgroundResource(R.drawable.humanoid);
        }  else if (name.equals("Plants")) {
            background.setBackgroundResource(R.drawable.plant);
        } else if (name.equals("Undead")) {
            background.setBackgroundResource(R.drawable.undead);
        } else if (name.equals("Melee")) {
            background.setBackgroundResource(R.drawable.melee);
        } else if (name.equals("Ranged")) {
            background.setBackgroundResource(R.drawable.ranged);
        } else if (name.equals("View All")) {
            background.setBackgroundResource(R.drawable.equipment);
        } else{
            background.setBackgroundResource(R.drawable.encyclopedialand);
        }
        item.setText(name);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int i) {

    }

    @Override
    public void onGroupCollapsed(int i) {

    }

    @Override
    public long getCombinedChildId(long l, long l1) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long l) {
        return 0;
    }
}
