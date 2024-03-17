package com.cs125.helth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    String distance;
    String time;
    String pace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Button back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(view -> back());

        Button home = (Button) findViewById(R.id.home_button);
        home.setOnClickListener(view -> home());

        Intent intent = getIntent();
        double distance_double = intent.getDoubleExtra("predictedDistance", 0.0);
        double time_double = intent.getDoubleExtra("predictedTime", 0.0);
        double pace_double = intent.getDoubleExtra("predictedPace", 0.0);

        time = convertToTime(time_double);

        distance = String.format("%.2f", distance_double);

        pace = convertToTime(pace_double);

        TextView paceView = findViewById(R.id.pace_value);
        paceView.setText(pace);
        TextView distanceView = findViewById(R.id.distance_value);
        distanceView.setText(distance);
        TextView timeView = findViewById(R.id.time_value);
        timeView.setText(time);

    }

    public void back() {
        finish();
        Intent NewPage = new Intent(ResultsActivity.this, RecommendationActivity.class);
        startActivity(NewPage);
    }
    public void home() {
        finish();
        Intent NewPage = new Intent(ResultsActivity.this, WelcomeActivity.class);
        startActivity(NewPage);
    }

    @SuppressLint("DefaultLocale")
    public String convertToTime(double value){
        int minutes = (int) value;
        int seconds = (int) ((value - minutes) * 60);

        // Format the pace in minute:second format
        return String.format("%d:%02d", minutes, seconds);
    }
}