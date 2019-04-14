package com.android.um.motivation_messages;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.DataModels.MotivationMessageModel;
import com.android.um.Model.SharedPrefsManager;

import java.util.ArrayList;

public class MotivationMessagesPresenter implements MotivationMessagesContract.Presenter{

    private MotivationMessagesContract.View mView;
    private DataHandler mDataHandler;

    public MotivationMessagesPresenter(MotivationMessagesContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void getMessages() {
        mDataHandler.getMotivationMessages(new DataCallBack<ArrayList<MotivationMessageModel>, String>() {
            @Override
            public void onReponse(ArrayList<MotivationMessageModel> result) {
                mView.showMessages(result);
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
        return mDataHandler.checkLogged();
    }
}
