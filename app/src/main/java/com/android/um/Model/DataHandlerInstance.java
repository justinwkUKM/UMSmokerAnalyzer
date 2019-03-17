package com.android.um.Model;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.DataModels.User;
import com.android.um.Model.Firebase.FirebaseHandler;
import com.android.um.Model.Firebase.FirebaseInstance;
import com.android.um.Model.SharedPref.SharedPrefsHandler;
import com.android.um.Model.SharedPref.SharedPrefsInstance;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;

public class DataHandlerInstance implements DataHandler {

    private static DataHandlerInstance INSTANCE = null;

    private SharedPrefsManager manager;
//    private DBHandler mDBHandler;
    private SharedPrefsHandler mPrefsHandler;
    private FirebaseHandler mFirebaseHandler;

    private DataHandlerInstance(SharedPrefsManager manager) {
        this.manager = manager;
        mFirebaseHandler = FirebaseInstance.getInstance();
        mPrefsHandler=SharedPrefsInstance.getInstance(manager);
    }


    public static DataHandlerInstance getInstance(SharedPrefsManager manager) {
        if (INSTANCE == null) {
                if (INSTANCE == null) {
                    INSTANCE = new DataHandlerInstance(manager);
                }
        }
        return INSTANCE;
    }

    @Override
    public void signupUser(User user, DataCallBack<User, String> callback) {
        mFirebaseHandler.signUpUser(user,callback);
        mPrefsHandler.saveUserName(user);
    }

    @Override
    public void signInUser(User user, DataCallBack<User,String> callback) {
        mFirebaseHandler.signInUser(user,callback);
    }

    @Override
    public void resetPassword(String email, DataCallBack<String,String> callBack) {
        mFirebaseHandler.resetPassword(email,callBack);
    }
    @Override
    public void signinWithGoogle(GoogleSignInAccount account, DataCallBack<User,String> callBack) {
        mFirebaseHandler.signinWithGoogle(account,callBack);
    }

    @Override
    public void signinWithFacebook(CallbackManager callbackManager,DataCallBack<User, FacebookException> response) {
        mFirebaseHandler.signinWithFacebook(callbackManager,response);
    }

    @Override
    public void saveUser(User user, DataCallBack<String, String> callBack) {
        mPrefsHandler.saveUser(user,callBack);
    }

    @Override
    public boolean checkLogged() {
        if (mFirebaseHandler.checkLogged())
        return true;
        else
        {
            if (mPrefsHandler.checkLogged())
                return true;
        }
        return false;
    }

    @Override
    public void getQuestions(String catgeory, DataCallBack<ArrayList<Question>, String> callBack) {
        mFirebaseHandler.getQuestions(catgeory,callBack);
    }

    @Override
    public User getLoggedUser() {
        if (mFirebaseHandler.getLoggedUser()!=null && mFirebaseHandler.getLoggedUser().getUsername()!=null)
            return mFirebaseHandler.getLoggedUser();
        else
            return mPrefsHandler.getUsername();
    }

    @Override
    public void LogOut() {
        mFirebaseHandler.LogOut();
    }
}