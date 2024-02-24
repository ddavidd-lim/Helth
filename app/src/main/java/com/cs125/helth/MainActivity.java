package com.cs125.helth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.settings) {
            Toast.makeText(this, "You have clicked on settings", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.share) {
            Toast.makeText(this, "You have clicked on share", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.aboutus) {
            Toast.makeText(this, "You have clicked on aboutus", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.logout) {
            Toast.makeText(this, "You have clicked on logout", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}