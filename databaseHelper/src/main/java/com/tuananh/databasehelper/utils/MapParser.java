package com.tuananh.databasehelper.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * Created by Tuan Anh on 2/2/2017.
 */
public class MapParser {
    public static <Object> Object toObject(java.lang.Object map, Class<Object> classOfT) {
        JsonElement jsonElement = new Gson().toJsonTree(map);
        return new Gson().fromJson(jsonElement, classOfT);
    }
}
