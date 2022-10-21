package com.example.cs5520_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiServiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] filters = { "No Filter", "Mono", "Blur", "Sepia", "Paint" };

    private String filter, width, height, caption, request;
    private ImageView responseImageView;
    private RelativeLayout loadingPanel;
    private Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_service);
        getSupportActionBar().hide();

        // Get loading panel
        loadingPanel = findViewById(R.id.loadingPanel);
        loadingPanel.setVisibility(View.GONE);

        // Get SeekBars for Width and Height
        SeekBar sbHeight = findViewById(R.id.seekBar);
        SeekBar sbWidth = findViewById(R.id.seekBar2);

        // Get EditText Field
        EditText captionEditText = findViewById(R.id.captionInput);

        // Get Response Image View
        responseImageView = findViewById(R.id.responseImageView);
        responseImageView.setImageResource(android.R.color.transparent);

        // Set SeekBar Maximums
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        // Set Picture Max Height and Width
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            sbHeight.setMax(metrics.heightPixels);
            sbWidth.setMax(metrics.widthPixels/2);
        } else {
            sbHeight.setMax(metrics.heightPixels/2);
            sbWidth.setMax(metrics.widthPixels);
        };
        height = String.valueOf((int)sbHeight.getMax());
        width = String.valueOf(sbWidth.getMax());

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
                loadingPanel.setVisibility(View.VISIBLE);
                caption = captionEditText.getText().toString();

                // Handle No Filter Case
                if(filter.equals("No Filter")) {
                    filter = "none";
                }

                if(caption.equals("")) {
                    request = "https://cataas.com/cat?filter=" + filter.toLowerCase() + "&width=" + width + "&height=" + height;
                } else {
                    request = "https://cataas.com/cat/says/"+ caption + "?filter=" + filter.toLowerCase() + "&width=" + width + "&height=" + height;
                }
                
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // doInBackground equivalent
                        try {
                            URL url = new URL(request);

                            System.out.println(request);

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            InputStream is = conn.getInputStream();
                            img = BitmapFactory.decodeStream(is);
                        }
                        catch(Exception e) {
                            System.out.println(e);
                        }

                        runOnUiThread(new Runnable() {
                            public void run() {
                                // onPostExecute equivalent
                                loadingPanel.setVisibility(View.GONE);
                                responseImageView.setImageBitmap(img);
                            }
                        });
                    }
                }).start();
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

