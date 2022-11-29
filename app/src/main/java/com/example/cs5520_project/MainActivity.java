package com.example.cs5520_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();

        Button apiServiceBtn = (Button) findViewById(R.id.apiServiceBtn);
        apiServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ApiServiceActivity.class);
                startActivity(intent);
            }
        });

        Button firebaseBtn = (Button) findViewById(R.id.firebaseBtn);
        firebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FirebaseActivity.class);
                startActivity(intent);
            }
        });

        Button finalProjectBtn = (Button) findViewById(R.id.projectBtn);
        finalProjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, SplashActivity.class);
                startActivity(intent);
            }
        });



    }
}