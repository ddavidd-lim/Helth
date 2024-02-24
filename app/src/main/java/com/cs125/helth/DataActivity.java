package com.cs125.helth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class DataActivity extends AppCompatActivity {

    private Switch sleepConsent;
    private Switch exerciseConsent;
    private Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);


        sleepConsent = (Switch) findViewById(R.id.switchSleep);
        exerciseConsent = (Switch) findViewById(R.id.switchExercise);

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(view -> next());
    }

    // TODO - set up next page
    public void next() {
        finish();

//        Intent PersonalInfoPage = new Intent(DataActivity.this, GoalsActivity.class);
//        startActivity(PersonalInfoPage);
    }
}
