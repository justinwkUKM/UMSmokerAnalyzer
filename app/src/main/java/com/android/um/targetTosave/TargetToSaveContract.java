package com.android.um.targetTosave;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.TargetToSaveModel;

public interface TargetToSaveContract {

    interface View extends BaseView<TargetToSaveContract.Presenter> {
        void goToDetails();
    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();

    }

}
