package com.android.um.socialsupport;

import com.android.um.BasePresenter;
import com.android.um.BaseView;

public interface SocialSupportContract {

    interface View extends BaseView<SocialSupportContract.Presenter> {
    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();

    }

}
