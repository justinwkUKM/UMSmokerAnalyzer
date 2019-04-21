package com.android.um.services;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.SharedPrefsManager;

public class FirebaseServicePresenter implements FirebaseServiceContract.Presenter{

    private FirebaseServiceContract.Service mService;
    private DataHandler mDataHandler;

    public FirebaseServicePresenter(FirebaseServiceContract.Service service) {
        this.mService = service;
        this.mDataHandler = DataHandlerInstance.getInstance();
        mService.setPresenter(this);
    }

    @Override
    public void sendRegistrationToServer(String token) {
        mDataHandler.sendToken(token);
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
