package com.android.um.splashscreen;

import com.android.um.BasePresenter;
import com.android.um.BaseView;

public interface SplashScreenContract {

    interface View extends BaseView<SplashScreenContract.Presenter> {
        void handleLogged();
        void goToDemographicQuestions();
        void goToLevelAddictionQuestions();
        void goToMainScreen();
    }

    interface Presenter extends BasePresenter {
        void showDemographicQuestions();
        void showlevelAddictionQuestions();
    }

}
