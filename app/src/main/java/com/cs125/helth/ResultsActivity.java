package com.cs125.helth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Button back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(view -> back());

        Button home = (Button) findViewById(R.id.home_button);
        home.setOnClickListener(view -> home());
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
}