package com.tuananh.databasehelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.tuananh.databasehelper.queryhelper.QueryHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.database.Cursor.FIELD_TYPE_INTEGER;
import static android.database.Cursor.FIELD_TYPE_STRING;

/**
 * Created by Tuan Anh on 2/4/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String FORMAT_THREE_STRING = "%s%s%s";
    private static final String FORMAT_TWO_STRING = "%s%s";
    private static final String DATABASE = "/databases/";
    private String mDatabasePath;
    private Context mContext;
    private String mNameDatabase;

    public DatabaseHelper(Context context, String nameDatabase,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, nameDatabase, factory, version);
        mNameDatabase = nameDatabase;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mDatabasePath =
                String.format(FORMAT_TWO_STRING, context.getApplicationInfo().dataDir, DATABASE);
        } else {
            mDatabasePath = String.format(FORMAT_THREE_STRING, context.getFilesDir().getPath(),
                context.getPackageName(), DATABASE);
        }
        mContext = context;
        try {
            createDatabase();
        } catch (IOException mIOException) {
            throw new Error("UnableToCreateDatabase");
        }
    }

    private void createDatabase() throws IOException {
        if (!isDatabaseExisted()) {
            getReadableDatabase();
            copyDatabase();
            close();
        }
    }

    private boolean isDatabaseExisted() {
        File dbFile =
            new File(String.format(FORMAT_TWO_STRING, mDatabasePath, mNameDatabase));
        return dbFile.exists();
    }

    private void copyDatabase() throws IOException {
        InputStream input = mContext.getAssets().open(mNameDatabase);
        String outFileName =
            String.format(FORMAT_TWO_STRING, mDatabasePath, mNameDatabase);
        OutputStream output = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        input.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public List<Map> getMapList(QueryHelper queryHelper) {
        List<Map> mapList = new ArrayList<>();
        Cursor cursor = query(queryHelper);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Map<String, Object> map = new HashMap<>();
                for (int columnIndex = 0; columnIndex < cursor.getColumnCount(); columnIndex++) {
                    map.put(cursor.getColumnName(columnIndex),
                        changeDataToString(cursor, columnIndex));
                }
                mapList.add(map);
            }
            while (cursor.moveToNext());
        }
        return mapList;
    }

    public Object changeDataToString(Cursor cursor, int columnIndex) {
        Object values = null;
        switch (cursor.getType(columnIndex)) {
            case FIELD_TYPE_INTEGER:
                values = cursor.getInt(columnIndex);
                break;
            case FIELD_TYPE_STRING:
                values = cursor.getString(columnIndex);
                break;
            default:
                break;
        }
        return values;
    }

    public Cursor query(QueryHelper queryHelper) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(queryHelper.getSqlQuery(), queryHelper.getSelectionArgs());
    }
}
