package com.example.cs5520_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class EventAdapterFind extends RecyclerView.Adapter<EventAdapterFind.EventViewHolder> {

    ArrayList<EventHelperClass> eventList;
    private static Bitmap img;
    EventHelperClass eventHelperClass;
    Context context;



    public EventAdapterFind(ArrayList<EventHelperClass> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
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
        Log.e("slay1", eventList.toString());
        Glide.with(context).load(eventHelperClass.getImage())
                .placeholder(R.drawable.blue_bg)
                .error(R.drawable.ic_launcher_foreground).into(holder.image);
        //URL newurl = new URL(eventHelperClass.getImage());
        //Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        //holder.image.setImageBitmap(mIcon_val);
        //holder.image.setImageBitmap(getBitmapFromURL(eventHelperClass.getImage()));
        //setBitmapFromNetwork(eventHelperClass, holder);
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


//    public void setBitmapFromNetwork(EventHelperClass e, EventViewHolder h) {
//
//        Runnable runnable = new Runnable() {
//            Bitmap bitmap = null;
//
//            @Override
//            public void run() {
//                try {
//                    bitmap = getBitmapFromLink(e.getImage());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                h.image.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        h.image.setImageBitmap(bitmap);
//                        h.description.setText(e.getDescription());
//                    }
//                });
//            }};
//        new Thread(runnable).start();
//    }
//
//
//    public Bitmap getBitmapFromLink(String URL) throws IOException{
//        HttpURLConnection conn =(HttpURLConnection) new URL(URL).openConnection();
//        conn.setDoInput(true);
//        InputStream ism = conn.getInputStream();
//        Bitmap bitmap = BitmapFactory.decodeStream(ism);
//        if (ism != null) {
//            ism .close();
//        }
//        return bitmap;
//    }




}
