package com.cs125.helth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        Intent intent = getIntent();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDatabase();
        long uid = databaseHelper.insertUser(intent.getStringExtra("name"), intent.getStringExtra("email"), intent.getStringExtra("password"));
        if (uid != -1) {
            Intent WeeksPage = new Intent(GoalsActivity.this, WelcomeActivity.class);
            finish();
            WeeksPage.putExtra("name", intent.getStringExtra("name"));
            WeeksPage.putExtra("uid", uid);
            startActivity(WeeksPage);
        } else {
            Toast.makeText(GoalsActivity.this, "Failed to register. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void back() {
        finish();
        Intent PersonalInfoPage = new Intent(GoalsActivity.this, PersonalInfoActivity.class);
        Intent intent = getIntent();
        PersonalInfoPage.putExtra("name", intent.getStringExtra("name"));
        PersonalInfoPage.putExtra("email", intent.getStringExtra("email"));
        PersonalInfoPage.putExtra("password", intent.getStringExtra("password"));
        startActivity(PersonalInfoPage);
    }
}