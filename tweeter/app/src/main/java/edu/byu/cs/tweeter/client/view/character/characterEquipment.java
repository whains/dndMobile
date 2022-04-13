package edu.byu.cs.tweeter.client.view.character;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.Character;
import edu.byu.cs.tweeter.model.domain.Item;
import edu.byu.cs.tweeter.model.domain.Weapon;

public class characterEquipment extends Fragment {
    Cache cache = Cache.getInstance();
    Character thisCharacter;
    View view;

    ArrayList<Item> items;
    ArrayList<Weapon> weapons;

    itemAdapter inventoryAdapter;
    weaponAdapter weaponsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.equipment_page, container, false);
        thisCharacter = cache.getCharacter(container.getTag().toString());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        items = thisCharacter.getItems();
        weapons = thisCharacter.getWeapons();

        Button simplify = view.findViewById(R.id.simplify);
        simplify.setOnClickListener(view -> {
            thisCharacter.exchangePieces();
            initializeCoins();
        });

        initializeCoins();

        RecyclerView inventory = view.findViewById(R.id.inventoryRecycler);
        inventory.setLayoutManager(new LinearLayoutManager(getContext()));

        inventoryAdapter = new itemAdapter();
        inventory.setAdapter(inventoryAdapter);


        RecyclerView weapons = view.findViewById(R.id.weaponsRecycler);
        weapons.setLayoutManager(new LinearLayoutManager(getContext()));

        weaponsAdapter = new weaponAdapter();
        weapons.setAdapter(weaponsAdapter);

        Button addInventoryItem = view.findViewById(R.id.add_inventory_button);
        addInventoryItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInventoryEditor();
            }
        });

        Button addWeapon = view.findViewById(R.id.add_weapons_button);
        addWeapon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWeaponEditor();
            }
        });
    }

    private void openInventoryEditor() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.new_inventory_item);

        EditText item = dialog.findViewById(R.id.itemName);
        EditText description = dialog.findViewById(R.id.itemDescription);

        Button submit = dialog.findViewById(R.id.rollAgain);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item newItem = new Item(item.getText().toString());
                newItem.addDescription(description.getText().toString());
                items.add(newItem);
                inventoryAdapter.notifyDataSetChanged();
                inventoryAdapter.notifyItemInserted(items.size() - 1);
                dialog.dismiss();
            }
        });

        Button close = dialog.findViewById(R.id.closeRoller);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void openWeaponEditor() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.new_weapon);

        EditText name = dialog.findViewById(R.id.weaponName);
        EditText type = dialog.findViewById(R.id.type);
        EditText damage = dialog.findViewById(R.id.damage);

        Button submit = dialog.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Weapon newWeapon = new Weapon(name.getText().toString(), damage.getText().toString(), type.getText().toString(),
                        true, true);
                weapons.add(newWeapon);
                weaponsAdapter.notifyDataSetChanged();
                weaponsAdapter.notifyItemInserted(weapons.size() - 1);
                dialog.dismiss();
            }
        });

        Button close = dialog.findViewById(R.id.closeTheThing);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static characterEquipment newInstance() {
        return new characterEquipment();
    }

    private class itemAdapter extends RecyclerView.Adapter<itemViewHolder> {

        @NotNull
        @Override
        public itemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            return new itemViewHolder(getLayoutInflater().inflate(R.layout.item_line, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull itemViewHolder holder, int position) {
            holder.bind(items.get(position));
        }

        @Override
        public int getItemCount() { return items.size(); }
    }

    private static class itemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView itemName;
        private final TextView itemDetails;
        Item item;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            itemName = itemView.findViewById(R.id.item);
            itemDetails = itemView.findViewById(R.id.description);
        }

        @Override
        public void onClick(View view) {

        }

        public void bind(Item item) {
            this.item = item;
            itemName.setText(item.getName());
            itemDetails.setText(item.getDescription());
        }
    }

    private class weaponAdapter extends RecyclerView.Adapter<weaponViewHolder> {

        @NotNull
        @Override
        public weaponViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            return new weaponViewHolder(getLayoutInflater().inflate(R.layout.weapon_line, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull weaponViewHolder holder, int position) {
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

        }

        public void bind(Weapon weapon) {
            this.weapon = weapon;
            weaponName.setText(weapon.getName());
            attackBonus.setText(weapon.printAttack());
            damage.setText(weapon.printDamage());
        }
    }

    private void initializeCoins() {
        TextView platinum = view.findViewById(R.id.platinum);
        platinum.setText(String.valueOf(thisCharacter.getPlatinumPieces()));

        TextView electrum = view.findViewById(R.id.electrum);
        electrum.setText(String.valueOf(thisCharacter.getElectrumPieces()));

        TextView gold = view.findViewById(R.id.gold);
        gold.setText(String.valueOf(thisCharacter.getGoldPieces()));

        TextView silver = view.findViewById(R.id.silver);
        silver.setText(String.valueOf(thisCharacter.getSilverPieces()));

        TextView copper = view.findViewById(R.id.copper);
        copper.setText(String.valueOf(thisCharacter.getCopperPieces()));
    }
}
