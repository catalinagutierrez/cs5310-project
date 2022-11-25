package com.example.cs5520_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendViewHolder>{
    ArrayList<String> friendsList;
    private Context context;

    public FriendsListAdapter(ArrayList<String> friendsList, Context context) {
        this.friendsList = friendsList;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_friend,parent,false);
        FriendsListAdapter.FriendViewHolder friendViewHolder = new FriendsListAdapter.FriendViewHolder(view);
        return friendViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsListAdapter.FriendViewHolder holder, int position) {
        System.out.println(friendsList.get(position));
        holder.friendName.setText(friendsList.get(position));
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    public void updateFriends(ArrayList<String> friends) {
        friendsList = friends;
        notifyDataSetChanged();
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder{
        TextView friendName;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.friendNameText);
        }
    }
}
