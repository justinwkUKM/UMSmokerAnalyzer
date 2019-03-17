package com.android.um.questionnaire.questions_a;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.DataModels.User;
import com.android.um.signup.SignupContract;

import java.util.ArrayList;

public interface QuestionContract {

    interface View extends BaseView<QuestionContract.Presenter> {
        void getQuestions(ArrayList<Question> questions);
        void failedToLoadQuestions(String error);

    }

    interface Presenter extends BasePresenter {
        void getQuestions();
    }
}
