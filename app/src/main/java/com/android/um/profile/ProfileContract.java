package com.android.um.profile;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.User;

import java.util.ArrayList;

public interface ProfileContract {

    interface View extends BaseView<ProfileContract.Presenter> {
        void showUserInfo(User user);

    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();
        void getUser();
    }

}
