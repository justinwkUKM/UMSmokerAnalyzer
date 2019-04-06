package com.android.um.Model.SharedPref;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.TargetToSaveModel;
import com.android.um.Model.DataModels.User;

public interface SharedPrefsHandler {

    void saveUser(User user, DataCallBack<String,String> callBack);
    boolean checkLogged();
    User getLoggedUser();
    void saveUserSharedPref(User user);
    void setLogged();
    void LogOut();
    boolean isQuestionsDone(String part);
    void setQuestionsAnswered(String part);
    void saveLanguage(String language);
    String getLanguage();
    void saveTermsAcceptence(boolean agree);
    boolean getTermsAcceptence();
    void saveTargetToSave(Double total);
    String getTargetToSaveLocaly();
}
