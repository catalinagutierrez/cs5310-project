package com.example.cs5520_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FindEventActivity extends AppCompatActivity implements View.OnClickListener {
    Button findEventbtn;
    Button todayBtn,tomorrowBtn,weekendBtn,thisWeekBtn,thisMonthBtn,nextWeekBtn;
    Button freeBtn, underFifteen,underThirty,underFifty,underHundred,noLimit;
    Button musicBtn,artsBtn,travelBtn,healthBtn,foodBtn,onlineBtn,hobbiesBtn,sportsBtn,businessBtn;
    String mURL="https://serpapi.com/search.json?engine=google_events&hl=en&gl=us\n" +
            "&api_key=5c030de392f00a9601c21416005ea917dabe08e5e8742d41cf8be0a4a566e7f1&q=Events+";
    String htichips = "&htichips=event_type:,date:";
    int black = Color.BLACK;
    int white = Color.WHITE;
    Boolean clickedDate = false;
    Map<Button,String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_find_event);

        String uid = getIntent().getStringExtra("uid");
        String cityName = getIntent().getStringExtra("location");

        todayBtn = findViewById(R.id.todayBtn);
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
        mURL = mURL + cityName;
        findEventbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mURL = mURL + htichips;
                Intent intent = new Intent(FindEventActivity.this, MatchEventActivity.class);
                intent.putExtra("URL", mURL);
                Log.e("slay2",mURL);
                intent.putExtra("uid",uid);
                startActivity(intent);
                finish();
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
            case R.id.onlineBtn:
                clickedDateButton(onlineBtn,"Virtual-Event");
                break;
            case R.id.todayBtn:
                clickedDateButton(todayBtn,"today");
                break;
            case R.id.tomorrowBtn:
                clickedDateButton(tomorrowBtn,"tomorrow");
                break;
            case R.id.thisWeekBtn:
                clickedDateButton(thisWeekBtn,"week");
                break;
            case R.id.weekendBtn:
                clickedDateButton(weekendBtn,"weekend");
                break;
            case R.id.nextWeekBtn:
                clickedDateButton(nextWeekBtn,"next_week");
                break;
            case R.id.thisMonthBtn:
                clickedDateButton(thisMonthBtn,"month");
                break;

        }
    }

    private void clickedButton(Button b) {
        if (b.getCurrentTextColor() == black){
            b.setBackgroundResource(R.drawable.btn_bg_black);
            b.setTextColor(white);
            mURL = mURL + "+"+b.getText();
        } else {
            b.setBackgroundResource(R.drawable.btn_bg);
            b.setTextColor(black);
            mURL = mURL.replace("+"+b.getText(),"");
        }
    }

    private void clickedDateButton(Button b, String s) {
        if(b != onlineBtn) {
            if (!clickedDate && b.getCurrentTextColor() == black) {
                b.setBackgroundResource(R.drawable.btn_bg_black);
                b.setTextColor(white);
                htichips = htichips.replace(",date:",",date:" + s);
                clickedDate = true;
                map.put(b,s);
            } else if (clickedDate && b.getCurrentTextColor() == white){
                b.setBackgroundResource(R.drawable.btn_bg);
                b.setTextColor(black);
                htichips = htichips.replace(",date:" + s, ",date:");
                clickedDate = false;
                map.remove(b);
            } else if (clickedDate && b.getCurrentTextColor() == black ){
                for (Button btn : map.keySet()){
                    clickedDateButton(btn,map.get(btn));
                    clickedDate = true;
                }
                clickedDate = false;
                clickedDateButton(b,s);
                map.put(b,s);
                clickedDate = true;
            }
        } else if(b.getCurrentTextColor() == black) {
            b.setBackgroundResource(R.drawable.btn_bg_black);
            b.setTextColor(white);
            htichips = htichips.replace("=event_type:","=event_type:"+ s);
        } else {
            b.setBackgroundResource(R.drawable.btn_bg);
            b.setTextColor(black);
            htichips = htichips.replace("=event_type:"+ s,"=event_type:");
        }
    }
}