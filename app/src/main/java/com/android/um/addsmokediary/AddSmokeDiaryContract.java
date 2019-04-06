package com.android.um.addsmokediary;

import com.android.um.BasePresenter;
import com.android.um.BaseView;

public interface AddSmokeDiaryContract {

    interface View extends BaseView<AddSmokeDiaryContract.Presenter> {
        void addSmokeDiary();
        void savingDiarySuccess();
        void savingDiaryFailed(String error);
    }

    interface Presenter extends BasePresenter {


        void addSmokeDiary(String smoked,int cravings,String severity);
    }

}
