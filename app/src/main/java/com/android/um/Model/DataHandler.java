package com.android.um.Model;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.DataModels.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;

public interface DataHandler {

    void signupUser(String email, String password, DataCallBack<User,String> callback);
    void signInUser(String email, String password, DataCallBack<User,String> callback);
    void resetPassword(String email,DataCallBack<String,String> callBack);
    void signinWithGoogle(GoogleSignInAccount account, DataCallBack<User,String> callBack);
    void signinWithFacebook(CallbackManager callbackManager,DataCallBack<User, FacebookException> response);
    void saveUser(User user,DataCallBack<String, String> callBack);
    void getQuestions(String catgeory,DataCallBack<ArrayList<Question>,String> callBack);
    boolean checkLogged();
}
