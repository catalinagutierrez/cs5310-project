package com.example.cs5520_project;


import android.content.Context;
import android.content.Intent;
import android.media.metrics.Event;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class EventAdapterYourEvents extends RecyclerView.Adapter<EventAdapterYourEvents.EventViewHolder> {

    private Context context;
    private ArrayList<EventHelperClass> eventList;
    private String uid;
    private boolean isOwnEvent;
    private ArrayList<String> friendsList;

    public EventAdapterYourEvents(ArrayList<EventHelperClass> eventList, Context context, String uid, boolean isOwnEvent, ArrayList<String> friendsList) {
        this.eventList = eventList;
        this.context = context;
        this.uid = uid;
        this.isOwnEvent = isOwnEvent;
        this.friendsList = friendsList;
    }

    public void setEvents(ArrayList<EventHelperClass> events) {
        eventList = events;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_card_view,parent,false);
        EventViewHolder eventViewHolder = new EventViewHolder(view);
        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder,int position) {
        EventHelperClass eventHelperClass = eventList.get(position);
        Picasso.get().load(eventHelperClass.getImage()).into(holder.image);
        holder.description.setText(eventHelperClass.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context , EventDetailsActivity.class);
                intent.putExtra("description", eventHelperClass.getDescription());
                intent.putExtra("title", eventHelperClass.getTitle());
                intent.putExtra("image", eventHelperClass.getImage());
                intent.putExtra("link", eventHelperClass.getLink());
                intent.putExtra("uid", uid);
                intent.putExtra("isOwnEvent", isOwnEvent);
                intent.putExtra("friendsList", friendsList);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView description;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.horizontal_image);
            description = itemView.findViewById(R.id.horizontal_text);
        }

    }



}