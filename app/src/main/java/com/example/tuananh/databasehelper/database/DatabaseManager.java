package com.example.tuananh.databasehelper.database;

import android.content.Context;
import android.database.Cursor;

import com.example.tuananh.databasehelper.enums.TypeSearch;
import com.example.tuananh.databasehelper.model.ItemSearch;
import com.tuananh.databasehelper.queryhelper.QueryHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.tuananh.databasehelper.database.DBConstant.FIELD_EN_VALUE;
import static com.example.tuananh.databasehelper.database.DBConstant.FIELD_ID;
import static com.example.tuananh.databasehelper.database.DBConstant.FIELD_JA_VALUE;
import static com.example.tuananh.databasehelper.database.DBConstant.FIELD_KEY;
import static com.example.tuananh.databasehelper.database.DBConstant.TABLE_DEGREE;
import static com.example.tuananh.databasehelper.database.DBConstant.TABLE_M_DEGREE;

/**
 * Created by framgia on 26/02/2017.
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
    public List<ItemSearch> getItemSearch(TypeSearch typeSearch) {
        QueryHelper queryHelper = new QueryHelper();
        String masterTableName, valueTableName, columnMasterTableName, columnValueTableName;
        switch (typeSearch) {
            case DEGREES:
                masterTableName = TABLE_M_DEGREE;
                valueTableName = TABLE_DEGREE;
                break;
            default:
                return null;
        }
        columnMasterTableName = masterTableName + "." + FIELD_KEY;
        columnValueTableName = valueTableName + "." + FIELD_KEY;
        queryHelper.addColumn(masterTableName + "." + FIELD_ID);
        queryHelper.addColumn(valueTableName + "." + FIELD_EN_VALUE);
        queryHelper.addColumn(valueTableName + "." + FIELD_JA_VALUE);
        queryHelper.setJoinTable(masterTableName, valueTableName,
            columnMasterTableName,
            columnValueTableName);
        return getItemSearchList(queryHelper);
    }

    public List<ItemSearch> getItemSearchList(QueryHelper queryHelper) {
        List<ItemSearch> itemSearchList = new ArrayList<>();
        Cursor cursor = DBHelper.getInstance(mContext).query(queryHelper);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    int id = cursor.getInt(cursor.getColumnIndex(FIELD_ID));
                    String enValues = cursor.getString(cursor.getColumnIndex(FIELD_EN_VALUE));
                    String jaValues = cursor.getString(cursor.getColumnIndex(FIELD_JA_VALUE));
                    ItemSearch itemSearch = new ItemSearch(id, enValues, jaValues);
                    itemSearchList.add(itemSearch);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            while (cursor.moveToNext());
        }
        return itemSearchList;
    }
}
