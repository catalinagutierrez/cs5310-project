package com.example.cs5520_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class EventAdapterFind extends RecyclerView.Adapter<EventAdapterFind.EventViewHolder> {

    ArrayList<EventHelperClass> eventList;
    ArrayList<EventHelperClass> addedEventList;
    EventHelperClass eventHelperClass;
    Context context;
    String uid;

    public EventAdapterFind(ArrayList<EventHelperClass> eventList, Context context, String uid) {
        this.eventList = eventList;
        this.context = context;
        this.uid = uid;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event,parent,false);
        EventViewHolder eventViewHolder = new EventViewHolder(view);
        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        eventHelperClass = eventList.get(position);
        holder.description.setText(eventHelperClass.getDescription());
        Picasso.get().load(eventHelperClass.getImage()).into(holder.image);
        holder.addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventHelperClass event = new EventHelperClass(eventHelperClass.getImage(),eventHelperClass.getDescription());
                addEvent(event);
            }
        });
    }

    public void addEvent(EventHelperClass event) {
        String eid = FirebaseDatabase.getInstance().getReference("Eventure Users").child(uid).child("addedEventList").push().getKey();
        FirebaseDatabase.getInstance().getReference("Eventure Users").child(uid).child("addedEventList").child(eid).setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView description;
        Button addEvent;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.eventImage);
            description = itemView.findViewById(R.id.eventDescription);
            addEvent = itemView.findViewById(R.id.eventRegisterBtn);
        }
    }


}
