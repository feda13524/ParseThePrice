package com.parsetheprice.utils;

import android.content.Context;
import android.content.SharedPreferences;
import static com.parsetheprice.utils.Constants.KEY_BALANCE;
import static com.parsetheprice.utils.Constants.USERDATA_NAME;

public class SharedPreferencesManager {
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(USERDATA_NAME, Context.MODE_PRIVATE);
    }

    public void saveBalance(long balance) {
        sharedPreferences.edit().putLong(KEY_BALANCE, balance).apply();
    }

    public long loadBalance() {
        return sharedPreferences.getLong(KEY_BALANCE, 0);
    }
}