package com.android.um.Model;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.AnsweredQuestion;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.DataModels.SmokeDiaryModel;
import com.android.um.Model.DataModels.TargetToSaveModel;
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
       return mPrefsHandler.getLoggedUser();
    }

    @Override
    public void saveUserInFirebase(User user, DataCallBack<User, String> callBack) {
        mFirebaseHandler.saveUserInFirebase(user,callBack);
    }

    @Override
    public void setLogged() {
        mPrefsHandler.setLogged();
    }

    @Override
    public void saveUserSharedPref(User user) {
        mPrefsHandler.saveUserSharedPref(user);
    }

    @Override
    public void LogOut() {
        mPrefsHandler.LogOut();
        mFirebaseHandler.LogOut();
    }

    @Override
    public void isQuestionsDone(String category,DataCallBack<Boolean,Boolean> callBack) {
        mFirebaseHandler.isQuestionsDone(category,mPrefsHandler.getLoggedUser().getId(),callBack);
    }


    @Override
    public void setQuestionsAnswered(String part) {
        mPrefsHandler.setQuestionsAnswered(part);

    }

    @Override
    public void saveUserAnsweredQuestions(String category,ArrayList<AnsweredQuestion> questions, DataCallBack<String,String> callBack) {
        mFirebaseHandler.saveUserAnsweredQuestions(category,mPrefsHandler.getLoggedUser().getId(),questions,callBack);
    }

    @Override
    public void saveLanguage(String language) {
        mPrefsHandler.saveLanguage(language);
    }

    @Override
    public String getLanguage() {
        return mPrefsHandler.getLanguage();
    }

    @Override
    public void saveTermsAcceptence(boolean agree) {
        mPrefsHandler.saveTermsAcceptence(agree);
    }

    @Override
    public boolean getTermsAcceptence() {
        return mPrefsHandler.getTermsAcceptence();
    }

    @Override
    public void saveTargetToSave(TargetToSaveModel target,final DataCallBack<Double,String> callBack) {
        mFirebaseHandler.saveTargetToSave(target,mPrefsHandler.getLoggedUser().getId(),callBack);
    }

    @Override
    public void addSmokeDiary(String smoked, int cravings, double severity,final DataCallBack<String,String> callBack) {
        mFirebaseHandler.addSmokeDiary(mPrefsHandler.getLoggedUser().getId(),smoked,cravings,severity,callBack);
    }

    @Override
    public void getSmokeDiarys(DataCallBack<ArrayList<SmokeDiaryModel>, String> callBack) {
        mFirebaseHandler.getSmokeDiarys(mPrefsHandler.getLoggedUser().getId(),callBack);
    }

    @Override
    public void saveTargetToSaveLocally(Double total) {
        mPrefsHandler.saveTargetToSave(total);
    }

    @Override
    public String getTargetToSaveLocaly() {
            return mPrefsHandler.getTargetToSaveLocaly();
    }

    @Override
    public void getTargetToSaveOnline(DataCallBack<Double,String> callBack) {
        mFirebaseHandler.getTargetToSave(mPrefsHandler.getLoggedUser().getId(),callBack);    }
}
