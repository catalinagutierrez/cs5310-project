package com.example.cs5520_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FindEventActivity extends AppCompatActivity implements View.OnClickListener {
    Button findEventbtn;
    Button todayBtn,tomorrowBtn,weekendBtn,thisWeekBtn,thisMonthBtn,nextWeekBtn;
    Button freeBtn, underFifteen,underThirty,underFifty,underHundred,noLimit;
    Button musicBtn,artsBtn,travelBtn,healthBtn,foodBtn,onlineBtn,hobbiesBtn,sportsBtn,businessBtn;
    String URL = "https://serpapi.com/search.json?engine=google_events&q=Events+in+Austin&hl=en&gl=us\n" +
            "&api_key=d139e1b5e2e1539f21fac65a05f3599f6b7cfe436a39a39392a5fc730c5b3bab";
    String mURL="https://serpapi.com/search.json?engine=google_events&hl=en&gl=us\n" +
            "&api_key=d139e1b5e2e1539f21fac65a05f3599f6b7cfe436a39a39392a5fc730c5b3bab&q=Events+Boston";
    String htichips = "";
    int black = Color.BLACK;
    int white = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_find_event);

        todayBtn = findViewById(R.id.todayBtn);
        tomorrowBtn = findViewById(R.id.tomorrowBtn);
        tomorrowBtn = findViewById(R.id.tomorrowBtn);
        weekendBtn = findViewById(R.id.weekendBtn);
        thisWeekBtn = findViewById(R.id.thisWeekBtn);
        thisMonthBtn = findViewById(R.id.thisMonthBtn);
        nextWeekBtn = findViewById(R.id.nextWeekBtn);
        freeBtn = findViewById(R.id.freeBtn);
        underFifteen = findViewById(R.id.underFifteen);
        underThirty = findViewById(R.id.underThirty);
        underFifty = findViewById(R.id.underFifty);
        underHundred = findViewById(R.id.underHundred);
        noLimit = findViewById(R.id.noLimit);
        musicBtn = findViewById(R.id.musicBtn);
        artsBtn = findViewById(R.id.artsBtn);
        travelBtn = findViewById(R.id.travelBtn);
        healthBtn = findViewById(R.id.healthBtn);
        foodBtn = findViewById(R.id.foodBtn);
        onlineBtn = findViewById(R.id.onlineBtn);
        hobbiesBtn = findViewById(R.id.hobbiesBtn);
        sportsBtn = findViewById(R.id.sportsBtn);
        businessBtn = findViewById(R.id.businessBtn);


        findEventbtn = findViewById(R.id.searchBtn);
        findEventbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindEventActivity.this, MatchEventActivity.class);
                intent.putExtra("URL", mURL);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.freeBtn:
                clickedButton(freeBtn);
                break;
            case R.id.musicBtn:
                clickedButton(musicBtn);
                break;
            case R.id.artsBtn:
                clickedButton(artsBtn);
                break;
            case R.id.travelBtn:
                clickedButton(travelBtn);
                break;
            case R.id.sportsBtn:
                clickedButton(sportsBtn);
                break;
            case R.id.healthBtn:
                clickedButton(healthBtn);
                break;
            case R.id.hobbiesBtn:
                clickedButton(hobbiesBtn);
                break;
            case R.id.businessBtn:
                clickedButton(businessBtn);
                break;
            case R.id.foodBtn:
                clickedButton(foodBtn);
                break;
            case R.id.underFifteen:
                clickedButton(underFifteen);
                break;
            case R.id.underThirty:
                clickedButton(underThirty);
                break;
            case R.id.underFifty:
                clickedButton(underFifty);
                break;
            case R.id.underHundred:
                clickedButton(underHundred);
                break;
        }
    }

    private void clickedButton(Button b) {
        if (b.getCurrentTextColor() == white){
            b.setBackgroundResource(R.drawable.btn_bg);
            b.setTextColor(black);
            mURL = mURL + "+"+b.getText();
            Toast.makeText(this, mURL, Toast.LENGTH_SHORT).show();
        } else {
            b.setBackgroundResource(R.drawable.btn_bg_black);
            b.setTextColor(white);
            mURL = mURL.replace("+"+b.getText(),"");
            Toast.makeText(this, mURL, Toast.LENGTH_SHORT).show();
        }
    }

    private void clickedDateButton(Button b, String s) {
        if (b.getCurrentTextColor() == white){
            b.setBackgroundResource(R.drawable.btn_bg);
            b.setTextColor(black);
            htichips = htichips + "=date:" + s;
            Toast.makeText(this, mURL, Toast.LENGTH_SHORT).show();
        } else {
            b.setBackgroundResource(R.drawable.btn_bg_black);
            b.setTextColor(white);
            htichips = mURL.replace("+"+b.getText(),"");
            Toast.makeText(this, mURL, Toast.LENGTH_SHORT).show();
        }
    }
}