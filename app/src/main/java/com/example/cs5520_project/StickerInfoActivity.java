package com.example.cs5520_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    Button sendStickerBtn;
    UserInfo currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_info);

        imageView = findViewById(R.id.imageInfo);
        textView = findViewById(R.id.imgTitle);
        receivedView = findViewById(R.id.ReceiveCount);
        sentView = findViewById(R.id.stickerCount);

        currentUser = (UserInfo) getIntent().getSerializableExtra("CURRENT_USER");

        Intent intent = getIntent();
        String stickerName = intent.getStringExtra("TITLE");
        String selectedFriend = intent.getStringExtra("SELECTED_FRIEND");
        imageView.setImageResource(intent.getIntExtra("IMAGE",0));
        textView.setText(stickerName);

        receivedView.setText("Times sent: "+ currentUser.receivedStickers.get(stickerName));
        sentView.setText("Times received: "+ currentUser.sentStickers.get(stickerName));

        sendStickerBtn = findViewById(R.id.sendBtn);
        sendStickerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

            }
        });
    }
}