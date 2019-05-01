package com.android.um.main;

import com.android.um.BasePresenter;
import com.android.um.BaseView;

public interface MainActivityContract {

    interface View extends BaseView<MainActivityContract.Presenter> {
        void handleLogOut();
    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();
        void getToken();
        void logout();
    }

}
