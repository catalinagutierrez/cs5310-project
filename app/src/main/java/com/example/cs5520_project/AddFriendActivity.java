package com.example.cs5520_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddFriendActivity extends AppCompatActivity {

    Button addFriendButton;
    EditText friendNameInput;
    String uid;
    boolean isFriend = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        friendNameInput = findViewById(R.id.addFriendText);
        addFriendButton = findViewById(R.id.addFriendButton);
        uid = getIntent().getStringExtra("uid");

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = friendNameInput.getText().toString();
                addFriend(username);
            }
        });

    }

    public void addFriend(String username) {

       // String uid;
        FirebaseDatabase.getInstance().getReference().child("Eventure Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userExists = false;
                boolean sameUser = false;
                String uidFriend = "";
                for (DataSnapshot data : snapshot.getChildren()) {
                    if (data.child("username").getValue().toString().equals(username)) {
                        if(data.getKey().equals(uid)) {
                            sameUser = true;
                        } else {
                            userExists = true;
                            uidFriend = data.getKey();
                        }
                    }
                }

                // If the user tries to add themself
                if(sameUser) {
                    Toast.makeText(AddFriendActivity.this, "Cannot add yourself!", Toast.LENGTH_SHORT).show();
                }
                // If a user does not exist in the database
                else if (!userExists){
                    Toast.makeText(AddFriendActivity.this, "User does not exist!", Toast.LENGTH_SHORT).show();
                }
                // If the user exists, add them if they are not an existing friend
                else if(userExists) {
                    if(isFriend(username)) {
                        Toast.makeText(AddFriendActivity.this, "You are already friends with this user!", Toast.LENGTH_SHORT).show();
                    } else {
                        addFriendToList(uidFriend);
                    }
                }
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error){
            }
        });
    }

    public boolean isFriend(String username) {
        isFriend = false;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Eventure Users").child(uid);
        ref.child("friendsList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()) {
                    if(data.getValue().toString().equals(username)) {
                        isFriend = true;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return isFriend;
    }

    public void addFriendToList(String uidFriend) {
        String fid = FirebaseDatabase.getInstance().getReference("Eventure Users").child(uid).child("friendsList").push().getKey();
        FirebaseDatabase.getInstance().getReference("Eventure Users").child(uid).child("friendsList").child(fid).setValue(uidFriend).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddFriendActivity.this, "Friend added!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddFriendActivity.this, "Friend was not added!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}