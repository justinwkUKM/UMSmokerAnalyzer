package com.android.um.Model.Firebase;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.AnsweredQuestion;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.DataModels.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public interface FirebaseHandler {

    void signUpUser(User user, DataCallBack<User,String> callback);
    void signInUser(User user, DataCallBack<User,String> callback);
    void resetPassword(String email,DataCallBack<String,String> callBack);
    void signinWithGoogle(GoogleSignInAccount account, DataCallBack<User,String> callBack);
    void signinWithFacebook(CallbackManager callbackManager,DataCallBack<User, FacebookException> response);
    void getQuestions(String catgeory,DataCallBack<ArrayList<Question>,String> callBack);
    boolean checkLogged();
    void LogOut();
//    User getLoggedUser();
    void saveUserInFirebase(User user,DataCallBack<User,String> callBack);
    void saveUserAnsweredQuestions(String category,String userId,ArrayList<AnsweredQuestion> questions, DataCallBack<String,String> callBack);
     void isQuestionsDone(String category,String userId,DataCallBack<Boolean,Boolean> callBack);
}
