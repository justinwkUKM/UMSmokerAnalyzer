package com.android.um.Model.SharedPref;

import android.content.SharedPreferences;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.TargetToSaveModel;
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
    Gson gson = new Gson();
    String json = gson.toJson(null);
    editor.putString("user", json);
    editor.commit();
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
    if (mPrefs.getSharedPrefs().getString(part, "")!= part)
      return true;
    return false;
  }

  @Override
  public void setQuestionsAnswered(String part) {
    SharedPreferences.Editor editor=mPrefs.getSharedPrefs().edit();
    editor.putString(part,"true");
    editor.commit();
  }

  @Override
  public void saveLanguage(String language) {
    SharedPreferences.Editor editor=mPrefs.getSharedPrefs().edit();
    editor.putString("LANG",language);
    editor.commit();
  }

  @Override
  public String getLanguage()
  {
    return mPrefs.getSharedPrefs().getString("LANG", "");
  }

  @Override
  public void saveTermsAcceptence(boolean agree) {
    SharedPreferences.Editor editor=mPrefs.getSharedPrefs().edit();
    editor.putBoolean("TERMS",agree);
    editor.commit();
  }

  @Override
  public boolean getTermsAcceptence() {
    return mPrefs.getSharedPrefs().getBoolean("TERMS", false);
  }

  @Override
  public void saveTargetToSave(Double target) {
    SharedPreferences.Editor editor=mPrefs.getSharedPrefs().edit();
    editor.putString("TARGET_TO_SAVE",""+target);
    editor.commit();
  }

  @Override
  public void saveString(String key,String value) {
    SharedPreferences.Editor editor=mPrefs.getSharedPrefs().edit();
    editor.putString(key,value);
    editor.commit();
  }

  @Override
  public String getString(String key) {
    return mPrefs.getSharedPrefs().getString(key, "");
  }

  @Override
  public String getTargetToSaveLocaly() {
    return mPrefs.getSharedPrefs().getString("TARGET_TO_SAVE", "0.0");
  }

  @Override
  public void deleteString(String key) {
    SharedPreferences.Editor editor=mPrefs.getSharedPrefs().edit();
    editor.putString(key,"");
    editor.commit();
  }
}
