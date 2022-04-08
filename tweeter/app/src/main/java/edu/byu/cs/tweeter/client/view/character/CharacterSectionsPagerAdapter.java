package edu.byu.cs.tweeter.client.view.character;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CharacterSectionsPagerAdapter extends FragmentPagerAdapter {
    private final Context context;

    public CharacterSectionsPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return characterSpells.newInstance();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
