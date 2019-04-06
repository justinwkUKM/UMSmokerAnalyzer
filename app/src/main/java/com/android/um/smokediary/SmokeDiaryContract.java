package com.android.um.smokediary;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.SmokeDiaryModel;

import java.util.ArrayList;

public interface SmokeDiaryContract {

    interface View extends BaseView<SmokeDiaryContract.Presenter> {
        void showDiary(ArrayList<SmokeDiaryModel> smokeDiaryModels);
        void getDiaryFailed(String error);
    }

    interface Presenter extends BasePresenter {
        void getSmokeDiary();

    }

}
