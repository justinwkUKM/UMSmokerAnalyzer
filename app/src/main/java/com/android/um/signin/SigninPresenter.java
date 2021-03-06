package com.android.um.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.DataModels.User;
import com.android.um.Model.SharedPrefsManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


public class SigninPresenter implements SigninContract.Presenter{

    private SigninContract.View mView;
    private DataHandler mDataHandler;

    public SigninPresenter(SigninContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void showQuestionsA() {
        mDataHandler.isQuestionsDone("demographicQuestions",new DataCallBack<Boolean, Boolean>() {
            @Override
            public void onReponse(Boolean result) {
                if (result)
                {
                    mDataHandler.isQuestionsDone("leveladdictionQuestions", new DataCallBack<Boolean, Boolean>() {
                        @Override
                        public void onReponse(Boolean result) {
                            if (result)
                                mView.goToMainScreen();
                            else
                                mView.goToLevelAddictionScreen();
                        }

                        @Override
                        public void onError(Boolean result) {
                            mView.goToLevelAddictionScreen();
                        }
                    });
                }
                else
                    mView.goToDemographicQuestionsScreen();
            }

            @Override
            public void onError(Boolean result) {
                mView.goToDemographicQuestionsScreen();
            }
        });
    }

    @Override
    public void signInUser(User user) {
        mDataHandler.signInUser(user, new DataCallBack<User,String>() {
            @Override
            public void onReponse(User result) {
                mDataHandler.saveUserSharedPref(result);
                mView.signInSuccess();
                mDataHandler.setLogged();

            }

            @Override
            public void onError(String error) {
                mDataHandler.LogOut();
                mView.signInFailed(error);
            }
        });
    }

    @Override
    public void signInWithGoogle(GoogleSignInAccount account) {
        mDataHandler.signinWithGoogle(account, new DataCallBack<User,String>() {
            @Override
            public void onReponse(User result)
            {
                mDataHandler.saveUserSharedPref(result);
                if (result!=null && result.getAge()!=0) {
                    mDataHandler.setLogged();
                    mDataHandler.isQuestionsDone("leveladdictionQuestions", new DataCallBack<Boolean, Boolean>() {
                        @Override
                        public void onReponse(Boolean flag) {
                            if (flag)
                            {
                                mView.signInSuccess();

                            }
                            else
                            {
                                mView.goToLevelAddictionScreen();
                            }
                        }
                        @Override
                        public void onError(Boolean result) {
                            mView.goToLevelAddictionScreen();
                        }
                    });
                }
                else
                {

                    mView.continueToSignUp(result);
                }

            }
            @Override
            public void onError(String result) {
                mDataHandler.LogOut();
                if (result!=null && result!=null)
                    mView.signInFailed(result);
                else {
                    mView.hideLoading();
                }
            }
        });
    }

    //TODO maybe later we can add flag to user model to know which provider created this user (fb-google...etc)
    //in case if log in failed due to already exists email so we can now by which email we need to use..

    @Override
    public void signinWithFaceBook(CallbackManager callbackManager) {
        mDataHandler.signinWithFacebook(callbackManager,new DataCallBack<User, FacebookException>() {
            @Override
            public void onReponse(User result) {
                mDataHandler.saveUserSharedPref(result);
                mView.hideLoading();
                if (result!=null && result.getAge()!=0) {
                    mView.signInSuccess();
                    mDataHandler.setLogged();

                }
                else
                    mView.continueToSignUp(result);
            }

            @Override
            public void onError(FacebookException result) {
                mDataHandler.LogOut();
                mView.hideLoading();

                if (result!=null && result.getMessage()!=null)
                    mView.signInFailed(result.getMessage());
                else {
                    mView.signInFailed("Something went wrong,Please try again!");
                }
            }
        });
    }

    @Override
    public String getLanguage() {
        return mDataHandler.getLanguage();
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
