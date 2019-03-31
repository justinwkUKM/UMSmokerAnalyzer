package com.android.um.splashscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.SharedPrefsManager;

public class SplashScreenPresenter implements SplashScreenContract.Presenter{

    private SplashScreenContract.View mView;
    private DataHandler mDataHandler;

    public SplashScreenPresenter(SplashScreenContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {

    }

//    @Override
//    public void showQuestionsA() {
////        if (mDataHandler.isQuestionsDone("A"))
////            mView.goToMainScreen();
////        else
////           mView.goToMainScreen();
//
//        mDataHandler.isQuestionsDone(new DataCallBack<Boolean, Boolean>() {
//            @Override
//            public void onReponse(Boolean result) {
//                mView.goToMainScreen();
//            }
//            @Override
//            public void onError(Boolean result) {
//
//            }
//        });

    //}


    @Override
    public void showDemographicQuestions() {


        mDataHandler.isQuestionsDone("demographicQuestions",new DataCallBack<Boolean, Boolean>() {
            @Override
            public void onReponse(Boolean result) {
                if (result)
                {
                    mDataHandler.isQuestionsDone("leveladdictionQuestions", new DataCallBack<Boolean, Boolean>() {
                        @Override
                        public void onReponse(Boolean result) {
                            if (result)
                            {
                                mView.goToMainScreen();
                            }
                            else
                                mView.goToLevelAddictionQuestions();
                        }

                        @Override
                        public void onError(Boolean result) {
                            mView.goToLevelAddictionQuestions();
                        }
                    });
                }
                else
                    mView.goToDemographicQuestions();
            }
            @Override
            public void onError(Boolean result) {
                mView.goToDemographicQuestions();
            }
        });

    }

    @Override
    public void showlevelAddictionQuestions() {

    }

    @Override
    public String getLanguage() {
        return null;
    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean checkifLogged() {

        return mDataHandler.checkLogged();
    }
}
