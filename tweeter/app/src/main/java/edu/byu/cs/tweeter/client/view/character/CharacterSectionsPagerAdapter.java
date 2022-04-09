package edu.byu.cs.tweeter.client.view.character;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.view.login.LoginFragment;
import edu.byu.cs.tweeter.client.view.login.RegisterFragment;

public class CharacterSectionsPagerAdapter extends FragmentPagerAdapter {
    private static final String LOG_TAG = "LoginSectionsPagerAdapter";

    private static final int MAIN_FRAGMENT_POSITION = 0;
    private static final int COMBAT_FRAGMENT_POSITION = 1;
    private static final int EQUIP_FRAGMENT_POSITION = 2;
    private static final int SPELLS_FRAGMENT_POSITION = 3;
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.mainTabTitle, R.string.combatTabTitle,
            R.string.equipTabTitle, R.string.spellsTabTitle};
    private final Context context;

    public CharacterSectionsPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == MAIN_FRAGMENT_POSITION) {
            //TODO ANDREW NEEDS TO CHANGE HIS PAGE TO A FRAGMENT
            return characterCombat.newInstance();
        } else if (position == COMBAT_FRAGMENT_POSITION) {
            return characterCombat.newInstance();
        } else if (position == EQUIP_FRAGMENT_POSITION) {
            return characterEquipment.newInstance();
        } else if (position == SPELLS_FRAGMENT_POSITION) {
            return characterSpells.newInstance();
        } else {
            Log.e(LOG_TAG, "Unknown fragment requested.");
            return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
