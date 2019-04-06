package com.android.um.smokediary;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.DataModels.SmokeDiaryModel;
import com.android.um.Model.SharedPrefsManager;

import java.util.ArrayList;

public class SmokeDiaryPresenter implements SmokeDiaryContract.Presenter{

    private SmokeDiaryContract.View mView;
    private DataHandler mDataHandler;

    public SmokeDiaryPresenter(SmokeDiaryContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {

    }


    @Override
    public void getSmokeDiary() {
        mDataHandler.getSmokeDiarys(new DataCallBack<ArrayList<SmokeDiaryModel>, String>() {
            @Override
            public void onReponse(ArrayList<SmokeDiaryModel> result) {
                mView.showDiary(result);
            }

            @Override
            public void onError(String result) {
                mView.getDiaryFailed(result);
            }
        });
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
