package com.android.um;


import com.android.um.language.LanguageContract;
import com.android.um.language.LanguagePresenter;
import com.android.um.postLogin.PostLoginContract;
import com.android.um.postLogin.PostLoginPresenter;
import com.android.um.prelogin.PreLoginContract;
import com.android.um.prelogin.PreLoginPresenter;
import com.android.um.questions.QuestionsContract;
import com.android.um.questions.QuestionsPresenter;
import com.android.um.splashscreen.SplashScreenContract;
import com.android.um.splashscreen.SplashScreenPresenter;
import com.android.um.resetpassword.ForgetPasswordContract;
import com.android.um.resetpassword.ForgetPasswordPresenter;
import com.android.um.signin.SigninContract;
import com.android.um.signin.SigninPresenter;
import com.android.um.signup.SignupContract;
import com.android.um.signup.SignupPresenter;
import com.android.um.terms.TermsContract;
import com.android.um.terms.TermsPresenter;


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

    public static void injectDemographicQuestionsPresenter(QuestionsContract.View questionView) {
        new QuestionsPresenter(questionView);
    }

    public static void injectLanguagePresenter(LanguageContract.View languageView) {
        new LanguagePresenter(languageView);
    }

    public static void injectPreLoginPresenter(PreLoginContract.View preLoginView) {
        new PreLoginPresenter(preLoginView);
    }

    public static void injectTermsPresenter(TermsContract.View termsView) {
        new TermsPresenter(termsView);
    }


}
