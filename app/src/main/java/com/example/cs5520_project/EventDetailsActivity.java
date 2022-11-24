package com.example.cs5520_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        String uid = getIntent().getStringExtra("description");

        System.out.println(uid);
    }
}