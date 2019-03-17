package com.android.um.questionnaire.questions_a;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.SharedPrefsManager;

import java.util.ArrayList;


public class QuestionPresenter implements QuestionContract.Presenter {

    private QuestionContract.View mView;
    private DataHandler mDataHandler;

    public QuestionPresenter(QuestionContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void getQuestions() {
        mDataHandler.getQuestions("a", new DataCallBack<ArrayList<Question>, String>() {
            @Override
            public void onReponse(ArrayList<Question> result) {
                mView.getQuestions(result);
            }

            @Override
            public void onError(String result) {
                mView.failedToLoadQuestions(result);
            }
        });
    }

    @Override
    public void start(@Nullable Bundle extras) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean checkifLogged() {
        return false;
    }
}
