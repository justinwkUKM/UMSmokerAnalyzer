package com.android.um.terms;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.SharedPrefsManager;

public class TermsPresenter implements TermsContract.Presenter{

    private TermsContract.View mView;
    private DataHandler mDataHandler;

    public TermsPresenter(TermsContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void saveTermAcceptence(boolean agree) {
        mDataHandler.saveTermsAcceptence(agree);

        if (agree)
            mView.goToLogin();
        else
            mView.goToPreLogin();

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
