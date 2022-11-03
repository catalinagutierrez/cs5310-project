package com.example.cs5520_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class StickerInfoActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private TextView receivedView, sentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_info);

        imageView = findViewById(R.id.imageInfo);
        textView = findViewById(R.id.imgTitle);
        receivedView = findViewById(R.id.ReceiveCount);
        sentView = findViewById(R.id.stickerCount);

        Intent intent = getIntent();
        String stickerName = intent.getStringExtra("title");
        String currentUser = intent.getStringExtra("currentUser");
        String selectedFriend = intent.getStringExtra("selectedFriend");
        imageView.setImageResource(intent.getIntExtra("image",0));
        textView.setText(stickerName);

        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datas : snapshot.getChildren()) {
                    // Get users data from database
                    if (datas.child("username").getValue().toString().equals(currentUser)) {
                        HashMap<String, Integer> receivedStickers =  (HashMap<String, Integer>)datas.child("receivedStickers").getValue();
                        HashMap<String, Integer> sentStickers =  (HashMap<String, Integer>)datas.child("sentStickers").getValue();
                        receivedView.setText("Times sent: "+ receivedStickers.get(stickerName));
                        sentView.setText("Times received: "+ sentStickers.get(stickerName));
                        break;
                    }
                }
            }
                @Override
                public void onCancelled (@NonNull DatabaseError error){
                }
            });
        
        // TODO: Add onClick Listener to "SEND STICKER" button
    }
}