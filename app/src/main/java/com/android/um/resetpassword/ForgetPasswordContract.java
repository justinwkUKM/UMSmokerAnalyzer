package com.android.um.resetpassword;

import com.android.um.BasePresenter;
import com.android.um.BaseView;

public interface ForgetPasswordContract {

    interface View extends BaseView<Presenter> {

        void resetResult(String message);
    }

    interface Presenter extends BasePresenter {

        void resetPassword(String email);
    }
}
