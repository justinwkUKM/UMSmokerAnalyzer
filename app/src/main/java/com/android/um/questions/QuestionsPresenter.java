package com.android.um.questions;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.DataModels.AnsweredQuestion;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.SharedPrefsManager;

import java.util.ArrayList;


public class QuestionsPresenter implements QuestionsContract.Presenter {

    private QuestionsContract.View mView;
    private DataHandler mDataHandler;

    public QuestionsPresenter(QuestionsContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void getQuestions(String category) {
        mView.showLoading();
        mDataHandler.getQuestions(category, new DataCallBack<ArrayList<Question>, String>() {
            @Override
            public void onReponse(ArrayList<Question> result) {

                mView.hideLoading();
                mView.getQuestions(result);
            }

            @Override
            public void onError(String result) {
                mView.hideLoading();
                mView.failedToLoadQuestions(result);
            }
        });
    }


    @Override
    public void saveAnsweredQuestions(String category, ArrayList<AnsweredQuestion> questions) {
        mDataHandler.saveUserAnsweredQuestions(category,questions, new DataCallBack<String, String>() {
            @Override
            public void onReponse(String result) {
                mView.SuccessSaveQuestions();
            }

            @Override
            public void onError(String result) {
                mView.failedToSaveQuestions(result);
            }
        });
    }

    @Override
    public void isQuestionAnswered(final String category) {
         mDataHandler.isQuestionsDone(category, new DataCallBack<Boolean, Boolean>() {
             @Override
             public void onReponse(Boolean result) {
                 if (result)
                     mView.SuccessSaveQuestions();
                 else
                     mView.goToQuestions(category);
             }

             @Override
             public void onError(Boolean result) {
                 mView.goToQuestions(category);
             }
         });
    }

    @Override
    public String getLanguage() {
        return mDataHandler.getLanguage();
    }

    @Override
    public void setQuestionsAnswered(String part) {
        mDataHandler.setQuestionsAnswered(part);
    }

    @Override
    public void start(@Nullable Bundle extras) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean checkifLogged() {
        return mDataHandler.checkLogged();
    }
}
