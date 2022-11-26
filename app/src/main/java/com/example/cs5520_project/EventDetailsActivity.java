package com.example.cs5520_project;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class EventDetailsActivity extends AppCompatActivity {
    Button deleteEventBtn, shareBtn;
    TextView eventDetailsTitle, eventDetailsDescription;
    ImageView eventDetailsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        String description = getIntent().getStringExtra("description");
        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");

        eventDetailsDescription = (TextView) findViewById(R.id.eventDetailsDescription);
        eventDetailsTitle = (TextView) findViewById(R.id.eventDetailsTitle);
        eventDetailsImage = (ImageView) findViewById(R.id.eventDetailsImage);

        eventDetailsTitle.setText(title);
        eventDetailsDescription.setText(description);
        Picasso.get().load(image).into(eventDetailsImage);

    }
}