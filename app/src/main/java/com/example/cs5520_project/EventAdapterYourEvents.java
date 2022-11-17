package com.example.cs5520_project;


import android.content.Context;
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

    public EventAdapterYourEvents(ArrayList<EventHelperClass> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
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
        holder.description.setText(eventHelperClass.getDescription());

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