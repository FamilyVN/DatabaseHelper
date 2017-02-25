package com.example.tuananh.databasehelper.database;

import com.example.tuananh.databasehelper.enums.TypeSearch;

import java.util.List;
import java.util.Map;

/**
 * Created by framgia on 26/02/2017.
 */
public interface DatabaseInterface {
    List<Map> getItemSearch(TypeSearch typeSearch);
}
