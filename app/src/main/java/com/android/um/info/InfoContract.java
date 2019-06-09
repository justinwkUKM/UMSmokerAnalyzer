package com.android.um.info;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.User;

public interface InfoContract {

    interface View extends BaseView<InfoContract.Presenter> {
        void showInfo();
        void lockInfo(String category);

    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();
        void checkQuestions();
    }

}
