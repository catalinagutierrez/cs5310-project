package com.example.cs5520_project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs5520_project.messages.MessagesList;

import java.util.ArrayList;
import java.util.List;


public class HomePageActivity extends AppCompatActivity implements LocationListener {
    Button findNewEventBtn;
    RecyclerView eventRecyler, friendEventRecyler;
    RecyclerView.Adapter adapter;
    ImageView messageIcon;
    List<MessagesList> messagesLists ;
    String uid;

    ArrayList<EventHelperClass> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        uid = getIntent().getStringExtra("uid");

        findNewEventBtn = findViewById(R.id.findNewEventBtn);
        findNewEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(HomePageActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(HomePageActivity.this,LocationActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(HomePageActivity.this,FindEventActivity.class);
                    startActivity(intent);
                }
            }
        });

        MemoryData.savaData(uid, HomePageActivity.this);
        messageIcon = findViewById(R.id.messageIcon);
        messageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageActivity.this,MessageActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });

        eventRecyler = findViewById(R.id.yourEventsRecycler);
        eventRecyler();

        friendEventRecyler = findViewById(R.id.yourFriendsEventsRecycler);
        friendEventRecyler();




    }

    private void eventRecyler() {
        eventRecyler.setHasFixedSize(true);
        eventRecyler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<EventHelperClass> eventList = new ArrayList<>();

        //eventList.add(new EventHelperClass(R.drawable.green_logo,"Testing"));
        //eventList.add(new EventHelperClass(R.drawable.red_logo,"Testing2"));

        adapter = new EventAdapterYourEvents(eventList, this);
        eventRecyler.setAdapter(adapter);
    }

    private void friendEventRecyler() {
        friendEventRecyler.setHasFixedSize(true);
        friendEventRecyler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //eventList.add(new EventHelperClass(R.drawable.green_logo,"Testing"));
        //eventList.add(new EventHelperClass(R.drawable.red_logo,"Testing2"));

        adapter = new EventAdapterYourEvents(eventList, this);
        friendEventRecyler.setAdapter(adapter);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }



}