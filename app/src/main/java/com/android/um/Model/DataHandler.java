package com.android.um.Model;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.AnsweredQuestion;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.DataModels.SmokeDiaryModel;
import com.android.um.Model.DataModels.SmokeFreeTime;
import com.android.um.Model.DataModels.TargetToSaveModel;
import com.android.um.Model.DataModels.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;

public interface DataHandler {

    void signupUser(User user, DataCallBack<User,String> callback);
    void signInUser(User user, DataCallBack<User,String> callback);
    void resetPassword(String email,DataCallBack<String,String> callBack);
    void signinWithGoogle(GoogleSignInAccount account, DataCallBack<User,String> callBack);
    void signinWithFacebook(CallbackManager callbackManager,DataCallBack<User, FacebookException> response);
    void saveUser(User user,DataCallBack<String, String> callBack);
    void getQuestions(String catgeory,DataCallBack<ArrayList<Question>,String> callBack);
    void saveUserInFirebase(User user,DataCallBack<User,String> callBack);
    boolean checkLogged();
    void LogOut();
    User getLoggedUser();
    void setLogged();
    void saveUserSharedPref(User user);
    void isQuestionsDone(String category,DataCallBack<Boolean,Boolean> callBack);
    void setQuestionsAnswered(String part);
    void saveUserAnsweredQuestions(String category,ArrayList<AnsweredQuestion> questions, DataCallBack<String,String> callBack);
    void saveLanguage(String language);
    String getLanguage();
    void saveTermsAcceptence(boolean agree);
    boolean getTermsAcceptence();
    void saveTargetToSave(TargetToSaveModel target,final DataCallBack<Double,String> callBack);
    void saveTargetToSaveLocally(Double total);
    String getTargetToSaveLocaly();
    void getTargetToSaveOnline(DataCallBack<Double,String> callBack);
    void addSmokeDiary(String smoked, int cravings, double severity, DataCallBack<String,String> callBack);
    void getSmokeDiarys(DataCallBack<ArrayList<SmokeDiaryModel>,String> smokeDiaryList);
    void startTimer(DataCallBack<Long,String> callBack);
    void stopTimer();
    void addSmokeFreeTime(DataCallBack<String,String> callBack);
    void getSmokeFreeTime(DataCallBack<SmokeFreeTime,String> callBack);
    void getFirebaseTime(DataCallBack<Long,String> callBack);
}
