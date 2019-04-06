package com.android.um.dashboard;

import com.android.um.BasePresenter;
import com.android.um.BaseView;

public interface DashboardContract {

    interface View extends BaseView<DashboardContract.Presenter> {
        void showTargetToSave(String total);
    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();
        void getTargetToSave();
    }

}
