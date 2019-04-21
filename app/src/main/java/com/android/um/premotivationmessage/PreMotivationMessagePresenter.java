package com.android.um.premotivationmessage;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.SharedPrefsManager;

public class PreMotivationMessagePresenter implements PreMotivationMessageContract.Presenter{

    private PreMotivationMessageContract.View mView;
    private DataHandler mDataHandler;
    String name;
    public PreMotivationMessagePresenter(PreMotivationMessageContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void saveImageUrl(String url) {
        mDataHandler.saveString("MOTIVATOR_IMAGE",url);
    }

    @Override
    public void start(@Nullable Bundle extras){
        name=extras.getString("MOTIVATOR_NAME");
        mView.getMotivatorName(name);
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
        return mDataHandler.checkLogged();
    }
}
