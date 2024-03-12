package com.cs125.helth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PersonalInfoActivity extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText age;
    private EditText height_ft;
    private EditText height_in;
    private EditText dob;
    private EditText home;
    private EditText work;
    private Button back_button;

    private Button next_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalinfo);
        Spinner spinner = findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_options_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        age = (EditText) findViewById(R.id.age);
        height_ft = (EditText) findViewById(R.id.height_ft);
        height_in = (EditText) findViewById(R.id.height_in);
//        dob = (EditText) findViewById();   DOB MISSING
        // GENDER NEEDS TO BE EXTRACTED FROM SPINNER
        home = (EditText) findViewById(R.id.home);
        work = (EditText) findViewById(R.id.work);
        back_button = (Button) findViewById(R.id.back_button);
        next_button = (Button) findViewById(R.id.next_button);

        back_button.setOnClickListener(view -> back());
        next_button.setOnClickListener(view -> next());
    }


    public void next() {
        finish();
        Intent GoalsPage = new Intent(PersonalInfoActivity.this, GoalsActivity.class);
        startActivity(GoalsPage);
    }

    public void back() {
        finish();
        Intent LoginPage = new Intent(PersonalInfoActivity.this, LoginActivity.class);
        startActivity(LoginPage);
    }
}