package com.cs125.helth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    String name;
    int uid;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        uid = intent.getIntExtra("uid", -1);

        TextView title = findViewById(R.id.title);
        title.setText("Welcome " + name);

        Button logout = findViewById(R.id.logout_button);
        logout.setOnClickListener(view -> logout());

        Button recommend = findViewById(R.id.recommend_button);
        recommend.setOnClickListener(view -> recommend());

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDatabase();
        Cursor cursor = databaseHelper.query("SELECT * FROM activity " +
                "WHERE uid = " + uid
                + " AND total_time > '0:00:00' AND total_distance_miles > '0.5'" + // Filter out rows where total_time is 0
                "ORDER BY date_of_activity DESC " +
                "LIMIT 10", new String[]{});

        ArrayList<Run> runs = new ArrayList<>();
        while (cursor.moveToNext()) {
            Run new_run = new Run();
            // Set the text of the TextViews based on the cursor
            new_run.a_id = cursor.getInt(cursor.getColumnIndex("aid"));
            new_run.u_id = cursor.getInt(cursor.getColumnIndex("uid"));
            new_run.average_heart_rate = cursor.getInt(cursor.getColumnIndex("average_heart_rate"));
            new_run.date = cursor.getString(cursor.getColumnIndex("date_of_activity"));
            String string_time = cursor.getString(cursor.getColumnIndex("total_time"));
            String string_distance = cursor.getString(cursor.getColumnIndex("total_distance_miles"));

            double time = parseTime(string_time);
            double distance = parseDouble(string_distance);
            new_run.total_time = time;
            new_run.total_distance = distance;

            double pace_num = time / distance;

            // Round the result to two decimal places
            String str_pace = String.format("%.2f", pace_num);
            double pace = parseDouble(str_pace);

            int minutes = (int) pace;
            int seconds = (int) ((pace - minutes) * 60);
            new_run.pace = parseDouble(String.format("%d.%02d", minutes, seconds));
            // Add the row to the LinearLayout
            runs.add(new_run);
        }
        cursor.close();
        if(runs.size() == 0) {
            TextView msg = findViewById(R.id.recent_activities);
            msg.setText("No recent activities.");
            recommend.setText("Import Activities");
            recommend.setOnClickListener(view -> importData() );
        }

        RunActivityViewAdapter adapter = new RunActivityViewAdapter(this, runs);
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    public void logout() {
        finish();
        Intent PersonalInfoPage = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(PersonalInfoPage);
    }
    public void recommend() {
        finish();
        Intent PersonalInfoPage = new Intent(WelcomeActivity.this, RecommendationActivity.class);
        PersonalInfoPage.putExtra("name", name);
        PersonalInfoPage.putExtra("uid", uid);
        startActivity(PersonalInfoPage);
    }

    public void importData() {
        finish();
        // Parse fit files and add to database
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "path/to/convert.py");
            Process p = pb.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Now that the python file has been run and data should be in the database, refresh the page
        Intent WelcomeActivity = new Intent(WelcomeActivity.this, WelcomeActivity.class);
        WelcomeActivity.putExtra("name", name);
        WelcomeActivity.putExtra("uid", uid);
        finish();
        startActivity(WelcomeActivity);
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
}