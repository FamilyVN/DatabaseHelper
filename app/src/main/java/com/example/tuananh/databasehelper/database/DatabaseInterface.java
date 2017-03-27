package com.example.tuananh.databasehelper.database;

import com.example.tuananh.databasehelper.enums.TypeSearch;
import com.example.tuananh.databasehelper.model.ItemSearch;

import java.util.List;

/**
 * Created by framgia on 26/02/2017.
 */
public interface DatabaseInterface {
    List<ItemSearch> getItemSearch(TypeSearch typeSearch);
}
