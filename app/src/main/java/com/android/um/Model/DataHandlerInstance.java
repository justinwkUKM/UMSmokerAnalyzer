package com.android.um.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.AnsweredQuestion;
import com.android.um.Model.DataModels.KliniksModel;
import com.android.um.Model.DataModels.LeaderBoardModel;
import com.android.um.Model.DataModels.MotivationMessageModel;
import com.android.um.Model.DataModels.PersonalityData;
import com.android.um.Model.DataModels.PersonalityModel;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.DataModels.SmokeDiaryModel;
import com.android.um.Model.DataModels.SmokeFreeTime;
import com.android.um.Model.DataModels.TargetToSaveModel;
import com.android.um.Model.DataModels.User;
import com.android.um.Model.Firebase.FirebaseHandler;
import com.android.um.Model.Firebase.FirebaseInstance;
import com.android.um.Model.SharedPref.SharedPrefsHandler;
import com.android.um.Model.SharedPref.SharedPrefsInstance;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DataHandlerInstance implements DataHandler {

    private static DataHandlerInstance INSTANCE = null;

    private SharedPrefsManager manager;
//    private DBHandler mDBHandler;
    private SharedPrefsHandler mPrefsHandler;
    private FirebaseHandler mFirebaseHandler;

    private Disposable smokeFreeDisposable;
    private DataHandlerInstance(SharedPrefsManager manager) {
        this.manager = manager;
        mFirebaseHandler = FirebaseInstance.getInstance();
        mPrefsHandler=SharedPrefsInstance.getInstance(manager);
    }

    private DataHandlerInstance() {
        mFirebaseHandler = FirebaseInstance.getInstance();
    }



    public static DataHandlerInstance getInstance(SharedPrefsManager manager) {
        if (INSTANCE == null) {
                if (INSTANCE == null) {
                    INSTANCE = new DataHandlerInstance(manager);
                }
        }
        return INSTANCE;
    }

    public static DataHandlerInstance getInstance() {
        if (INSTANCE == null) {
            if (INSTANCE == null) {
                INSTANCE = new DataHandlerInstance();
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
    public void saveUserAnsweredQuestions(String category, ArrayList<AnsweredQuestion> questions, DataCallBack<String,String> callBack) {
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

    @Override
    public void startTimer(final DataCallBack<Long,String> callBack) {

        if (smokeFreeDisposable!=null)
            smokeFreeDisposable.dispose();

            smokeFreeDisposable=Observable.interval(1,TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(
                            new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    callBack.onReponse(aLong);

                                }
                            }
                    ).subscribe();

    }

    @Override
    public void stopTimer() {
        if (smokeFreeDisposable!=null)
            smokeFreeDisposable.dispose();
    }

    public void getFirebaseTime(final DataCallBack<Long,String> callBack)
    {
        FirebaseFunctions.getInstance().getHttpsCallable("getTime")
                .call().addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                callBack.onReponse((long) httpsCallableResult.getData());

            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onError("");
            }
        });
    }


    @Override
    public void getSmokeFreeTime(DataCallBack<SmokeFreeTime, String> callBack) {
        mFirebaseHandler.getSmokeFreeTime(mPrefsHandler.getLoggedUser().getId(),callBack);
    }

    @Override
    public void addSmokeFreeTime(final DataCallBack<String, String> callBack) {
        getFirebaseTime(new DataCallBack<Long, String>() {
            @Override
            public void onReponse(Long result) {
                Date startDate=new Date(result);
                mFirebaseHandler.addSmokeFreeTime(mPrefsHandler.getLoggedUser().getId(),startDate,callBack);
            }
            @Override
            public void onError(String result) {

            }
        });
    }

    @Override
    public void updateSmokeFreeTime(SmokeFreeTime smokeFreeTime) {
        mFirebaseHandler.updateSmokeFreeTime(mPrefsHandler.getLoggedUser().getId(),smokeFreeTime);
    }

    @Override
    public void getMotivationMessages(DataCallBack<ArrayList<MotivationMessageModel>, String> callBack) {
        mFirebaseHandler.getMotivationMessages(mPrefsHandler.getLoggedUser().getId(),callBack);
    }

    @Override
    public void addMotivtationMessages(MotivationMessageModel messageModel,DataCallBack<String,String> callBack) {
        mFirebaseHandler.addMotivtationMessages(messageModel,mPrefsHandler.getLoggedUser().getId(),callBack);
    }

    @Override
    public void saveString(String key, String value) {
        mPrefsHandler.saveString(key,value);
    }

    @Override
    public String getString(String key) {
        return mPrefsHandler.getString(key);
    }

    @Override
    public void deleteString(String key) {
        mPrefsHandler.deleteString(key);
    }

    @Override
    public void getToken() {
        mFirebaseHandler.getToken(mPrefsHandler.getLoggedUser().getId());
    }

    @Override
    public void sendToken(String token) {
        mFirebaseHandler.sendToken(mPrefsHandler.getLoggedUser().getId(),token);
    }

    @Override
    public void getMindfulnessVideos(String lang,DataCallBack<ArrayList<String>,String> callBack) {
        mFirebaseHandler.getMindfulnessVideos(lang,mPrefsHandler.getLoggedUser().getId(),callBack);
    }

    @Override
    public void checkVideoQuestions(int index, DataCallBack<Boolean,String> callBack) {
            mFirebaseHandler.checkVideoQuestions(index,mPrefsHandler.getLoggedUser().getId(),callBack);
    }

    @Override
    public void getPersonality(DataCallBack<ArrayList<PersonalityModel>, String> callBack) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_id",mPrefsHandler.getLoggedUser().getId());
        FirebaseFunctions.getInstance().getHttpsCallable("calculate_EACNO")
                .call(data).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {


                HashMap<String,Object> json=(HashMap)httpsCallableResult.getData();
                if (json.get("error_message")!=null)
                    callBack.onError((String)json.get("error_message"));
                else
                {

                    ArrayList<HashMap<String,String>> maps=(ArrayList<HashMap<String,String>>)json.get("scores");
                    ArrayList<PersonalityModel> personalityModels=new ArrayList<>();
                    for (HashMap<String,String> map: maps)
                    {
                        PersonalityModel personalityModel=new PersonalityModel();
                        for (Map.Entry model:map.entrySet())
                        {

                            if (model.getKey().equals("title"))
                                personalityModel.setTitle((String)model.getValue());
                            if (model.getKey().equals("score"))
                                personalityModel.setScore((int)model.getValue());
                            if (model.getKey().equals("description"))
                                personalityModel.setDescription((String)model.getValue());


                        }
                        personalityModels.add(personalityModel);
                    }

                    if (personalityModels.size()>0)
                        callBack.onReponse(personalityModels);
                    else
                        callBack.onError("Error");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onError("Failed to check please try again");
                    }
                });
    }

    @Override
    public void getKliniks(DataCallBack<ArrayList<KliniksModel>, String> callBack) {
        mFirebaseHandler.getKliniks(mPrefsHandler.getLoggedUser().getId(),callBack);
    }


    @Override
    public void getLeaderBoard(DataCallBack<ArrayList<LeaderBoardModel>, String> callBack) {
        FirebaseFunctions.getInstance().getHttpsCallable("getLeaderBoard")
                .call().addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {


                HashMap<String,Object> json=(HashMap)httpsCallableResult.getData();

                    ArrayList<HashMap<String,String>> maps=(ArrayList<HashMap<String,String>>)json.get("LeaderBoard");
                    ArrayList<LeaderBoardModel> leaderBoardModelArrayList=new ArrayList<>();
                    for (HashMap<String,String> map: maps)
                    {
                        LeaderBoardModel leaderBoardModel=new LeaderBoardModel();
                        for (Map.Entry model:map.entrySet())
                        {

                            if (model.getKey().equals("name"))
                                leaderBoardModel.setName((String)model.getValue());
                            if (model.getKey().equals("SmokeFreeTime"))
                                leaderBoardModel.setHours((int)model.getValue());

                        }
                        leaderBoardModelArrayList.add(leaderBoardModel);
                    }

                    if (leaderBoardModelArrayList.size()>0)
                        callBack.onReponse(leaderBoardModelArrayList);
                    else
                        callBack.onError("SmokeFree warriors not found yet.");
                }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onError("Failed to get LeaderBoard");
                    }
                });
    }
}
