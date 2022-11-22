package com.example.cs5520_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendViewHolder>{
    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_friend,parent,false);
        FriendsListAdapter.FriendViewHolder friendViewHolder = new FriendsListAdapter.FriendViewHolder(view);
        return friendViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsListAdapter.FriendViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView description;
        Button addEvent;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.eventImage);
            description = itemView.findViewById(R.id.eventDescription);
            addEvent = itemView.findViewById(R.id.eventRegisterBtn);
        }
    }
}
