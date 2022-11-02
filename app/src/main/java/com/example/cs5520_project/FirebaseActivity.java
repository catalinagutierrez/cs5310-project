package com.example.cs5520_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        //TODO check if user already exists and load it as current user

        // Generate a new empty item, and get the automatically generated key
        String userId = reference.push().getKey();

        // Insert the data using the key
        currentUser = new UserInfo(userId, name, username);
        reference.child(userId).setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful() && currentUser != null){
                    loadUserProfile(currentUser);
                }else{
                    Toast.makeText(FirebaseActivity.this, "Login failed, please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loadUserProfile(UserInfo user){
        Toast.makeText(FirebaseActivity.this, user.username, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(FirebaseActivity.this, UserProfileActivity.class);
        intent.putExtra("username", user.username);
        startActivity(intent);
    }
}