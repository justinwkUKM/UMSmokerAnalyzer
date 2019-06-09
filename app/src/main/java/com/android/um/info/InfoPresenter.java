package com.android.um.info;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.SharedPrefsManager;

public class InfoPresenter implements InfoContract.Presenter{

    private InfoContract.View mView;
    private DataHandler mDataHandler;

    public InfoPresenter(InfoContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }


    @Override
    public void checkQuestions() {
        mDataHandler.checkVideoQuestions(5, new DataCallBack<Boolean, String>() {
            @Override
            public void onReponse(Boolean result) {
                if (result)
                    mView.showInfo();
            }

            @Override
            public void onError(String result) {
                mView.lockInfo(result);
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
        return mDataHandler.checkLogged();
    }
}
