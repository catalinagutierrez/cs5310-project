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
        String userName = intent.getStringExtra("userName");
        String friendName = intent.getStringExtra("friendName");
        imageView.setImageResource(intent.getIntExtra("image",0));
        textView.setText(stickerName);

        FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datas : snapshot.getChildren()) {
                    // Get users data from database
                    if (datas.child("username").getValue().toString().equals(userName)) {
                        // TODO: Loop through sentStickers and receivedStickers in DB,
                        // TODO: gather values and set the receivedView and sentView text.
                        receivedView.setText("Populate me!");
                        sentView.setText("Populate me");
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