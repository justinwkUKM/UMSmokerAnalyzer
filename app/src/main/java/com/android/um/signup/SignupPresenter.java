package com.android.um.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.DataModels.User;
import com.android.um.Model.SharedPrefsManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupPresenter implements SignupContract.Presenter {

    private SignupContract.View mView;
    private DataHandler mDataHandler;

    public SignupPresenter(SignupContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void Signup(User user) {
        mDataHandler.signupUser(user, new DataCallBack<User, String>() {
            @Override
            public void onReponse(User result) {
                mDataHandler.setLogged();
                mView.handleSignup(result);
                mDataHandler.saveUserSharedPref(result);
            }

            @Override
            public void onError(String result) {
                mView.handleFailedSignup(result);
            }
        });
    }

    @Override
    public void saveUserInfo(final User user) {
            mDataHandler.saveUserInFirebase(user, new DataCallBack<User, String>() {
                @Override
                public void onReponse(User result){
                    mDataHandler.setLogged();
                    mView.handleSignup(user);
                    mDataHandler.saveUserSharedPref(result);
                }

                @Override
                public void onError(String result) {
                    mView.handleFailedSignup(result);
                }
            });
    }

    @Override
    public void start(@Nullable Bundle extras) {

        if (extras!=null && extras.getParcelable("mUser")!=null)
        {
                User  user=(User) extras.get("mUser");
                mView.handleSignInActivity(user);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean checkifLogged() {
        return false;
    }
}
