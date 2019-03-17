package com.android.um.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsManager {

    Context mContext;
    public static SharedPrefsManager instance;

    public SharedPrefsManager(Context context)
    {
        this.mContext=context;
    }

    public static SharedPrefsManager getInstance(Context context) {
        if (instance!=null)
            return instance;
        else
            return new SharedPrefsManager(context);
    }

    public SharedPreferences getSharedPrefs()
    {
        return mContext.getSharedPreferences("USERS",Context.MODE_PRIVATE);
    }
}
