package com.test.yatfirstexercise;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by NH on 06-Jan-18.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "usersInfo";
    private static final String USERS_TABLE = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("oncreate", "oncreate");
        String CREATE_USERS_TABLE = "CREATE TABLE " + USERS_TABLE + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, " + KEY_EMAIL + " TEXT, " + KEY_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("onupgrade", "onupgrade");
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        onCreate(db);
    }

    public void addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);

        db.insert(USERS_TABLE, null, values);
        db.close();
    }


    public boolean credentialChecker(String email, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor =
                database.rawQuery("select * from "
                        + USERS_TABLE + " where " + KEY_EMAIL +
                        " =? and " + KEY_PASSWORD + " =?" , new String[]{email, password});
        return cursor.getCount() > 0;

    }

}
