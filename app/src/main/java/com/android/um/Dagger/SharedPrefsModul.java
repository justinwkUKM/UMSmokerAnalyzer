package com.android.um.Dagger;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPrefsModul {

    Context mContext;
    public SharedPrefsModul(Context context)
    {
        this.mContext=context;
    }
    @Provides
    SharedPreferences provideSharedPreferences()
    {
        return mContext.getSharedPreferences("TEST", Context.MODE_PRIVATE);
    }
}
