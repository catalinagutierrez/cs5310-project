package com.example.cs5520_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class EventAdapterFind extends RecyclerView.Adapter<EventAdapterFind.EventViewHolder> {

    ArrayList<EventHelperClass> eventList;

    public EventAdapterFind(ArrayList<EventHelperClass> eventList) {
        this.eventList = eventList;
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
        EventHelperClass eventHelperClass = eventList.get(position);

        holder.image.setImageResource(eventHelperClass.getImage());
        holder.description.setText(eventHelperClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView description;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.eventImage);
            description = itemView.findViewById(R.id.eventDescription);
        }
    }

}
