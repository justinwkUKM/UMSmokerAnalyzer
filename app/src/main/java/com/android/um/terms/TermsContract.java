package com.android.um.terms;

import com.android.um.BasePresenter;
import com.android.um.BaseView;

public interface TermsContract {

    interface View extends BaseView<TermsContract.Presenter> {
        void goToPreLogin();
        void goToLogin();
    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();

        void saveTermAcceptence(boolean agree);
    }

}
