package com.android.um.unlockfeature;

import com.android.um.BasePresenter;
import com.android.um.BaseView;

public interface UnlockFeatureContract {

    interface View extends BaseView<UnlockFeatureContract.Presenter> {
        void setFeature(String feature);
    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();


    }

}
