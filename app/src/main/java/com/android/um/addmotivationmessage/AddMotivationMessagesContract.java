package com.android.um.addmotivationmessage;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.MotivationMessageModel;

import java.util.ArrayList;

public interface AddMotivationMessagesContract {

    interface View extends BaseView<AddMotivationMessagesContract.Presenter> {
        void addMessageSuccess(String message);
        void addMessageFailed(String error);
    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();
        void addMessage(String message);
    }

}
