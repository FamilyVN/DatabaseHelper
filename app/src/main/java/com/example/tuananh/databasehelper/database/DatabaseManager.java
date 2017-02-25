package com.example.tuananh.databasehelper.database;

import android.content.Context;

import com.example.tuananh.databasehelper.enums.TypeSearch;
import com.tuananh.databasehelper.queryhelper.QueryHelper;

import java.util.List;
import java.util.Map;

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
    public List<Map> getItemSearch(TypeSearch typeSearch) {
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
        return DBHelper.getInstance(mContext).getMapList(queryHelper);
    }
}
