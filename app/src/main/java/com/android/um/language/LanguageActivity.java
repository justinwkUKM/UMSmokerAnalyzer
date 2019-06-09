package com.android.um.language;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;


import com.android.um.BaseActivity;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.prelogin.PreLoginActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanguageActivity extends BaseActivity implements LanguageContract.View {

    LanguageContract.Presenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        PresenterInjector.injectLanguagePresenter(this);
    }

    @Override
    public void goToPreLogin() {
        Intent intent = new Intent(this, PreLoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(LanguageContract.Presenter presenter) {
        this.mPresenter=presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }


    @OnClick({R.id.english_btn, R.id.bahasa_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.english_btn:
                mPresenter.saveLanguage("EN");
                break;
            case R.id.bahasa_btn:
               mPresenter.saveLanguage("MY");
                break;
        }
    }
}
