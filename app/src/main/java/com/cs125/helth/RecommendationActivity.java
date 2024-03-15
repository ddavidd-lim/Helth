package com.cs125.helth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;

public class RecommendationActivity extends AppCompatActivity {

    private Spinner difficultySpinner;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        difficultySpinner = findViewById(R.id.difficulty_spinner);

        Button back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(view -> back());

        Button calculate = (Button) findViewById(R.id.calculate_button);
        calculate.setOnClickListener(view -> calculate());

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDatabase();
        Cursor cursor = databaseHelper.query("SELECT * FROM activity " +
                "WHERE total_time > '0:00:00' AND total_distance_miles > '0.5'" + // Filter out rows where total_time is 0
                "ORDER BY date_of_activity DESC " +
                "LIMIT 6", new String[]{});
        ArrayList<float[]> tuples = new ArrayList<>();
        float[] weights = {10.5f, 20.3f, 15.8f};
        while (cursor.moveToNext()) {
            String string_time = cursor.getString(cursor.getColumnIndex("total_time"));
            String string_heartrate = cursor.getString(cursor.getColumnIndex("average_heart_rate"));
            String string_distance = cursor.getString(cursor.getColumnIndex("total_distance_miles"));

            float time_num = parseTime(string_time);
            float heart_rate = parsefloats(string_heartrate);
            float distance = parsefloats(string_distance);
            float pace_num = (float) time_num / distance;
            String difficulty = difficultySpinner.getSelectedItem().toString();
            float diff_num = 0.0F;
            switch (difficulty) {
                case "Easy":
                    diff_num = 1.0F;
                    break;
                case "Medium":
                    diff_num = 2.0F;
                    break;
                case "Hard":
                    diff_num = 3.0F;
                    break;
            }

            float[] tuple = {time_num, heart_rate, distance, diff_num};
//            String selectedItem = difficultySpinner.getSelectedItem().toString();
//
//            String pace = String.format("%.2f", pace_num);
//            String paceDisplay = "Pace: " + pace + "/mile";
//
//            String distanceDisplay = distance + " Mile Run";
//
//            String time = String.format("%.2f", time_num);
//            String timeDisplay = time + " Mile Run";
        }
        cursor.close();
    }

    public void back() {
        finish();
        Intent NewPage = new Intent(RecommendationActivity.this, WelcomeActivity.class);
        startActivity(NewPage);
    }
    public void calculate() {
        finish();
        Intent NewPage = new Intent(RecommendationActivity.this, ResultsActivity.class);


        startActivity(NewPage);
    }

    public float parseTime(String timeString) {
        float minutes = 0; // Default value if parsing fails

        // Split the time string into components using ":"
        String[] timeComponents = timeString.split(":");
        minutes = Float.parseFloat(timeComponents[1]);

        return minutes;
    }

    public float parsefloats(String str_float) {
        float val = Float.parseFloat(str_float);

        return Float.parseFloat(String.format("%.2f", val));
    }
}