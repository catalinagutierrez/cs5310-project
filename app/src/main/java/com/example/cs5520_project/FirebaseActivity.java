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
import java.util.HashMap;
import java.util.List;

public class FirebaseActivity extends AppCompatActivity {
    EditText name, username;
    Button loginBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    List<UserInfo> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        name = findViewById(R.id.fullNameText);
        username = findViewById(R.id.usernameText);
        loginBtn = findViewById(R.id.loginBtn);
        userList = new ArrayList<>();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameStr = username.getText().toString();
                String nameStr = name.getText().toString();

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");

                signIn(nameStr, usernameStr);
            }
        });
    }

    public void signIn(String name, String username){
        //TODO - Swap - handle when user input is empty
        // Check if user already exists in the list of users

        // Check with db records if user exists
        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userExists = false;
                for (DataSnapshot data : snapshot.getChildren()) {
                    // if user exists, load their user profile
                    if (data.child("username").getValue().toString().equals(username)) {
                        HashMap<String, Integer> receivedStickers =  (HashMap<String, Integer>)data.child("receivedStickers").getValue();
                        HashMap<String, Integer> sentStickers =  (HashMap<String, Integer>)data.child("sentStickers").getValue();
                        UserInfo existingUser = new UserInfo(data.getKey(), data.child("name").getValue().toString(), data.child("username").getValue().toString(), sentStickers, receivedStickers);

                        loadUserProfile(existingUser);

                        userExists = true;

                        break;
                    }
                }

                // if user does not exist, create a new user
                if(!userExists){
                    signUp(name, username);
                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
            }
        });
    }

    public void signUp(String name, String username){
        // Generate a new empty item, and get the automatically generated key
        String uid = reference.push().getKey();

        // Insert the data using the key
        UserInfo newUser = new UserInfo(uid, name, username);
        reference.child(uid).setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    loadUserProfile(newUser);
                }else{
                    Toast.makeText(FirebaseActivity.this, "Login failed, please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loadUserProfile(UserInfo currentUser){
        Toast.makeText(FirebaseActivity.this, currentUser.username, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(FirebaseActivity.this, UserProfileActivity.class);
        intent.putExtra("CURRENT_USER", currentUser);
        startActivity(intent);
    }
}