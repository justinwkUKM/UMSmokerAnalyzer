package com.android.um.services;

import com.android.um.BasePresenter;
import com.android.um.BaseView;

public interface FirebaseServiceContract {

    interface Service extends BaseView<FirebaseServiceContract.Presenter> {

    }

    interface Presenter extends BasePresenter {
        void sendRegistrationToServer(String token);
    }

}
