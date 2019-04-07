package com.android.um.dashboard;

import com.android.um.BasePresenter;
import com.android.um.BaseView;

public interface DashboardContract {

    interface View extends BaseView<DashboardContract.Presenter> {
        void showTargetToSave(String total);
        void updateSmokeFreeTimer(long seconds,long minutes,long hours);
        void unlockFeature();
        void showSmokeFreeTime();
    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();
        void startSmokeFreeTime();
        void getTargetToSave();
        void stopTimer();
    }

}
