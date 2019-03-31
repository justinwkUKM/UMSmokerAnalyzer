package com.android.um.questions;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataModels.AnsweredQuestion;
import com.android.um.Model.DataModels.Question;

import java.util.ArrayList;

public interface QuestionsContract {

    interface View extends BaseView<Presenter> {
        void getQuestions(ArrayList<Question> questions);
        void failedToLoadQuestions(String error);
        void failedToSaveQuestions(String error);
        void SuccessSaveQuestions();
        void goToQuestions(String category);
    }

    interface Presenter extends BasePresenter {
        void getQuestions(String category);
        void setQuestionsAnswered(String part);
        void saveAnsweredQuestions(String category,ArrayList<AnsweredQuestion> questions);
        void isQuestionAnswered(String category);
    }
}
