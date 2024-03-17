package com.cs125.helth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText new_name;
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

        new_name = (EditText) findViewById(R.id.name);
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
        String username = email.getText().toString();
        String passwordText = password.getText().toString();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDatabase();
        Cursor cursor = databaseHelper.query("SELECT * FROM users WHERE email=? AND password=?", new String[]{username, passwordText});
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int uid = cursor.getInt(cursor.getColumnIndex("uid"));

            Intent WelcomeActivity = new Intent(LoginActivity.this, WelcomeActivity.class);
            WelcomeActivity.putExtra("name", name);
            WelcomeActivity.putExtra("uid", uid);
            finish();
            startActivity(WelcomeActivity);
        } else {
            Toast.makeText(LoginActivity.this, "Invalid login. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void register() {
        String newName = new_name.getText().toString();
        String newEmail = new_email.getText().toString();
        String newPassword = new_password.getText().toString();
        String confirmPassword = confirm_password.getText().toString();

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(LoginActivity.this, "Passwords are mismatching.", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDatabase();
        Cursor cursor = databaseHelper.query("SELECT * FROM users WHERE email=?", new String[]{newEmail});
        if (cursor.moveToFirst()) {
            Toast.makeText(LoginActivity.this, "Email is already used.", Toast.LENGTH_SHORT).show();
        } else {
            long uid = databaseHelper.insertUser(newName, newEmail, newPassword);
            if (uid != -1) {
                finish();
                Intent PersonalInfoPage = new Intent(LoginActivity.this, PersonalInfoActivity.class);
                PersonalInfoPage.putExtra("name", newName);
                PersonalInfoPage.putExtra("email", newEmail);
                PersonalInfoPage.putExtra("password", newPassword);
                startActivity(PersonalInfoPage);
            } else {
                Toast.makeText(LoginActivity.this, "Failed to register. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}