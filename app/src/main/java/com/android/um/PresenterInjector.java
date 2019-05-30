package com.android.um;


import com.android.um.addmotivationmessage.AddMotivationMessagesContract;
import com.android.um.addmotivationmessage.AddMotivationMessagesPresenter;
import com.android.um.addsmokediary.AddSmokeDiaryContract;
import com.android.um.addsmokediary.AddSmokeDiaryPresenter;
import com.android.um.dashboard.DashboardContract;
import com.android.um.dashboard.DashboardPresenter;
import com.android.um.language.LanguageContract;
import com.android.um.language.LanguagePresenter;
import com.android.um.main.MainActivityContract;
import com.android.um.main.MainActivityPresenter;
import com.android.um.mindfulness.MindfulnessContract;
import com.android.um.mindfulness.MindfulnessPresenter;
import com.android.um.motivation_messages.MotivationMessagesContract;
import com.android.um.motivation_messages.MotivationMessagesPresenter;
import com.android.um.postLogin.PostLoginContract;
import com.android.um.postLogin.PostLoginPresenter;
import com.android.um.prelogin.PreLoginContract;
import com.android.um.prelogin.PreLoginPresenter;
import com.android.um.premotivationmessage.PreMotivationMessageContract;
import com.android.um.premotivationmessage.PreMotivationMessagePresenter;
import com.android.um.profile.ProfileContract;
import com.android.um.profile.ProfilePresenter;
import com.android.um.questions.QuestionsContract;
import com.android.um.questions.QuestionsPresenter;
import com.android.um.services.FirebaseServiceContract;
import com.android.um.services.FirebaseServicePresenter;
import com.android.um.smokediary.SmokeDiaryContract;
import com.android.um.smokediary.SmokeDiaryPresenter;
import com.android.um.socialsupport.SocialSupportContract;
import com.android.um.socialsupport.SocialSupportPresenter;
import com.android.um.splashscreen.SplashScreenContract;
import com.android.um.splashscreen.SplashScreenPresenter;
import com.android.um.resetpassword.ForgetPasswordContract;
import com.android.um.resetpassword.ForgetPasswordPresenter;
import com.android.um.signin.SigninContract;
import com.android.um.signin.SigninPresenter;
import com.android.um.signup.SignupContract;
import com.android.um.signup.SignupPresenter;
import com.android.um.targetTosave.TargetToSaveContract;
import com.android.um.targetTosave.TargetToSavePresenter;
import com.android.um.targetTosavedetails.TargetToSaveDetailsContract;
import com.android.um.targetTosavedetails.TargetToSaveDetailsPresenter;
import com.android.um.terms.TermsContract;
import com.android.um.terms.TermsPresenter;
import com.android.um.unlockfeature.UnlockFeatureContract;
import com.android.um.unlockfeature.UnlockFeaturePresenter;


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

    public static void injectTargetToSavePresenter(TargetToSaveContract.View targetView) {
        new TargetToSavePresenter(targetView);
    }

    public static void injectTargetToSaveDetailsPresenter(TargetToSaveDetailsContract.View targetView) {
        new TargetToSaveDetailsPresenter(targetView);
    }

    public static void injectSmokeDiaryPresenter(SmokeDiaryContract.View smokeDiaryView) {
        new SmokeDiaryPresenter(smokeDiaryView);
    }

    public static void injectAddSmokeDiaryPresenter(AddSmokeDiaryContract.View addSmokeDiaryView) {
        new AddSmokeDiaryPresenter(addSmokeDiaryView);
    }

    public static void injectDashboardPresenter(DashboardContract.View dashboardView) {
        new DashboardPresenter(dashboardView);
    }

    public static void injectUnlockFeaturePresenter(UnlockFeatureContract.View unlockFeatureView) {
        new UnlockFeaturePresenter(unlockFeatureView);
    }

    public static void injectMotivationMessagesPresenter(MotivationMessagesContract.View motiviationMessagesView) {
        new MotivationMessagesPresenter(motiviationMessagesView);
    }

    public static void injectAddMotivationMessagesPresenter(AddMotivationMessagesContract.View addMotiviationMessagesView) {
        new AddMotivationMessagesPresenter(addMotiviationMessagesView);
    }

    public static void injectPreMotivationMessagesPresenter(PreMotivationMessageContract.View preMotiviationMessagesView) {
        new PreMotivationMessagePresenter(preMotiviationMessagesView);
    }

    public static void injectSocialSupportPresenter(SocialSupportContract.View socialSupportView) {
        new SocialSupportPresenter(socialSupportView);
    }

    public static void injectMainPresenter(MainActivityContract.View mainView) {
        new MainActivityPresenter(mainView);
    }

    public static void injectFirebaseServicePresenter(FirebaseServiceContract.Service firebaseService) {
        new FirebaseServicePresenter(firebaseService);
    }

    public static void injectMindfulnessPresenter(MindfulnessContract.View mindefulness) {
        new MindfulnessPresenter(mindefulness);
    }

    public static void injectProfilePresenter(ProfileContract.View profileView) {
        new ProfilePresenter(profileView);
    }


}
