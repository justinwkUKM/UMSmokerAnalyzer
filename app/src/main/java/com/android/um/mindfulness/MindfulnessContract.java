package com.android.um.mindfulness;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.Question;

import java.util.ArrayList;

public interface MindfulnessContract {

    interface View extends BaseView<MindfulnessContract.Presenter> {
        void showMindfulnessVideos(ArrayList<String> videos);
        void failedToGetVideos(String error);
        void showQuestions(String category);
        void playVideo(int index,String url);
    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();
        void getMindfulnessVideos();
        void checkVideoQuestions(int index,String url);
    }

}
