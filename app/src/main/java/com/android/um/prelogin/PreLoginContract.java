package com.android.um.prelogin;

import com.android.um.BasePresenter;
import com.android.um.BaseView;

public interface PreLoginContract {

    interface View extends BaseView<PreLoginContract.Presenter> {
        void goToSigninPage();
        void goToSignupPage();
        void goToTermsPage();
        void showTermsError();
    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();
        void goToSigninPage();
        void goToSignupPage();
    }

}
