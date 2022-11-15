package com.example.cs5520_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.cs5520_project.messages.MessagesAdapter;
import com.example.cs5520_project.messages.MessagesList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageActivity extends AppCompatActivity {
    String uid;
    ImageView profileImage;
    private RecyclerView messagesRecyclerView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    Context context;
    List<MessagesList> messagesLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        uid = getIntent().getStringExtra("uid");
        profileImage = findViewById(R.id.userProfileImage);
        messagesRecyclerView = findViewById(R.id.messagesRecylerView);
        messagesRecyclerView.setHasFixedSize(true);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String profileImageUrl = snapshot.child("Eventure Users").child(uid).child("profile_image").getValue(String.class);
                if (!profileImageUrl.isEmpty()){
                    Picasso.get().load(profileImageUrl).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messagesLists.clear();

                for (DataSnapshot dataSnapshot : snapshot.child("Eventure Users").getChildren()){
                    List<MessagesList> messagesLists = dataSnapshot.child(uid).child("messagesLists").getValue(List.class);
                }
                messagesRecyclerView.setAdapter(new MessagesAdapter(messagesLists, MessageActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}