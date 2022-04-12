package edu.byu.cs.tweeter.client.view.character;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.Character;
import edu.byu.cs.tweeter.model.domain.Weapon;

public class characterCombat extends Fragment {
    Cache cache = Cache.getInstance();
    Character thisCharacter;
    View view;

    ArrayList<Weapon> weapons;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.combat_page, container, false);
        thisCharacter = cache.getCharacter(container.getTag().toString());

        view.findViewById(R.id.initiativeWindow).setOnClickListener(view -> openRoller(thisCharacter.getInitiative()));
        view.findViewById(R.id.deathSaves).setOnClickListener(view -> openRoller(0));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        TextView AC = view.findViewById(R.id.AC);
        AC.setText(String.valueOf(thisCharacter.getAC()));

        TextView initiative = view.findViewById(R.id.Initiative);
        initiative.setText(String.valueOf(thisCharacter.printInitiative()));


        TextView currentHP = view.findViewById(R.id.CurrentHP);
        currentHP.setText(String.valueOf(thisCharacter.getCurrentHP()));

        TextView maxHP = view.findViewById(R.id.MaxHP);
        maxHP.setText(String.valueOf(thisCharacter.getMaxHP()));

        TextView tempHP = view.findViewById(R.id.TemporaryHP);
        tempHP.setText(String.valueOf(thisCharacter.getTemporaryHP()));


        TextView walking = view.findViewById(R.id.walkingSpeed);
        walking.setText(String.valueOf(thisCharacter.getWalkingSpeed()));

        TextView swimming = view.findViewById(R.id.swimmingSpeed);
        swimming.setText(String.valueOf(thisCharacter.getSwimmingSpeed()));

        TextView climbing = view.findViewById(R.id.climbingSpeed);
        climbing.setText(String.valueOf(thisCharacter.getClimbingSpeed()));

        TextView flying = view.findViewById(R.id.flyingSpeed);
        flying.setText(String.valueOf(thisCharacter.getFlyingSpeed()));

        RadioButton success1 = view.findViewById(R.id.success1);
        success1.setChecked(thisCharacter.getSuccess1());
        success1.setOnClickListener(view -> {
            thisCharacter.setSuccess1();
            success1.setChecked(thisCharacter.getSuccess1());
        });

        RadioButton success2 = view.findViewById(R.id.success2);
        success2.setChecked(thisCharacter.getSuccess2());
        success2.setOnClickListener(view -> {
            thisCharacter.setSuccess2();
            success2.setChecked(thisCharacter.getSuccess2());
        });

        RadioButton success3 = view.findViewById(R.id.success3);
        success3.setChecked(thisCharacter.getSuccess3());
        success3.setOnClickListener(view -> {
            thisCharacter.setSuccess3();
            success3.setChecked(thisCharacter.getSuccess3());
        });

        RadioButton failure1 = view.findViewById(R.id.failure1);
        failure1.setChecked(thisCharacter.getFailure1());
        failure1.setOnClickListener(view -> {
            thisCharacter.setFailure1();
            failure1.setChecked(thisCharacter.getFailure1());
        });

        RadioButton failure2 = view.findViewById(R.id.failure2);
        failure2.setChecked(thisCharacter.getFailure2());
        failure2.setOnClickListener(view -> {
            thisCharacter.setFailure2();
            failure2.setChecked(thisCharacter.getFailure2());
        });

        RadioButton failure3 = view.findViewById(R.id.failure3);
        failure3.setChecked(thisCharacter.getFailure3());
        failure3.setOnClickListener(view -> {
            thisCharacter.setFailure3();
            failure3.setChecked(thisCharacter.getFailure3());
        });

        weapons = thisCharacter.getWeapons();

        RecyclerView weapons = view.findViewById(R.id.weaponsRecycler);
        weapons.setLayoutManager(new LinearLayoutManager(getContext()));

        weaponAdapter weaponsAdapter = new weaponAdapter();
        weapons.setAdapter(weaponsAdapter);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public  static characterCombat newInstance() {
        characterCombat fragment = new characterCombat();
        return fragment;
    }

    private class weaponAdapter extends RecyclerView.Adapter<weaponViewHolder> {

        @NotNull
        @Override
        public weaponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new weaponViewHolder(getLayoutInflater().inflate(R.layout.weapon_line, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull weaponViewHolder holder, int position) {
            holder.bind(weapons.get(position));
        }

        @Override
        public int getItemCount() { return weapons.size(); }
    }

    private class weaponViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView weaponName;
        private final TextView attackBonus;
        private final TextView damage;
        Weapon weapon;

        public weaponViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            weaponName = itemView.findViewById(R.id.weapon);
            attackBonus = itemView.findViewById(R.id.attackBonus);
            damage = itemView.findViewById(R.id.Damage);
        }

        @Override
        public void onClick(View view) {
            openAttackRoller(weapon);
        }

        public void bind(Weapon weapon) {
            this.weapon = weapon;
            weaponName.setText(weapon.getName());
            attackBonus.setText(weapon.printAttack());
            damage.setText(weapon.printDamage());
        }
    }

    private void openRoller(int mod) {
        int[] rolls = {1};
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dice_results);

        TextView firstRoll = dialog.findViewById(R.id.firstRoll);
        firstRoll.setText(d20(mod));

        TextView secondRoll = dialog.findViewById(R.id.secondRoll);
        secondRoll.setText("");

        TextView thirdRoll = dialog.findViewById(R.id.thirdRoll);
        thirdRoll.setText("");

        dialog.findViewById(R.id.rollAgain).setOnClickListener(view -> {
            if (rolls[0] == 1) {
                rolls[0]++;
                secondRoll.setText(d20(mod));
            }

            else if (rolls[0] == 2) {
                rolls[0]++;
                thirdRoll.setText(d20(mod));
            }
        });

        dialog.findViewById(R.id.closeRoller).setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void openAttackRoller(Weapon weapon) {
        int[] rolls = {1};
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.attack_result);

        TextView weaponText = dialog.findViewById(R.id.weapon);
        weaponText.setText("Attacking with " + weapon.getName());

        TextView firstRoll = dialog.findViewById(R.id.firstRoll);
        firstRoll.setText(d20(weapon.getAttackBonus()));

        TextView secondRoll = dialog.findViewById(R.id.secondRoll);
        secondRoll.setText("");

        TextView thirdRoll = dialog.findViewById(R.id.thirdRoll);
        thirdRoll.setText("");

        TextView damageRoll = dialog.findViewById(R.id.damageRoll);
        damageRoll.setText(weapon.getDamage());

        dialog.findViewById(R.id.rollAgain).setOnClickListener(view -> {
            if (rolls[0] == 1) {
                rolls[0]++;
                secondRoll.setText(d20(weapon.getAttackBonus()));
            }

            else if (rolls[0] == 2) {
                rolls[0]++;
                thirdRoll.setText(d20(weapon.getAttackBonus()));
            }
        });

        dialog.findViewById(R.id.closeRoller).setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    private String d20(int mod) {
        Random rand = new Random();
        int roll = rand.nextInt(20) + 1;

        return rollString(roll, mod);
    }

    private String rollString(int roll, int mod){
        StringBuilder string = new StringBuilder();
        string.append("You rolled ");
        string.append(roll);

        if (mod > 0) {
            string.append(" + ");
            string.append(mod);
        }

        else if (mod < 0) {
            string.append(" - ");
            string.append(-mod);
        }

        if (mod != 0) {
            string.append(" = ");
            string.append(roll + mod);
        }

        return string.toString();
    }
}
