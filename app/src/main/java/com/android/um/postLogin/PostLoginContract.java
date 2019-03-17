package com.android.um.postLogin;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.DataModels.User;
import com.android.um.signup.SignupContract;

import java.util.ArrayList;

public interface PostLoginContract {

    interface View extends BaseView<Presenter> {
        void handleLogOut();
    }

    interface Presenter extends BasePresenter {
        User getLoggedUser();
        void LogOut();
    }
}
