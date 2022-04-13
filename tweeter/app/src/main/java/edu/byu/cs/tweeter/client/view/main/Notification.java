package edu.byu.cs.tweeter.client.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.view.character.baseActivity;

public class Notification extends Fragment {

    Cache cache = Cache.getInstance();
    Notification.characterAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.notification, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RelativeLayout exit = getView().findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LandingPageActivity landingPageActivity = (LandingPageActivity) getActivity();
                landingPageActivity.closeNotifications();
            }
        });

        if (cache.getNotifications().size() == 0) {
            TextView nothingToShow = getActivity().findViewById(R.id.nothingToShow);
            nothingToShow.setText("No New Notifications! \nYou're Good to Go!");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //characters = (ArrayList) cache.getCharacters().values();

        RecyclerView recyclerView = getView().findViewById(R.id.notificationRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new Notification.characterAdapter();
        recyclerView.setAdapter(adapter);
    }

    private class characterAdapter extends RecyclerView.Adapter<Notification.characterViewHolder> {

        @NotNull
        @Override
        public Notification.characterViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            return new Notification.characterViewHolder(getLayoutInflater().inflate(R.layout.notification_option, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull Notification.characterViewHolder holder, int position) {
            holder.bind(cache.getNotifications().get(position), cache.getSubNotifications().get(position), position);
        }

        @Override
        public int getItemCount() {
            return cache.getNotifications().size();
        }
    }

    private class characterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView message;
        private final TextView subtext;
        private final ImageView delete;
        String notification;

        public characterViewHolder(@NonNull View characterView) {
            super(characterView);

            message = itemView.findViewById(R.id.message);
            subtext = itemView.findViewById(R.id.subtext);
            delete = itemView.findViewById(R.id.deleteNotification);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            removeAt(getAdapterPosition());
        }

        public void bind(String message, String subNotifications, int position) {
            this.notification = message + subNotifications;
            this.message.setText(message);
            subtext.setText(subNotifications);
        }
    }

    public void removeAt(int position) {
        cache.deleteNotification(position);
        cache.deleteSubNotification(position);
        adapter.notifyItemRemoved(position);
    }


}
