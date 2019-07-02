package com.android.um.clinicsmap;

import com.android.um.BasePresenter;
import com.android.um.BaseView;
import com.android.um.Model.DataModels.KliniksModel;

import java.util.ArrayList;

public interface ClinicsMapContract {

    interface View extends BaseView<ClinicsMapContract.Presenter> {
        void setKliniksLocations(ArrayList<KliniksModel> kliniksLocations);
        void failedGetLocations(String error);

    }

    interface Presenter extends BasePresenter {
        @Override
        String getLanguage();

        void getKliniks();

    }

}
