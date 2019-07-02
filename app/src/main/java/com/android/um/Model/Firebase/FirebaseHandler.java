package com.android.um.Model.Firebase;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.AnsweredQuestion;
import com.android.um.Model.DataModels.KliniksModel;
import com.android.um.Model.DataModels.MotivationMessageModel;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.DataModels.SmokeDiaryModel;
import com.android.um.Model.DataModels.SmokeFreeTime;
import com.android.um.Model.DataModels.TargetToSaveModel;
import com.android.um.Model.DataModels.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.Date;

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
    void saveUserAnsweredQuestions(String category, String userId, ArrayList<AnsweredQuestion> questions, DataCallBack<String,String> callBack);
     void isQuestionsDone(String category,String userId,DataCallBack<Boolean,Boolean> callBack);

    void saveTargetToSave(TargetToSaveModel target,String userId,final DataCallBack<Double,String> callBack);
    void getTargetToSave(String userId,DataCallBack<Double,String> callBack);
    void addSmokeDiary(String userId,String smoked, int cravings, double severity,final DataCallBack<String,String> callBack);
    void getSmokeDiarys(String userId,DataCallBack<ArrayList<SmokeDiaryModel>,String> callBack);
    void getSmokeFreeTime(String userId,DataCallBack<SmokeFreeTime,String> callBack);
    void addSmokeFreeTime(String userId,Date startDate,DataCallBack<String, String> callBack);
    void updateSmokeFreeTime(String userId,SmokeFreeTime smokeFreeTime);
    void getMotivationMessages(String userId, DataCallBack<ArrayList<MotivationMessageModel>,String> callBack);
    void addMotivtationMessages(MotivationMessageModel messageModel,String userId, DataCallBack<String,String> callBack);
    void getToken(String userId);
    void sendToken(String userId,String token);
    void getMindfulnessVideos(String lang,String userId,DataCallBack<ArrayList<String>,String> callBack);
    void checkVideoQuestions(int index,String userId,DataCallBack<Boolean,String> callBack);
    void getKliniks(String userId, DataCallBack<ArrayList<KliniksModel>,String> callBack);
}
