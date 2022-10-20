package com.example.cs5520_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;

public class ApiServiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] filters = { "No Filter", "Mono", "Blur", "Sepia", "Paint" };

    private String filter, width, height, caption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_service);

        // Get SeekBars for Width and Height
        SeekBar sbHeight = findViewById(R.id.seekBar);
        SeekBar sbWidth = findViewById(R.id.seekBar2);

        // Get EditText Field
        EditText captionText = findViewById(R.id.captionInput);

        // Set SeekBar Maximums
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        sbHeight.setMax(metrics.heightPixels);
        sbWidth.setMax(metrics.widthPixels);

        sbHeight.setProgress(sbHeight.getMax());
        sbWidth.setProgress(sbWidth.getMax());

        // Set Height Listener
        sbHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                height = String.valueOf(progress);
            }
        });

        // Set Width Listener
        sbWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                width = String.valueOf(progress);
            }
        });

        // Set Spinner Options
        Spinner filterOptions = findViewById(R.id.filterSpinner);
        filterOptions.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, filters);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterOptions.setAdapter(ad);

        // Set Generate Button Listener
        Button generateBtn = findViewById(R.id.GenerateBtn);
        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caption = captionText.getText().toString();

                String request = "https://cataas.com/cat/says/";
                request.concat(captionText + "?");
                request.concat("filter=" + filter);
                request.concat("&width=" + width);
                request.concat("&height=" + height);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        filter = filters[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        filter = filters[0];
    }
}