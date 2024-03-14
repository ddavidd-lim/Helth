package com.cs125.helth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RecommendationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        Button back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(view -> calculate());

        Button calculate = (Button) findViewById(R.id.calculate_button);
        calculate.setOnClickListener(view -> calculate());
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
}