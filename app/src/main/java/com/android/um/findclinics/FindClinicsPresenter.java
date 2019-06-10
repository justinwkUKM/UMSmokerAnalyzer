package com.android.um.findclinics;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.SharedPrefsManager;

public class FindClinicsPresenter implements FindClinicsContract.Presenter{

    private FindClinicsContract.View mView;
    private DataHandler mDataHandler;

    public FindClinicsPresenter(FindClinicsContract.View view) {
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
        return mDataHandler.checkLogged();
    }
}
