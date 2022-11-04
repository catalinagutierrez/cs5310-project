package com.example.cs5520_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    TextView username;
    Spinner spinner;
    private RecyclerView recyclerView;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    RecyclerView.LayoutManager layoutManager;
    RecylerViewAdapter recylerViewAdapter;
    int arr[] = {R.drawable.emoji_1,R.drawable.emoji_10,R.drawable.emoji_3,
    R.drawable.emoji_4,R.drawable.emoji_5, R.drawable.emoji_6, R.drawable.emoji_7,
    R.drawable.emoji_8,R.drawable.emoji_9, R.drawable.emoji_2};
    List<String> friendsList;
    String selectedFriend;
    UserInfo currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        username = findViewById(R.id.userId);
        Bundle bundle = getIntent().getExtras();
        currentUser = (UserInfo) getIntent().getSerializableExtra("CURRENT_USER");
        friendsList = new ArrayList<>();

        //TODO - Swap - handle default option. Make sure when none were selected selectedFriend defaults to the first
        //TODO - Swap - handle what happens when friendList is empty in the db
        selectedFriend = "";

        // Add users to friends dropdown
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(UserProfileActivity.this, android.R.layout.simple_list_item_1, friendsList);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedFriend = friendsList.get(i);
            }

            //TODO handle this better
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Nothing selected", Toast.LENGTH_SHORT).show();
            }
        });

        // Load Users
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference();
        reference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friendsList.clear();
                for(DataSnapshot data : snapshot.getChildren()) {
                    String friendUsername = data.child("username").getValue().toString();
                    if(!currentUser.getUsername().equals(friendUsername)) {
                        friendsList.add(friendUsername);
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        reference.child("Transactions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()) {
                    String receiver = data.child("receiver").getValue().toString();
                    if(currentUser.getUsername().equals(receiver)) {
                        String sender = data.child("sender").getValue().toString();
                        String stickerName = data.child("sticker").getValue().toString();
                        currentUser.incrementStickerCount("received", stickerName);
                        reference.child("Users").child(currentUser.uid).setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(UserProfileActivity.this, stickerName + " sent by " + sender, Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(UserProfileActivity.this, "Failed to send, please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recylerViewAdapter = new RecylerViewAdapter(arr,this);
        recyclerView.setAdapter(recylerViewAdapter);
        recyclerView.setHasFixedSize(true);

        username.setText(currentUser.username);
    }


    public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.MyViewHolder> {

        int []arr;
        Context context;

        public RecylerViewAdapter(int[] arr, Context context) {
            this.arr = arr;
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.imageView.setImageResource(arr[position]);
            holder.textView.setText("Emoji " + (position + 1));
        }

        @Override
        public int getItemCount() {
            return arr.length;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView imageView;
            TextView textView;
            TextView clickedView;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                textView = itemView.findViewById(R.id.textView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,StickerInfoActivity.class);
                intent.putExtra("IMAGE",arr[getAbsoluteAdapterPosition()]);
                intent.putExtra("TITLE","Emoji " + (getAbsoluteAdapterPosition() + 1));
                intent.putExtra("CURRENT_USER", currentUser);
                intent.putExtra("SELECTED_FRIEND", selectedFriend);
                context.startActivity(intent);
            }
        }

    }

}