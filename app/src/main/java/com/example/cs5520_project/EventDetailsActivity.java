package com.example.cs5520_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EventDetailsActivity extends AppCompatActivity {
    Button deleteEventBtn, shareBtn, saveEventBtn;
    TextView eventDetailsTitle, eventDetailsDescription, friendsAttendingText;
    ImageView eventDetailsImage;
    String currentUserId;
    boolean isOwnEvent;
    ArrayList<String> friendsAttending;
    ArrayList<String> friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        friendsAttending = new ArrayList<String>();
        friendsList = new ArrayList<String>();

        String description = getIntent().getStringExtra("description");
        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        String link = getIntent().getStringExtra("link");
        String currentUserId = getIntent().getStringExtra("uid");
        isOwnEvent = getIntent().getBooleanExtra("isOwnEvent", false);

        // Query which friends are attending
        if(isOwnEvent) {
            friendsList = getIntent().getStringArrayListExtra("friendsList");
            queryFriendsAttending(title);
        }

        eventDetailsDescription = (TextView) findViewById(R.id.eventDetailsDescription);
        eventDetailsTitle = (TextView) findViewById(R.id.eventDetailsTitle);
        eventDetailsImage = (ImageView) findViewById(R.id.eventDetailsImage);
        friendsAttendingText = (TextView) findViewById(R.id.friendsAttendingText);

        eventDetailsTitle.setText(title);
        eventDetailsDescription.setText(description);
        Picasso.get().load(image).into(eventDetailsImage);


        shareBtn = (Button) findViewById(R.id.shareBtn);
        deleteEventBtn = (Button) findViewById(R.id.deleteEventBtn);
        saveEventBtn = (Button) findViewById(R.id.saveEventBtn);

        if(isOwnEvent){
            deleteEventBtn.setVisibility(View.VISIBLE);
        }else{
            saveEventBtn.setVisibility(View.VISIBLE);
        }

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String body = "Checkout this event I found on Eventure!";
                String sub = link;
                intent.putExtra(Intent.EXTRA_TEXT, body);
                intent.putExtra(Intent.EXTRA_TEXT, sub);
                startActivity(Intent.createChooser(intent, "Share this event"));
            }
        });

        deleteEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EventDetailsActivity.this)
                        .setTitle("Delete event")
                        .setMessage("Are you sure you want to delete this event?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Eventure Users").child(currentUserId);
                                ref.child("addedEventList").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot data : snapshot.getChildren()) {
                                            if(data.child("title").getValue().equals(title)){
                                                data.getRef().removeValue();
                                                finish();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        saveEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventHelperClass event = new EventHelperClass(image,description,title,link);
                String eid = FirebaseDatabase.getInstance().getReference("Eventure Users").child(currentUserId).child("addedEventList").push().getKey();
                FirebaseDatabase.getInstance().getReference("Eventure Users").child(currentUserId).child("addedEventList").child(eid).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(EventDetailsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EventDetailsActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    private void queryFriendsAttending(String event) {
        friendsAttending.clear();
        for (String friend : friendsList) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Eventure Users").child(friend);
            ref.child("addedEventList").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot data : snapshot.getChildren()) {
                        if(data.child("title").getValue().toString().equals(event)) {
                            friendsAttending.add(friend);
                        }
                    }
                    String tempText = friendsAttending.size() == 1 ? " friend is attending!" : " friends are attending!";
                    friendsAttendingText.setText(friendsAttending.size() + tempText);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
}