package com.cs125.helth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;

    private Button register;
    private TextView status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        status = (TextView) findViewById(R.id.textView2);

        login.setOnClickListener(view -> login());
        register.setOnClickListener(view -> register());

//        Old listener
//        login.setOnClickListener(new View.OnClickListener(){
//            @Override
//                    public void onClick(View v){
//                String confirmation = "Login Valid";
//                status.setText(confirmation);
//            }
//        });
    }

    public void login() {
        String confirmation = "Login Valid";
        status.setText(confirmation);
    }

    public void register() {
        String confirmation = "Registering";
        status.setText(confirmation);
        finish();

        Intent PersonalInfoPage = new Intent(LoginActivity.this, PersonalInfoActivity.class);
        startActivity(PersonalInfoPage);
    }
}