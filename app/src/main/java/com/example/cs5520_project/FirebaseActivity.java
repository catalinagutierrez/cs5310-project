package com.example.cs5520_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseActivity extends AppCompatActivity {
    EditText name, username;
    Button loginBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    List<UserInfo> userList;
    UserInfo currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        name = findViewById(R.id.fullNameText);
        username = findViewById(R.id.usernameText);
        loginBtn = findViewById(R.id.loginBtn);
        userList = new ArrayList<>();

        // Populate user list with existing users
        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot datas : snapshot.getChildren()) {
                    Log.i("I AM", "GETTING");
                    String uid = datas.getKey();
                    String name = datas.child("name").getValue().toString();
                    String username = datas.child("username").getValue().toString();
                    userList.add(new UserInfo(uid, name, username));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();
                String fullName = name.getText().toString();

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");

                login(fullName, userName);
            }
        });
    }

    public void login(String name, String username){

        // Check if user already exists in the list of users
        boolean userExists = false;
        for(UserInfo user : userList) {
            if(user.getUsername().equals(username)) {
                userExists = true;
                currentUser = user;
            }
        }

        // If they exist, load their profile
        if(userExists){
            loadUserProfile();
        }
        // Otherwise, genarate a new profile and add them to the database
        else{
            // Generate a new empty item, and get the automatically generated key
            String userId = reference.push().getKey();

            // Insert the data using the key
            currentUser = new UserInfo(userId, name, username);
            reference.child(userId).setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful() && currentUser != null){
                        loadUserProfile();
                    }else{
                        Toast.makeText(FirebaseActivity.this, "Login failed, please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void loadUserProfile(){
        Toast.makeText(FirebaseActivity.this, currentUser.username, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(FirebaseActivity.this, UserProfileActivity.class);
        intent.putExtra("username", currentUser.username);
        startActivity(intent);
    }
}