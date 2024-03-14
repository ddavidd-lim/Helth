package com.cs125.helth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button logout = (Button) findViewById(R.id.logout_button);
        logout.setOnClickListener(view -> logout());
    }

    public void logout() {
        finish();
        Intent PersonalInfoPage = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(PersonalInfoPage);
    }
}