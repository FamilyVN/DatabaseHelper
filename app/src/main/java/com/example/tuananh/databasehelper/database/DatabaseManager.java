package com.example.tuananh.databasehelper.database;

import android.content.Context;

import com.tuananh.databasehelper.queryhelper.QueryHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by tuananh on 23/02/2017.
 */

public class DatabaseManager implements DatabaseInterface {
    private static DatabaseManager sInstance;
    private Context mContext;

    private DatabaseManager(Context context) {
        mContext = context;
    }

    public static synchronized DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public List<Map> getItemSearch(String tableName) {
        QueryHelper queryHelper = new QueryHelper();
        queryHelper.setJoinTable(tableName);
        return DBHelper.getInstance(mContext).getMapList(queryHelper);
    }
}
