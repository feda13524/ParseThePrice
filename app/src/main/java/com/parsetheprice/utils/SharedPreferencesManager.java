package com.parsetheprice.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.parsetheprice.utils.Constants.SORT_TYPE_KEY;
import static com.parsetheprice.utils.Constants.USERDATA_NAME;
import static com.parsetheprice.utils.Constants.BALANCE_KEY;

import java.util.SortedMap;

public class SharedPreferencesManager {
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(USERDATA_NAME, Context.MODE_PRIVATE);
    }

    public void saveBalance(long balance) {
        sharedPreferences.edit().putLong(BALANCE_KEY, balance).apply();
    }

    public long loadBalance() { return sharedPreferences.getLong(BALANCE_KEY, 0); }

    public void saveSortType(int sortType) {
        sharedPreferences.edit().putInt(SORT_TYPE_KEY, sortType).apply();
    }

    public int loadSortType() { return sharedPreferences.getInt(SORT_TYPE_KEY, 0); }
}