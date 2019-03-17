package com.android.um.Model.SharedPref;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.User;

public interface SharedPrefsHandler {

    void saveUser(User user, DataCallBack<String,String> callBack);
    boolean checkLogged();
    User getUsername();
    void saveUserName(User user);
}
