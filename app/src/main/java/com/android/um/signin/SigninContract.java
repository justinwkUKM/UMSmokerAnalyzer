package com.android.um.signin;

import android.content.Context;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.User;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public interface SigninContract {

    interface View extends BaseView<Presenter> {

      void signInSuccess();
      void signInFailed(String message);
      void continueToSignUp(User user);
      void startGoogleIntent();
      void signup();
      void goToDemographicQuestionsScreen();
      void goToLevelAddictionScreen();
      void goToMainScreen();
    }

    interface Presenter extends BasePresenter {
        void showQuestionsA();
        void signInUser(User user);
        void signInWithGoogle(GoogleSignInAccount account);
        void signinWithFaceBook( CallbackManager callbackManager);
    }
}
