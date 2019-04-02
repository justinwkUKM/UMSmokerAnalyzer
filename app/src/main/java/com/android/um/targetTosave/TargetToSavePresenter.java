package com.android.um.targetTosave;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.SharedPrefsManager;

public class TargetToSavePresenter implements TargetToSaveContract.Presenter{

    private TargetToSaveContract.View mView;
    private DataHandler mDataHandler;

    public TargetToSavePresenter(TargetToSaveContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
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
