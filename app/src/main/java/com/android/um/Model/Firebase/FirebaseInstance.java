package com.android.um.Model.Firebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.AnsweredQuestion;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.DataModels.SmokeDiaryModel;
import com.android.um.Model.DataModels.SmokeFreeTime;
import com.android.um.Model.DataModels.TargetToSaveModel;
import com.android.um.Model.DataModels.User;
import com.android.um.Model.DataModels.options;
import com.android.um.Utils.AppUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class FirebaseInstance implements FirebaseHandler {

    private static FirebaseInstance INSTANCE = null;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference rootRef;
    boolean checkFlag=false;
    private FirebaseInstance() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        rootRef= firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public static FirebaseInstance getInstance() {
        if (INSTANCE == null) {
            if (INSTANCE == null) {
                INSTANCE = new FirebaseInstance();
            }
        }
        return INSTANCE;
    }

    @Override
    public void signUpUser(final User user, final DataCallBack<User, String> callback) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(),user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    user.setFirebaseUser(mAuth.getCurrentUser());
                    signInUser(user,callback);

                }
                else
                    callback.onError(task.getException().getMessage());
            }
        })
        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e.getMessage());
                    }
        });
    }

    @Override
    public void signInUser(final User user, final DataCallBack<User,String> callback) {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                   checkifUserExist(user, new DataCallBack<User, String>() {
                       @Override
                       public void onReponse(User result) {
                           if (result!=null)
                                callback.onReponse(result);
                           else
                               callback.onReponse(user);
                       }

                       @Override
                       public void onError(String result) {
                           callback.onReponse(user);
                       }
                   });
                }
                else
                    callback.onError(task.getException().getMessage());

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

    @Override
    public void resetPassword(String email, final DataCallBack<String,String> callBack) {

    }

    @Override
    public void signinWithGoogle(GoogleSignInAccount account, final DataCallBack<User,String> googleResponse) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                final User user=new User();
                user.setFirebaseUser(mAuth.getCurrentUser());
                checkifUserExist( user, new DataCallBack<User, String>() {
                    @Override
                    public void onReponse(User result) {
                        if (result!=null)
                            googleResponse.onReponse(result);
                        else
                        {
                            saveUserInFirebase(user, new DataCallBack<User, String>() {
                                @Override
                                public void onReponse(User result) {

                                    googleResponse.onReponse(user);
                                }

                                @Override
                                public void onError(String result) {
                                    googleResponse.onError("Something went wrong,Please try again!");
                                }
                            });
                        }

                    }

                    @Override
                    public void onError(String result) {
                        googleResponse.onError("Failed,Please try again!");
                    }
                });

            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                googleResponse.onError(e.getMessage());
            }
        });

    }

    @Override
    public void signinWithFacebook( CallbackManager callbackManager,final DataCallBack<User,FacebookException> facebookResponse) {

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        authFaceBook(loginResult.getAccessToken(), new DataCallBack<FirebaseUser, String>() {
                            @Override
                            public void onReponse(final FirebaseUser firebaseUser) {
                                String username=firebaseUser.getDisplayName();
                                GraphRequest request = GraphRequest.newMeRequest(
                                        loginResult.getAccessToken(),
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject object, GraphResponse response) {
                                                final User user=new User();
                                                user.setFirebaseUser(firebaseUser);
                                                try {
                                                    //get facebook user info to save it to our realtime database
                                                    user.setUsername(object.getString("name"));
                                                    user.setEmail(object.getString("email"));
//                                                    String birthday = object.getString("birthday");
//                                                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(birthday);
//                                                    user.setAge(date.getYear());
//                                                    user.setGender(object.getString("gender"));

                                                    checkifUserExist(user, new DataCallBack<User, String>() {
                                                        @Override
                                                        public void onReponse(User result) {
                                                            if (result!=null)
                                                                facebookResponse.onReponse(result);
                                                            else
                                                            {
                                                                saveUserInFirebase(user, new DataCallBack<User, String>() {
                                                                    @Override
                                                                    public void onReponse(User result) {
                                                                        facebookResponse.onReponse(user);
                                                                    }

                                                                    @Override
                                                                    public void onError(String result) {
                                                                        facebookResponse.onError(null);
                                                                    }
                                                                });
                                                            }

                                                        }

                                                        @Override
                                                        public void onError(String result) {
                                                            facebookResponse.onError(null);
                                                        }
                                                    });
                                                }
                                                catch (JSONException exception) {

                                                    facebookResponse.onError(null);
                                                }
//                                                catch (ParseException parse) {
//
//                                                    facebookResponse.onError(null); }
                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id,name,email,gender,birthday");
                                request.setParameters(parameters);
                                request.executeAsync();
                            }

                            @Override
                            public void onError(String result) {
                                FacebookException exception=new FacebookException(result);
                                facebookResponse.onError(exception);

                            }
                        });
                    }

                    @Override
                    public void onCancel() {
                        FacebookException exception=new FacebookException();
                        facebookResponse.onError(exception);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        facebookResponse.onError(exception);
                    }
                });

    }

    public void checkifUserExist(final User user,final DataCallBack<User,String> callBack)
    {
        rootRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean flag=false;
                for (DataSnapshot data: dataSnapshot.getChildren())
                {
                    if (data.child("email").getValue()!=null && data.child("email").getValue().equals(user.getEmail()))
                    {
                        flag=true;
                        User user=data.getValue(User.class);
                        user.setId(data.getKey());
                        callBack.onReponse(user);
                    }
                }
                //this flag is set to prevent the call back from calling again
                if (!flag)
                    callBack.onReponse(null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onError("");
            }
        });
    }
    public void authFaceBook(AccessToken accessToken, final DataCallBack<FirebaseUser, String> callBack) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            callBack.onReponse(user);
                        }
                        else
                            callBack.onError(task.getException().getMessage());

                    }
                });
    }

    @Override
    public boolean checkLogged() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null)
            return true;
        else
            return false;
    }

    @Override
    public void getQuestions(String catgeory, final DataCallBack<ArrayList<Question>, String> callBack) {
        String x=catgeory;
        rootRef.child("questions").child(catgeory).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Question> questions=new ArrayList<>();
                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    ArrayList<options> optionsList=new ArrayList<>();
                    Question question = dsp.getValue(Question.class);
                    for (DataSnapshot data:dsp.child("options").getChildren())
                    {
                        options options=data.getValue(com.android.um.Model.DataModels.options.class);
                        optionsList.add(options);
                    }
                    question.setQustionOptions(optionsList);
                    questions.add(question);
                }
                if (questions.size()>0)
                    callBack.onReponse(questions);
                else
                    callBack.onError("No Questions Found,Try Again");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onError(databaseError.getMessage());
            }
        });

    }

    @Override
    public void saveUserInFirebase(final User user, final DataCallBack<User, String> callBack) {


      checkifUserExist(user, new DataCallBack<User, String>() {
          @Override
          public void onReponse(User result) {
              if (result!=null)
              {

                  //in case for google sign in we wont be able to get the age and gender
                  //so user is asked to enter age and gender but then we need to update the firebase user we added after signin with google
                  //here we are comparing the two users the first one is (result) which is the user found in firebase and the second user is
                  // the user with age and gender so will be comparing if there is no gender nor age in the firebase user then we need to update that user
                  User saveUser=compareUser(result,user);
                  final DatabaseReference ref=rootRef.child("users");
                  Map<String,Object> userMap=new HashMap<>();
                  userMap.put(result.getId(),saveUser);
                  ref.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          if (task.isSuccessful())
                          {
                              //if the user already had its key no need to assign a new key
                              if (user.getId().length()==0 )
                                    user.setId(ref.getKey());

                              callBack.onReponse(user);
                          }
                          else
                              callBack.onError("Failed,please try again");

                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          callBack.onError("Failed,please try again");
                      }
                  });

              }
              else
              {
                  final DatabaseReference ref=rootRef.child("users").push();
                  ref.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          if (task.isSuccessful())
                          {
                              user.setId(ref.getKey());
                              callBack.onReponse(user);
                          }
                          else
                              callBack.onError("Failed,please try again");

                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          callBack.onError("Failed,please try again");
                      }
                  });
              }
          }

          @Override
          public void onError(String result) {
                callBack.onError(result);
          }
      });

    }

    public User compareUser(User firebaseUser,User signupUser)
    {
        if (signupUser.getAge()==0 && firebaseUser!=null)
            signupUser.setAge(firebaseUser.getAge());
        else if ((signupUser.getGender()==null || signupUser.getGender().length()==0) && firebaseUser.getGender()!=null)
            signupUser.setGender(firebaseUser.getGender());

        return signupUser;

    }
    @Override
    public void saveUserAnsweredQuestions(String category,String userId,ArrayList<AnsweredQuestion> questions, final DataCallBack<String,String> callBack) {

        DatabaseReference ref=rootRef.child("users").child(userId);
        HashMap<String,Object> answersMap=new HashMap<>();
        answersMap.put(category,questions);
        ref.updateChildren(answersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    callBack.onReponse("");
                else
                    callBack.onError("Failed to save answers,Try again");
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onError("Failed to save answers,Try again");
            }
        });

    }

    @Override
    public void isQuestionsDone(final String category,String userId, final DataCallBack<Boolean, Boolean> callBack) {
        rootRef.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean flag=false;
                for (DataSnapshot data: dataSnapshot.getChildren())
                {
                    if (data.getKey()!=null && data.getKey().equals(category))
                    {

                        callBack.onReponse(true);
                        flag=true;
                        break;
                    }
                }
                if (!flag)
                    callBack.onReponse(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onError(false);
            }
        });
    }


    @Override
    public void saveTargetToSave(final TargetToSaveModel target, String userId, final DataCallBack<Double,String> callBack) {
        DatabaseReference ref=rootRef.child("users").child(userId);
        HashMap<String,Object> answersMap=new HashMap<>();
        answersMap.put("TargetToSave",target);
        ref.updateChildren(answersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    callBack.onReponse(target.getTotal());
                else
                    callBack.onError("Failed to save Target,Try again");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onError("Failed to save Target,Try again");
                    }
                });
    }

    @Override
    public void getTargetToSave(String userId,final DataCallBack<Double,String> callBack) {
        rootRef.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               TargetToSaveModel targetToSaveModel=new TargetToSaveModel();
                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                        if (dsp.getKey().equals("TargetToSave"))
                        targetToSaveModel=dsp.getValue(TargetToSaveModel.class);
                }
                callBack.onReponse(targetToSaveModel.getTotal());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onError(databaseError.getMessage());
            }
        });
    }

    @Override
    public void addSmokeDiary(String userId,String smoked, int cravings, double severity,final DataCallBack<String,String> callBack) {
        SmokeDiaryModel smokeDiaryModel=new SmokeDiaryModel();
        smokeDiaryModel.setSmoked(smoked);
        smokeDiaryModel.setCravings(cravings);
        smokeDiaryModel.setSeverity(severity);

        Calendar cal= Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        SimpleDateFormat year_date = new SimpleDateFormat("YYYY");
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String month_name = month_date.format(cal.getTime());
        String year_name = year_date.format(cal.getTime());
        String date_name = ""+day;

        smokeDiaryModel.setDay(date_name);
        smokeDiaryModel.setDate(month_name+" "+year_name);


        DatabaseReference ref=rootRef.child("users").child(userId).child("SmokeDiarys").push();
        HashMap<String,Object> answersMap=new HashMap<>();
        answersMap.put("SmokeDiary",smokeDiaryModel);

        ref.setValue(answersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    callBack.onReponse("");
                else
                    callBack.onError("Failed to save Target,Try again");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onError("Failed to save Target,Try again");
                    }
                });


    }

    @Override
    public void getSmokeDiarys(String userId,final DataCallBack<ArrayList<SmokeDiaryModel>, String> callBack) {
        rootRef.child("users").child(userId).child("SmokeDiarys").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<SmokeDiaryModel> smokeDiaryModels=new ArrayList<>();
                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    for (DataSnapshot dataSnapshot1:dsp.getChildren() )
                     smokeDiaryModels.add(dataSnapshot1.getValue(SmokeDiaryModel.class));
                }
                if (smokeDiaryModels.size()>0)
                    callBack.onReponse(smokeDiaryModels);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onError(databaseError.getMessage());
            }
        });

    }

    @Override
    public void getSmokeFreeTime(String userId,final DataCallBack<SmokeFreeTime, String> callBack) {
        rootRef.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SmokeFreeTime smokeFreeTime=new SmokeFreeTime();

                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    if (dsp.getKey().equals("SmokeFreeTime"))
                    smokeFreeTime=dsp.getValue(SmokeFreeTime.class);

                }

                if (smokeFreeTime.getStartDate()!=null)
                    callBack.onReponse(smokeFreeTime);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onError(databaseError.getMessage());
            }
        });
    }

    @Override
    public void addSmokeFreeTime(String userId,Date startDate,final DataCallBack<String, String> callBack) {

        SmokeFreeTime smokeFreeTime=new SmokeFreeTime();
        smokeFreeTime.setStartDate(startDate);

        DatabaseReference ref=rootRef.child("users").child(userId);
        HashMap<String,Object> answersMap=new HashMap<>();
        answersMap.put("SmokeFreeTime",smokeFreeTime);

        ref.updateChildren(answersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    callBack.onReponse("");
                else
                    callBack.onError("Failed to save Smoke Free Time,Try again");
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callBack.onError("Failed to save Smoke Free Time,Try again");
                    }
                });
    }

    @Override
    public void updateSmokeFreeTime(String userId,SmokeFreeTime smokeFreeTime) {
        DatabaseReference ref=rootRef.child("users").child(userId);
        HashMap<String,Object> answersMap=new HashMap<>();
        answersMap.put("SmokeFreeTime",smokeFreeTime);
        ref.updateChildren(answersMap);
    }

    @Override
    public void LogOut()
    {
        LoginManager.getInstance().logOut();
        mAuth.signOut();
    }
}
