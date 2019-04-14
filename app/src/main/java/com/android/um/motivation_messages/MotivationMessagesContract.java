package com.android.um.motivation_messages;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.MotivationMessageModel;

import java.util.ArrayList;

public interface MotivationMessagesContract {

    interface View extends BaseView<MotivationMessagesContract.Presenter> {
        void showMessages(ArrayList<MotivationMessageModel> messages);

    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();
        void getMessages();
    }

}
