package com.android.um.resetpassword;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.SharedPrefsManager;

public class ForgetPasswordPresenter implements ForgetPasswordContract.Presenter {

    private ForgetPasswordContract.View mView;
    private DataHandler mDataHandler;

    public ForgetPasswordPresenter(ForgetPasswordContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void resetPassword(String email) {
        mDataHandler.resetPassword(email, new DataCallBack<String,String>() {
            @Override
            public void onReponse(String result) {
                mView.resetResult(result);
            }

            @Override
            public void onError(String result) {
                mView.resetResult(result);
            }
        });
    }

    @Override
    public void start(@Nullable Bundle extras) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean checkifLogged() {
        return false;
    }
}
