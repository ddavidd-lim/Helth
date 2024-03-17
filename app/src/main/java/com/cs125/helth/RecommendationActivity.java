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
import java.util.List;

public class RecommendationActivity extends AppCompatActivity {

    Spinner difficultySpinner;
    List<DataPoint> distanceData;
    List<DataPoint> timeData;

    double average_heart_rate;
    double diff_scaling;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        // Get buttons
        difficultySpinner = findViewById(R.id.difficulty_spinner);

        Button back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(view -> back());

        Button calculate = (Button) findViewById(R.id.calculate_button);
        calculate.setOnClickListener(view -> calculate());

        // Query Database
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDatabase();
        Cursor cursor = databaseHelper.query("SELECT * FROM activity " +
                "WHERE total_time > '0:00:00' AND total_distance_miles > '0.5'" + // Filter out rows where total_time is 0
                "ORDER BY date_of_activity DESC " +
                "LIMIT 10", new String[]{});
        distanceData = new ArrayList<>();
        timeData = new ArrayList<>();
        double heartrate_sum = 0.0;
        while (cursor.moveToNext()) {
            String string_time = cursor.getString(cursor.getColumnIndex("total_time"));
            String string_heartrate = cursor.getString(cursor.getColumnIndex("average_heart_rate"));
            String string_distance = cursor.getString(cursor.getColumnIndex("total_distance_miles"));

            // Add data from last 6 runs to training data
            double heart_rate = parseDouble(string_heartrate);
            heartrate_sum += heart_rate;
            double time_num = parseTime(string_time);
            double distance = parseDouble(string_distance);
            distanceData.add(new DataPoint(heart_rate, distance));
            timeData.add(new DataPoint(heart_rate, time_num));

        }
        average_heart_rate = heartrate_sum /10;
        cursor.close();
    }

    public void back() {
        finish();
        Intent NewPage = new Intent(RecommendationActivity.this, WelcomeActivity.class);
        transferName(NewPage);
        startActivity(NewPage);
    }
    public void calculate() {
        KNNRegressor distanceKNN = new KNNRegressor(distanceData, 3);
        KNNRegressor timeKNN = new KNNRegressor(timeData, 3);
        // Difficulty scaling
        String difficulty = difficultySpinner.getSelectedItem().toString();

        switch (difficulty) {
            case "Easy":
                diff_scaling = 0.25;
                break;
            case "Medium":
                diff_scaling = 1.0;
                break;
            case "Hard":
                diff_scaling = 2;
                break;
        }
        double target_heart_rate = average_heart_rate * diff_scaling;
        double predictedDistance = distanceKNN.predict(target_heart_rate);
        double predictedTime = timeKNN.predict(target_heart_rate);
        double predictedPace = predictedTime / predictedDistance;
        finish();
        Intent NewPage = new Intent(RecommendationActivity.this, ResultsActivity.class);
        NewPage.putExtra("predictedDistance", predictedDistance);
        NewPage.putExtra("predictedTime", predictedTime);
        NewPage.putExtra("predictedPace", predictedPace);
        transferName(NewPage);
        startActivity(NewPage);
    }

    public double parseTime(String timeString) {
        double minutes = 0; // Default value if parsing fails

        // Split the time string into components using ":"
        String[] timeComponents = timeString.split(":");
        minutes = Double.parseDouble(timeComponents[1]);

        return minutes;
    }

    public double parseDouble(String str_double) {
        double newDouble = Double.parseDouble(str_double);

        return Double.parseDouble(String.format("%.2f", newDouble));
    }

    public void transferName(Intent NewPage){
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int uid = intent.getIntExtra("uid", -1);
        NewPage.putExtra("name", name);
        NewPage.putExtra("uid", uid);
    }
}