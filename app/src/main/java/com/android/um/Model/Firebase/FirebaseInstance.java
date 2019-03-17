package com.android.um.Model.Firebase;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.DataModels.User;
import com.android.um.Model.DataModels.options;
import com.android.um.Utils.AppUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
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

import java.util.ArrayList;

public class FirebaseInstance implements FirebaseHandler {

    private static FirebaseInstance INSTANCE = null;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference rootRef;
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
                { callback.onReponse(user);
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
    public void signinWithGoogle(GoogleSignInAccount account, final DataCallBack<User,String> callBack) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    if (firebaseUser!=null)
                    {
                        User user=new User();
                        user.setFirebaseUser(firebaseUser);
                        callBack.onReponse(user);
                    }
                    else
                        callBack.onError(task.getException().getMessage());
                  }
                else
                    callBack.onError(task.getException().getMessage());
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callBack.onError(e.getMessage());
            }
        });

    }

    @Override
    public void signinWithFacebook( CallbackManager callbackManager,final DataCallBack<User,FacebookException> respone) {

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        authFaceBook(loginResult.getAccessToken(), new DataCallBack<FirebaseUser, String>() {
                            @Override
                            public void onReponse(FirebaseUser result) {
                                User user=new User();
                                user.setFirebaseUser(result);
                                respone.onReponse(user);
                            }

                            @Override
                            public void onError(String result) {
                                FacebookException exception=new FacebookException(result);
                                respone.onError(exception);

                            }
                        });
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        respone.onError(exception);
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
        return false;
    }

    @Override
    public void getQuestions(String catgeory, final DataCallBack<ArrayList<Question>, String> callBack) {
        String x=catgeory;
        rootRef.child("questions").child("questions_"+catgeory).addValueEventListener(new ValueEventListener() {
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
                    callBack.onError("No Questions");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onError(databaseError.getMessage());
            }
        });

    }


    @Override
    public User getLoggedUser() {
        User user=new User();
        if (mAuth.getCurrentUser()!=null)
            user.setFirebaseUser(mAuth.getCurrentUser());
        else
            user.setUsername("");
        return user;
    }

    @Override
    public void LogOut() {
        mAuth.signOut();
    }
}
