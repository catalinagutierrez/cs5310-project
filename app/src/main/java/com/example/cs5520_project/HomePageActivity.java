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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs5520_project.messages.MessagesList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomePageActivity extends AppCompatActivity implements LocationListener {
    Button findNewEventBtn;
    RecyclerView eventRecyler, friendEventRecyler;
    EventAdapterYourEvents adapter;
    RecyclerView.Adapter friendsAdapter;
    String uid;

    ArrayList<EventHelperClass> eventList = new ArrayList<>();
    ArrayList<EventHelperClass> addedEventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        uid = getIntent().getStringExtra("uid");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Eventure Users").child(uid);
       // String key = ref.child("addedEventList").push().getKey();
        ref.child("addedEventList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addedEventList.clear();
                for(DataSnapshot data : snapshot.getChildren()) {
                    EventHelperClass event = new EventHelperClass(data.child("image").getValue().toString(), data.child("description").getValue().toString());
                    addedEventList.add(event);
                    adapter.setEvents(addedEventList);
                    Log.e("slay", String.valueOf(addedEventList.size()));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        findNewEventBtn = findViewById(R.id.findNewEventBtn);
        findNewEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(HomePageActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent(HomePageActivity.this,LocationActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(HomePageActivity.this,FindEventActivity.class);
                    intent.putExtra("uid",uid);
                    startActivity(intent);
                }
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
        adapter = new EventAdapterYourEvents(addedEventList, this);
        eventRecyler.setAdapter(adapter);
    }

    private void friendEventRecyler() {
        friendEventRecyler.setHasFixedSize(true);
        friendEventRecyler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        friendsAdapter = new EventAdapterYourEvents(eventList, this);
        friendEventRecyler.setAdapter(friendsAdapter);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }



}