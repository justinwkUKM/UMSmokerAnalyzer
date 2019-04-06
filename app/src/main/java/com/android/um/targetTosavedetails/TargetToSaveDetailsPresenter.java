package com.android.um.targetTosavedetails;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.DataModels.TargetToSaveModel;
import com.android.um.Model.SharedPrefsManager;

public class TargetToSaveDetailsPresenter implements TargetToSaveDetailsContract.Presenter{

    private TargetToSaveDetailsContract.View mView;
    private DataHandler mDataHandler;
    Double totalAmount=0.0;
    TargetToSaveModel target;
    public TargetToSaveDetailsPresenter(TargetToSaveDetailsContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }


    @Override
    public void saveTarget() {
        mDataHandler.saveTargetToSave(target, new DataCallBack<Double, String>() {
            @Override
            public void onReponse(Double result) {
                mView.goToDashBoard();
                mDataHandler.saveTargetToSaveLocally(result);
            }

            @Override
            public void onError(String result) {
                mView.failedToSave(result);
            }
        });
    }

    @Override
    public void start(@Nullable Bundle extras){
        target=extras.getParcelable("TARGET");
        totalAmount=extras.getDouble("TOTAL_AMOUNT");
        mView.showAmounts(totalAmount);
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
