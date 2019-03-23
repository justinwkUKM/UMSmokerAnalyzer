package com.android.um.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.android.um.BaseActivity;
import com.android.um.GenderActivity;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.postLogin.PostLoginActivity;
import com.android.um.prelogin.PreLoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashScreenActivity extends BaseActivity implements SplashScreenContract.View{

    @BindView(R.id.next_btn)
    Button nextBtn;
    private SplashScreenContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.spalsh_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        PresenterInjector.injectSplashScreenPresenter(this);

        if (mPresenter.checkifLogged())
            handleLogged();
    }

    @Override
    public void handleLogged() {
        Intent intent=new Intent(this, PostLoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setPresenter(SplashScreenContract.Presenter presenter) {
        mPresenter = presenter;
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

    public void goToNextActivity() {
        Intent intent = new Intent(this, PreLoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.next_btn)
    public void onViewClicked() {
        goToNextActivity();
    }
}
