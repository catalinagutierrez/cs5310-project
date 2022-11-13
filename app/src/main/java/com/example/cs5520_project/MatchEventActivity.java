package com.example.cs5520_project;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MatchEventActivity extends AppCompatActivity {

    RecyclerView eventRecyler;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_match_event);

        eventRecyler = findViewById(R.id.event_recycler);
        eventRecyler();
    }

    private void eventRecyler() {
        eventRecyler.setHasFixedSize(true);
        eventRecyler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<EventHelperClass> eventList = new ArrayList<>();

        eventList.add(new EventHelperClass(R.drawable.green_logo,"Testing"));
        eventList.add(new EventHelperClass(R.drawable.red_logo,"Testing2"));

        adapter = new EventAdapterFind(eventList);
        eventRecyler.setAdapter(adapter);
    }
}