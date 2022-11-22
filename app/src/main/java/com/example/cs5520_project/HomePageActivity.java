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
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    String uid, location = "Boston";
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult activityResult) {
                            int result = activityResult.getResultCode();
                            Intent data = activityResult.getData();

                            if(result == RESULT_OK){
                                location = data.getStringExtra("location");
                                Toast.makeText(HomePageActivity.this, "success " + location , Toast.LENGTH_SHORT).show();
                            } else {
                                location = "Austin";
                            }
                        }
                    });


    ArrayList<EventHelperClass> eventList = new ArrayList<>();
    ArrayList<EventHelperClass> addedEventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page);
        uid = getIntent().getStringExtra("uid");

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
                    activityResultLauncher.launch(intent);
                    //startActivity(intent);
                }else{
                    Intent intent = new Intent(HomePageActivity.this,FindEventActivity.class);
                    intent.putExtra("uid",uid);
                    intent.putExtra("location",location);
                    Log.e("slay1", location);
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

        navPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(HomePageActivity.this);
        navigationView.setCheckedItem(R.id.nav_home);

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
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_find_events:
                break;
            case R.id.nav_logout:
                Log.e("slay2","lol");
                Intent logoutIntent = new Intent(HomePageActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }
}