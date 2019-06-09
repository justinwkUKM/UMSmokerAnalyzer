package com.android.um.achievement;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.PersonalityModel;

import java.util.ArrayList;

public interface AchievementContract {

    interface View extends BaseView<AchievementContract.Presenter> {
        void showPersonalityInfo(ArrayList<PersonalityModel> personalityModels);
        void showErrorMessage(String error);
    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();
        void getPersonality();
    }

}
