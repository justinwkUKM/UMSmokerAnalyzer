package com.android.um.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.SharedPrefsManager;

public class DashboardPresenter implements DashboardContract.Presenter{

    private DashboardContract.View mView;
    private DataHandler mDataHandler;

    public DashboardPresenter(DashboardContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }


    @Override
    public void start(@Nullable Bundle extras){
    }

    @Override
    public void getTargetToSave() {

        if (mDataHandler.getTargetToSaveLocaly()!=null && Double.parseDouble(mDataHandler.getTargetToSaveLocaly())!=0.0)
        {
            mView.showTargetToSave(mDataHandler.getTargetToSaveLocaly());
        }
        else
        {
            mDataHandler.getTargetToSaveOnline(new DataCallBack<Double, String>() {
                @Override
                public void onReponse(Double result) {
                    mView.showTargetToSave(""+result);
                }

                @Override
                public void onError(String result) {
                    mView.showTargetToSave("0.0");
                }
            });
        }


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
