package com.android.um.language;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.SharedPrefsManager;

public class LanguagePresenter implements LanguageContract.Presenter{

    private LanguageContract.View mView;
    private DataHandler mDataHandler;

    public LanguagePresenter(LanguageContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {

    }

    @Override
    public void saveLanguage(String language) {
        mDataHandler.saveLanguage(language);
        mView.goToPreLogin();
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
