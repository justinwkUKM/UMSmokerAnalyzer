package com.android.um.unlockfeature;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.SharedPrefsManager;

public class UnlockFeaturePresenter implements UnlockFeatureContract.Presenter{

    private UnlockFeatureContract.View mView;
    private DataHandler mDataHandler;

    public UnlockFeaturePresenter(UnlockFeatureContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }


    @Override
    public void start(@Nullable Bundle extras){
        mView.setFeature(extras.getString("FEATURE"));
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
