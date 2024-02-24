package com.cs125.helth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class PersonalInfoActivity extends AppCompatActivity {


    private Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);

        next = (Button) findViewById(R.id.nextButton);
        next.setOnClickListener(view -> next());
    }

    public void next() {
        finish();

        Intent PersonalInfoPage = new Intent(PersonalInfoActivity.this, GoalsActivity.class);
        startActivity(PersonalInfoPage);
    }
}