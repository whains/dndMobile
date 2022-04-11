package edu.byu.cs.tweeter.client.view.character;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.login.LoginSectionsPagerAdapter;
import edu.byu.cs.tweeter.model.domain.Character;

public class baseActivity extends AppCompatActivity {
    Cache cache = Cache.getInstance();
    Character thisCharacter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_base);

        thisCharacter = cache.getCharacter(getIntent().getExtras().getString("characterID"));
        initializeName();

        CharacterSectionsPagerAdapter c = new CharacterSectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.character_viewer);
        viewPager.setAdapter(c);
        TabLayout tabs = findViewById(R.id.characterTabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void initializeName() {
        TextView characterName = findViewById(R.id.character_name);
        characterName.setText(thisCharacter.getName());
        characterName.setOnClickListener(view -> editName());
    }

    private void editName() {
        Dialog dialog = new Dialog(baseActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.name_update);

        EditText editName = dialog.findViewById(R.id.Name);
        editName.setText(thisCharacter.getName());

        dialog.findViewById(R.id.saveName).setOnClickListener(view -> {
            thisCharacter.setCharacterName(editName.getText().toString());
            initializeName();
            dialog.dismiss();
        });

        dialog.findViewById(R.id.cancelName).setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    private class CharacterSectionsPagerAdapter extends FragmentPagerAdapter {
        private static final String LOG_TAG = "LoginSectionsPagerAdapter";

        private static final int MAIN_FRAGMENT_POSITION = 0;
        private static final int COMBAT_FRAGMENT_POSITION = 1;
        private static final int EQUIP_FRAGMENT_POSITION = 2;
        private static final int SPELLS_FRAGMENT_POSITION = 3;

        @StringRes
        private final int[] TAB_TITLES = new int[]{R.string.mainTabTitle, R.string.combatTabTitle,
                R.string.equipTabTitle, R.string.spellsTabTitle};
        private final Context context;

        public CharacterSectionsPagerAdapter(Context context, @NonNull FragmentManager fm) {
            super(fm);
            this.context = context;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            viewPager.setTag(thisCharacter.getCharacterID());

            if (position == MAIN_FRAGMENT_POSITION) {
                return characterMain.newInstance();
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
}
