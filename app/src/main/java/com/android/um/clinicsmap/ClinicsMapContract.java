package com.android.um.clinicsmap;

import com.android.um.BasePresenter;
import com.android.um.BaseView;

public interface ClinicsMapContract {

    interface View extends BaseView<ClinicsMapContract.Presenter> {

    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();

    }

}
