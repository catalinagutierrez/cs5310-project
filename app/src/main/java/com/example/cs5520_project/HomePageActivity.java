package com.example.cs5520_project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomePageActivity extends AppCompatActivity implements LocationListener, NavigationView.OnNavigationItemSelectedListener {
    Button findNewEventBtn;
    RecyclerView eventRecyler, friendEventRecyler;
    EventAdapterYourEvents adapter;
    RecyclerView.Adapter friendsAdapter;
    String uid, username;
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    ArrayList<EventHelperClass> friendEventList = new ArrayList<>();
    ArrayList<EventHelperClass> addedEventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        uid = getIntent().getStringExtra("uid");
        username = getIntent().getStringExtra("username");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Eventure Users").child(uid);
        ref.child("addedEventList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addedEventList.clear();
                for(DataSnapshot data : snapshot.getChildren()) {
                    EventHelperClass event = new EventHelperClass(data.child("image").getValue().toString(), data.child("description").getValue().toString());
                    addedEventList.add(event);
                    adapter.setEvents(addedEventList);
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


        drawerLayout = findViewById(R.id.drawer_view);
        navigationView = findViewById(R.id.nav_view);
        ImageView navPanel = findViewById(R.id.sidePanel);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(HomePageActivity.this);
        navigationView.setCheckedItem(R.id.nav_home);
        navPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });


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
        friendsAdapter = new EventAdapterYourEvents(friendEventList, this);
        friendEventRecyler.setAdapter(friendsAdapter);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
//                Intent intent = new Intent(this, HomePageActivity.class);
//                intent.putExtra("username",username);
//                intent.putExtra("uid",uid);
//                startActivity(intent);
//                finish();
                break;
            case R.id.nav_find_events:
                Intent event_intent = new Intent(this,FindEventActivity.class);
                event_intent.putExtra("uid",uid);
                startActivity(event_intent);
                break;
            case R.id.nav_add_friends:
                break;
            case R.id.nav_logout:
                Intent logout_intent = new Intent(this, LoginActivity.class);
                startActivity(logout_intent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }
}