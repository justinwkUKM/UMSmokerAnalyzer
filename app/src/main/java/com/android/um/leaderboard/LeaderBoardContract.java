package com.android.um.leaderboard;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.LeaderBoardModel;

import java.util.ArrayList;

public interface LeaderBoardContract {

    interface View extends BaseView<LeaderBoardContract.Presenter> {

        void showLeaderBoard(ArrayList<LeaderBoardModel> leaderBoardModels);
        void noLeaderBoard(String message);

    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();

        void getLeaderBoard();
    }

}
