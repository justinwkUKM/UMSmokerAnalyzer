package com.android.um.Model.SharedPref;

import android.content.SharedPreferences;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.User;
import com.android.um.Model.SharedPrefsManager;

public class SharedPrefsInstance implements SharedPrefsHandler{

    private static SharedPrefsInstance INSTANCE = null;
    SharedPrefsManager mPrefs;
    private SharedPrefsInstance(SharedPrefsManager manager) {
      mPrefs=manager;
    }

    public static SharedPrefsInstance getInstance(SharedPrefsManager manager) {
        if (INSTANCE == null) {
            if (INSTANCE == null) {
                INSTANCE = new SharedPrefsInstance(manager);
            }
        }
        return INSTANCE;
    }

    @Override
    public void saveUser(User user, DataCallBack<String,String> callBack) {
        SharedPreferences.Editor editor=mPrefs.getSharedPrefs().edit();
        editor.putString("Name",user.getUsername());
        callBack.onReponse(user.getUsername());
    }

    @Override
    public boolean checkLogged() {
        String value=mPrefs.getSharedPrefs().getString("Name","");
        if (value!=null && value.length()>0)
            return true;
        return false;
    }
}
