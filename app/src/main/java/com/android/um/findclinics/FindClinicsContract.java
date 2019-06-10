package com.android.um.findclinics;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.User;

public interface FindClinicsContract {

    interface View extends BaseView<FindClinicsContract.Presenter> {


    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();

    }

}
