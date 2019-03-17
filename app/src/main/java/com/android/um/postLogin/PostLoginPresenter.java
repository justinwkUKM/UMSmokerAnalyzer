package com.android.um.postLogin;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.um.Interface.DataCallBack;
import com.android.um.Model.DataHandler;
import com.android.um.Model.DataHandlerInstance;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.SharedPrefsManager;

import java.util.ArrayList;

public class PostLoginPresenter implements PostLoginContract.Presenter {

    private PostLoginContract.View mView;
    private DataHandler mDataHandler;

    public PostLoginPresenter(PostLoginContract.View view) {
        this.mView = view;
        this.mDataHandler = DataHandlerInstance.getInstance(SharedPrefsManager.getInstance(mView.getContext()));
        view.setPresenter(this);
    }

    @Override
    public void start(@Nullable Bundle extras) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean checkifLogged() {
        return false;
    }
}
