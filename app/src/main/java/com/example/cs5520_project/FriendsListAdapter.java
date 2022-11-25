package com.example.cs5520_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendViewHolder>{
    ArrayList<String> friendsList;
    ArrayList<String> friendsListKeys;
    String uid;
    private Context context;

    public FriendsListAdapter(ArrayList<String> friendsList, ArrayList<String> friendsListKeys, String uid, Context context) {
        this.friendsList = friendsList;
        this.friendsListKeys = friendsListKeys;
        this.uid = uid;
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

        String deletedFriend = friendsListKeys.get(position);
        int deletedPosition = position;

        holder.deleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendsList.remove(deletedPosition);
                removeFriend(deletedFriend);
            }
        });
    }

    public void removeFriend(String deletedFriend) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Eventure Users");
        ref.child(uid).child("friendsList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {
                    if(data.getValue().equals(deletedFriend)) {
                        data.getRef().removeValue();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        friendsListKeys.remove(deletedFriend);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    public void updateFriends(ArrayList<String> friends, ArrayList<String> friendsKeys) {
        friendsList = friends;
        friendsListKeys = friendsKeys;
        notifyDataSetChanged();
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder{
        TextView friendName;
        Button deleteFriend;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.friendNameText);
            deleteFriend = itemView.findViewById(R.id.deleteFriend);
        }
    }
}
