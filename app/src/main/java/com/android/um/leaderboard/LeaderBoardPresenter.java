package com.android.um.leaderboard;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.DataModels.LeaderBoardModel;
import com.android.um.Model.SharedPrefsManager;

import java.util.ArrayList;

public class LeaderBoardPresenter implements LeaderBoardContract.Presenter{

    private LeaderBoardContract.View mView;
    private DataHandler mDataHandler;

    public LeaderBoardPresenter(LeaderBoardContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void getLeaderBoard() {
        mDataHandler.getLeaderBoard(new DataCallBack<ArrayList<LeaderBoardModel>, String>() {
            @Override
            public void onReponse(ArrayList<LeaderBoardModel> result) {
                mView.showLeaderBoard(result);
            }

            @Override
            public void onError(String result) {
                mView.noLeaderBoard(result);
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
