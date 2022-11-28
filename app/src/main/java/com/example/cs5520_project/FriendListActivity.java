package com.example.cs5520_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

    ArrayList<String> friendsList;
    ArrayList<String> friendsUsernameList;
    Button addNewFriendButton;
    String uid;
    RecyclerView friendRecyler;
    FriendsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addNewFriendButton = findViewById(R.id.addNewFriendButton);
        friendRecyler = findViewById(R.id.friendsListRecycler);

        friendsList = new ArrayList<String>();
        friendsUsernameList = new ArrayList<String>();
        friendsList = getIntent().getStringArrayListExtra("friendsList");
        uid = getIntent().getStringExtra("uid");
        friendRecyler();

        addNewFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendIntent = new Intent(FriendListActivity.this, AddFriendActivity.class);
                friendIntent.putExtra("uid",uid);
                friendIntent.putExtra("friendsList", friendsList);
                startActivity(friendIntent);
                finish();
            }
        });

        for(String friend : friendsList) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Eventure Users").child(friend);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    friendsUsernameList.add(snapshot.child("username").getValue().toString());
                    adapter.updateFriends(friendsUsernameList, friendsList);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    private void friendRecyler() {
        friendRecyler.setHasFixedSize(true);
        friendRecyler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new FriendsListAdapter(friendsUsernameList, friendsList, uid, this);
        friendRecyler.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FriendListActivity.this, HomePageActivity.class);
        intent.putExtra("uid", uid);
        startActivity(intent);
        finish();
    }
}