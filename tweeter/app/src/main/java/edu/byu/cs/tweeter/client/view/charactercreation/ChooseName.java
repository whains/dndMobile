package edu.byu.cs.tweeter.client.view.charactercreation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.byu.cs.client.R;

public class ChooseName extends Fragment {
    EditText name;
    LinearLayout continueFloating;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.character_creation_choose_name, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = getView().findViewById(R.id.characterName);

        continueFloating = getView().findViewById(R.id.raceContniue);
        continueFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() > 0) {
                    CreationMainActivity creationMainActivity = (CreationMainActivity) getActivity();
                    creationMainActivity.createCharacter(name.getText().toString());
                } else {
                    Toast.makeText(getContext(), "You must enter a name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
