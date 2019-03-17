package com.android.um.splashscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.SharedPrefsManager;

public class SplashScreenPresenter implements SplashScreenContract.Presenter{

    private SplashScreenContract.View mView;
    private DataHandler mDataHandler;

    public SplashScreenPresenter(SplashScreenContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean checkifLogged() {
        return mDataHandler.checkLogged();
    }
}
