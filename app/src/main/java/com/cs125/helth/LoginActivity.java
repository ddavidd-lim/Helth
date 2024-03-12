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
    private EditText new_email;
    private EditText new_password;
    private EditText confirm_password;
    private Button login;

    private Button register;
    private TextView status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        new_email = (EditText) findViewById(R.id.new_email);
        new_password = (EditText) findViewById(R.id.new_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        login = (Button) findViewById(R.id.login_button);
        register = (Button) findViewById(R.id.sign_up_button);


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
        finish();
        Intent PersonalInfoPage = new Intent(LoginActivity.this, WeeksActivity.class);
        startActivity(PersonalInfoPage);
    }

    public void register() {
        finish();
        Intent PersonalInfoPage = new Intent(LoginActivity.this, PersonalInfoActivity.class);
        startActivity(PersonalInfoPage);
    }
}