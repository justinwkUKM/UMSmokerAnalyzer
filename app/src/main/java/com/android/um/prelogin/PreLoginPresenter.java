package com.android.um.prelogin;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.SharedPrefsManager;

public class PreLoginPresenter implements PreLoginContract.Presenter{

    private PreLoginContract.View mView;
    private DataHandler mDataHandler;

    public PreLoginPresenter(PreLoginContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void goToSigninPage() {
        if (mDataHandler.getTermsAcceptence())
            mView.goToSigninPage();
        else
            mView.showTermsError();
    }

    @Override
    public void goToSignupPage() {
        if (mDataHandler.getTermsAcceptence())
            mView.goToSignupPage();
        else
            mView.showTermsError();
    }

    @Override
    public void start(@Nullable Bundle extras){
    }

    @Override
    public String getLanguage() {

        return mDataHandler.getLanguage();
    }

    @Override
    public void destroy() {
    }

    @Override
    public boolean checkifLogged() {
        return false;
    }
}
