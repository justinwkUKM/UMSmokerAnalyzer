package com.android.um.targetTosavedetails;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.TargetToSaveModel;

public interface TargetToSaveDetailsContract {

    interface View extends BaseView<TargetToSaveDetailsContract.Presenter> {
        void showAmounts(double totalAmount);
        void goToDashBoard();
        void failedToSave(String error);
    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();
        void saveTarget();

    }

}
