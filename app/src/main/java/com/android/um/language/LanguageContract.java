package com.android.um.language;

import com.android.um.BasePresenter;
import com.android.um.BaseView;

public interface LanguageContract {

    interface View extends BaseView<LanguageContract.Presenter> {
        void goToPreLogin();
    }

    interface Presenter extends BasePresenter {
        void saveLanguage(String language);

    }

}
