package com.cs125.helth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class GoalsActivity extends AppCompatActivity {

    private EditText current_sleep;
    private EditText target_sleep;
    private EditText current_weight;
    private EditText target_weight;
    private Button back_button;
    private Button next_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        current_sleep = (EditText) findViewById(R.id.current_sleep);
        target_sleep = (EditText) findViewById(R.id.target_sleep);
        current_weight = (EditText) findViewById(R.id.current_weight);
        target_weight = (EditText) findViewById(R.id.target_weight);

        back_button = (Button) findViewById(R.id.back_button);
        next_button = (Button) findViewById(R.id.next_button);

        back_button.setOnClickListener(view -> back());
        next_button.setOnClickListener(view -> next());
    }

    public void next() {
        finish();
        Intent WeeksPage = new Intent(GoalsActivity.this, WeeksActivity.class);
        startActivity(WeeksPage);
    }

    public void back() {
        finish();
        Intent PersonalInfoPage = new Intent(GoalsActivity.this, PersonalInfoActivity.class);
        startActivity(PersonalInfoPage);
    }
}