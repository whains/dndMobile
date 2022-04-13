package edu.byu.cs.tweeter.client.view.character;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import edu.byu.cs.client.R;

public class CharacterDetailsExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private Map<String, List<String>> names;
    private List<String> groups;

    public CharacterDetailsExpandableListAdapter(Context context, List<String> listGroup, Map<String, List<String>> listItem) {
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
            view = inflater.inflate(R.layout.character_details_item, null);

        }
        TextView item = view.findViewById(R.id.group);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(group);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String name = names.get(groups.get(i)).get(i1);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (name.equals("Physical Details")) {
            view = inflater.inflate(R.layout.physical_details, null);
        } else if (name.equals("Character Traits")) {
            view = inflater.inflate(R.layout.character_traits, null);
        } else if (name.equals("Backstory")) {
            view = inflater.inflate(R.layout.backstory, null);
        } else {
            view = inflater.inflate(R.layout.notes, null);
        }
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
