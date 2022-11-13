package com.example.cs5520_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {

    TextView historyText;
    UserInfo currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Bundle bundle = getIntent().getExtras();
        currentUser = (UserInfo) getIntent().getSerializableExtra("CURRENT_USER");
        historyText = findViewById(R.id.historyText);

        FirebaseDatabase.getInstance().getReference().child("Transactions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historyText.setText("");
                for (DataSnapshot data : snapshot.getChildren()) {
                    // if user exists, load their user profile
                    String receiver = data.child("receiver").getValue().toString();
                    if (currentUser.getUsername().equals(receiver)) {
                        String sender = data.child("sender").getValue().toString();
                        String stickerName = data.child("sticker").getValue().toString();
                        String time = data.child("date").getValue().toString();

                        String transaction = stickerName + " was sent by " + sender + " on " + time + "\n\n";
                        historyText.append(transaction);
                    }
                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
            }
        });
    }
}