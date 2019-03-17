package com.android.um;


import com.android.um.Model.DataModels.Question;
import com.android.um.postLogin.PostLoginContract;
import com.android.um.postLogin.PostLoginPresenter;
import com.android.um.questionnaire.questions_a.QuestionContract;
import com.android.um.questionnaire.questions_a.QuestionPresenter;
import com.android.um.splashscreen.SplashScreenContract;
import com.android.um.splashscreen.SplashScreenPresenter;
import com.android.um.resetpassword.ForgetPasswordContract;
import com.android.um.resetpassword.ForgetPasswordPresenter;
import com.android.um.signin.SigninContract;
import com.android.um.signin.SigninPresenter;
import com.android.um.signup.SignupContract;
import com.android.um.signup.SignupPresenter;


public class PresenterInjector {

    public static void injectSignInPresenter(SigninContract.View signInView) {
        new SigninPresenter(signInView);
    }

    public static void injectProfilePresenter(ForgetPasswordContract.View forgetView) {
        new ForgetPasswordPresenter(forgetView);
    }

    public static void injectSignupPresenter(SignupContract.View signupView) {
        new SignupPresenter(signupView);
    }

    public static void injectPostLoginPresenter(PostLoginContract.View postLoginView) {
        new PostLoginPresenter(postLoginView);
    }

    public static void injectSplashScreenPresenter(SplashScreenContract.View splashscreenView) {
        new SplashScreenPresenter(splashscreenView);
    }

    public static void injectQuestionPresenter(QuestionContract.View questionView) {
        new QuestionPresenter(questionView);
    }


}
