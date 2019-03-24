package com.android.um.Model.SharedPref;

import android.content.SharedPreferences;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.User;
import com.android.um.Model.SharedPrefsManager;
import com.google.gson.Gson;

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
    public void saveUserSharedPref(User user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        SharedPreferences.Editor editor=mPrefs.getSharedPrefs().edit();
        editor.putString("user", json);
        editor.commit();
    }

    @Override
    public void saveUser(User user, DataCallBack<String,String> callBack) {
        SharedPreferences.Editor editor=mPrefs.getSharedPrefs().edit();
        editor.putString("Name",user.getUsername());
        callBack.onReponse(user.getUsername());
    }

    @Override
    public boolean checkLogged() {
        String value=mPrefs.getSharedPrefs().getString("LOGGED","");
        if (value!=null && value.length()>0 && value.equals("true"))
            return true;
        return false;
    }

//    @Override
//    public User getUsername() {
//        User user=new User();
//        String value=mPrefs.getSharedPrefs().getString("Name","");
//        if (value!=null && value.length()>0)
//        {
//            user.setUsername(value);
//            return user;
//        }
//            user.setUsername("");
//        return user;
//
//    }


    @Override
    public User getLoggedUser() {
        Gson gson = new Gson();
        User user;
        if (mPrefs.getSharedPrefs().getString("user", "")!=null) {
            String json = mPrefs.getSharedPrefs().getString("user", "");
            user = gson.fromJson(json, User.class);
            return user;
        }
        user=new User();
        user.setUsername(" ");
        return user;
    }

    @Override
    public void LogOut() {
        SharedPreferences.Editor editor=mPrefs.getSharedPrefs().edit();
        editor.putString("LOGGED","false");
        editor.commit();
    }

    @Override
    public void setLogged() {
        SharedPreferences.Editor editor=mPrefs.getSharedPrefs().edit();
        editor.putString("LOGGED","true");
        editor.commit();
    }

    @Override
    public boolean isQuestionsDone(String part) {
        if (mPrefs.getSharedPrefs().getString("Questions"+part, "")!="Questions"+part)
            return true;
        return false;
    }

    @Override
    public void setQuestionsAnswered(String part) {
        SharedPreferences.Editor editor=mPrefs.getSharedPrefs().edit();
        editor.putString("Questions"+part,"true");
        editor.commit();
    }
}
