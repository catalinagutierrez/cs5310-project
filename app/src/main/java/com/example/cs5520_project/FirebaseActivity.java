package com.example.cs5520_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseActivity extends AppCompatActivity {

    EditText name, username;
    Button loginBtn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

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
                UserInfo user1 = new UserInfo(fullName, userName, 0);

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Users");

                reference.setValue(user1.name);
                Toast.makeText(FirebaseActivity.this, user1.username, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FirebaseActivity.this, UserProfileActivity.class);
                intent.putExtra("username", user1.username);
                startActivity(intent);

            }
        });

    }
}