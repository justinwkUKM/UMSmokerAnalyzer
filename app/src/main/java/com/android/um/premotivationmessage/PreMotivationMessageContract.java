package com.android.um.premotivationmessage;

import com.android.um.BasePresenter;
import com.android.um.BaseView;

public interface PreMotivationMessageContract {

    interface View extends BaseView<PreMotivationMessageContract.Presenter> {
        void getMotivatorName(String name);
    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();

        void saveImageUrl(String url);


    }

}
