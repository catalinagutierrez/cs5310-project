package com.example.cs5520_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

    ArrayList<String> friendsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        friendsList = new ArrayList<String>();
        friendsList = getIntent().getStringArrayListExtra("friendsList");


    }

    private void friendRecyler(ArrayList friendsList) {
//        eventRecyler.setHasFixedSize(true);
//        eventRecyler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        adapter = new FriendsListAdapter();
//        eventRecyler.setAdapter(adapter);
    }
}