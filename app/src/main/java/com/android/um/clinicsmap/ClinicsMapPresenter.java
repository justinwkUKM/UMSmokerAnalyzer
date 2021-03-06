package com.android.um.clinicsmap;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.DataModels.KliniksModel;
import com.android.um.Model.SharedPrefsManager;

import java.util.ArrayList;

public class ClinicsMapPresenter implements ClinicsMapContract.Presenter{

    private ClinicsMapContract.View mView;
    private DataHandler mDataHandler;

    public ClinicsMapPresenter(ClinicsMapContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void getKliniks() {
        mDataHandler.getKliniks(new DataCallBack<ArrayList<KliniksModel>, String>() {
            @Override
            public void onReponse(ArrayList<KliniksModel> result) {
                mView.setKliniksLocations(result);
            }

            @Override
            public void onError(String result) {

            }
        });
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
