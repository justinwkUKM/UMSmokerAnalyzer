package com.android.um.addsmokediary;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.SharedPrefsManager;

public class AddSmokeDiaryPresenter implements AddSmokeDiaryContract.Presenter{

    private AddSmokeDiaryContract.View mView;
    private DataHandler mDataHandler;

    public AddSmokeDiaryPresenter(AddSmokeDiaryContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {

    }


    @Override
    public void addSmokeDiary(final String smoked, int cravings, String severity) {
        double mSeverity=0.0;

        mDataHandler.addSmokeDiary(smoked, cravings, mSeverity, new DataCallBack<String, String>() {
            @Override
            public void onReponse(String result) {
                mView.savingDiarySuccess();
                if (!smoked.equals("I Resisted"))
                {
                    mDataHandler.addSmokeFreeTime(new DataCallBack<String, String>() {
                        @Override
                        public void onReponse(String result) {
                            //DO NOTHING
                        }

                        @Override
                        public void onError(String result) {

                        }
                    });
                }
            }

            @Override
            public void onError(String result) {
                mView.savingDiaryFailed(result);
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
