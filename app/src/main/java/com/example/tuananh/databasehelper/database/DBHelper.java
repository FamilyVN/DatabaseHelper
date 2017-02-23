package com.example.tuananh.databasehelper.database;

import android.content.Context;

import com.tuananh.databasehelper.DatabaseHelper;

/**
 * Created by Tuan Anh on 2/4/2017.
 */
public class DBHelper extends DatabaseHelper {
    private static final String DATABASE_NAME = "test.sqlite3";
    private static final int DATABASE_VERSION = 1;
    private static DBHelper sInstance;

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }
}
