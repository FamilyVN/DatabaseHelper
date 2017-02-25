package com.example.tuananh.databasehelper.database;

import java.util.List;
import java.util.Map;

/**
 * Created by tuananh on 23/02/2017.
 */

public interface DatabaseInterface {
    List<Map> getItemSearch(String tableName);
}
