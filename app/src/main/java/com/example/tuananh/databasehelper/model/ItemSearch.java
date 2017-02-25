package com.example.tuananh.databasehelper.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by framgia on 26/02/2017.
 */
public class ItemSearch {
    @SerializedName("id")
    private int mId;
    @SerializedName("enValue")
    private String mEnValue;
    @SerializedName("jaValue")
    private String mJaValue;

    public int getId() {
        return mId;
    }

    public String getEnValue() {
        return mEnValue;
    }

    public String getJaValue() {
        return mJaValue;
    }
}