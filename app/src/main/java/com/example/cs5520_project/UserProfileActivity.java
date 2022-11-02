package com.example.cs5520_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    TextView username;
    private RecyclerView recyclerView;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    RecyclerView.LayoutManager layoutManager;
    RecylerViewAdapter recylerViewAdapter;
    int arr[] = {R.drawable.emoji_1,R.drawable.emoji_10,R.drawable.emoji_3,
    R.drawable.emoji_4,R.drawable.emoji_5, R.drawable.emoji_6, R.drawable.emoji_7,
    R.drawable.emoji_8,R.drawable.emoji_9, R.drawable.emoji_2};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        username = findViewById(R.id.userId);
        List<String> names = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("username");

        // Load Users
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference();
        reference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot datas : snapshot.getChildren()) {
                    String friendUsername = datas.child("username").getValue().toString();
                    if(!userName.equals(friendUsername)) {
                        names.add(datas.child("name").getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Add users to friends dropdown
        Spinner mySpinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(UserProfileActivity.this, android.R.layout.simple_list_item_1,names);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recylerViewAdapter = new RecylerViewAdapter(arr,this, userName, "swap"); // TODO: Removed hardcoded name for spinner name
        recyclerView.setAdapter(recylerViewAdapter);
        recyclerView.setHasFixedSize(true);

        username.setText(userName);
    }
}