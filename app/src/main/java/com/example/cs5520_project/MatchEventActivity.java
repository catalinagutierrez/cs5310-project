package com.example.cs5520_project;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MatchEventActivity extends AppCompatActivity {

    RecyclerView eventRecyler;
    RecyclerView.Adapter adapter;
    ArrayList<EventHelperClass> eventList = new ArrayList<>();
    String URL1 = "https://serpapi.com/search.json?engine=google_events&q=Events+in+Austin&hl=en&gl=us\n" +
            "&api_key=d139e1b5e2e1539f21fac65a05f3599f6b7cfe436a39a39392a5fc730c5b3bab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_match_event);

        eventRecyler = findViewById(R.id.event_recycler);
        String URL = getIntent().getStringExtra("URL");
        parseApiData(URL);
    }

    private void eventRecyler(ArrayList eventList) {
        eventRecyler.setHasFixedSize(true);
        eventRecyler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new EventAdapterFind(eventList,MatchEventActivity.this);
        eventRecyler.setAdapter(adapter);
    }

    public void parseApiData(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("events_results");
                    if(jsonArray.length() > 0) {
                        for(int i=0; i < jsonArray.length(); i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String description = "";
                            if (jsonObject1.has("description")){
                                description = jsonObject1.getString("description");
                            }
                            String image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQez2075rzHXB0DoVoojer29WCh0wPNxO-HNgfrYEawd3AU6c1_oXrKQkA&s";
                            if(jsonObject1.has("thumbnail")){
                                image = jsonObject1.getString("thumbnail");
                            }
                            eventList.add(new EventHelperClass(image,description));
                        }

                    }
                    eventRecyler(eventList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}