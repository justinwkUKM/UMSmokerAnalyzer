package com.android.um.mindfulness;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.SharedPrefsManager;

import java.util.ArrayList;

public class MindfulnessPresenter implements MindfulnessContract.Presenter{

    private MindfulnessContract.View mView;
    private DataHandler mDataHandler;

    public MindfulnessPresenter(MindfulnessContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void checkVideoQuestions(int index,String url) {
        mDataHandler.checkVideoQuestions(index, new DataCallBack<Boolean, String>() {
            @Override
            public void onReponse(Boolean result) {
                if (result)
                    mView.playVideo(index,url);

            }

            @Override
            public void onError(String result) {
                if (result!=null && result.length()>0)
                 mView.showQuestions(result);
            }
        });
    }

    @Override
    public void getMindfulnessVideos() {
        mDataHandler.getMindfulnessVideos(new DataCallBack<ArrayList<String>, String>() {
            @Override
            public void onReponse(ArrayList<String> result) {
                mView.showMindfulnessVideos(result);
            }

            @Override
            public void onError(String result) {
                mView.failedToGetVideos(result);
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
