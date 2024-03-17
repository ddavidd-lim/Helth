package com.cs125.helth;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DB_NAME = "mydatabase.db"; // Name of your database file in assets
    private static final int DB_VERSION = 1;

    private final Context context;
    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        copyDatabaseFromAssets();
    }

    private void copyDatabaseFromAssets() {
        File dbFile = context.getDatabasePath(DB_NAME);

        if (dbFile.exists()) {
            Log.d(TAG, "Database already exists. No need to copy from assets.");
            return;
        }

        try {
            Log.d(TAG, "Copying database from assets...");
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(DB_NAME);
            OutputStream outputStream = new FileOutputStream(dbFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            Log.d(TAG, "Database copied successfully.");
        } catch (IOException e) {
            Log.e(TAG, "Error copying database", e);
        }
    }



    public void openDatabase() {
        db = getReadableDatabase();
    }

    public void closeDatabase() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public Cursor query(String query, String[] selectionArgs) {
        Log.d("DatabaseHelper", "Database path: " + db.getPath());
        return db.rawQuery(query, selectionArgs);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Not needed for this example
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not needed for this example
    }

    public long insertUser(String newName, String newEmail, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", newName);
        contentValues.put("email", newEmail);
        contentValues.put("password", newPassword);

        long uid = db.insert("users", null, contentValues);

        return uid;
    }
}