package com.android.um.signup;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.User;
import com.android.um.signin.SigninContract;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface SignupContract  {

    interface View extends BaseView<SignupContract.Presenter> {

        void handleSignup(User user);
        void handleFailedSignup(String message);

    }

    interface Presenter extends BasePresenter {
        void Signup(String email,String password);
    }


}
