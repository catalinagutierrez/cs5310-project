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
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();
                String fullName = name.getText().toString();

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");

                //TODO load the existing users in userList

                login(fullName, userName);
            }
        });
    }

    public void login(String name, String username){
        if(false){
            //TODO check if user already exists, load it as current user, and call loadUserProfile
        }else{
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