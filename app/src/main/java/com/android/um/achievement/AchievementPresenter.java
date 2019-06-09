package com.android.um.achievement;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.DataModels.PersonalityModel;
import com.android.um.Model.SharedPrefsManager;

import java.util.ArrayList;

public class AchievementPresenter implements AchievementContract.Presenter{

    private AchievementContract.View mView;
    private DataHandler mDataHandler;

    public AchievementPresenter(AchievementContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void getPersonality() {
        mDataHandler.getPersonality(new DataCallBack<ArrayList<PersonalityModel>, String>() {
            @Override
            public void onReponse(ArrayList<PersonalityModel> result) {
                mView.showPersonalityInfo(result);
            }

            @Override
            public void onError(String result) {
            mView.showErrorMessage(result);
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
