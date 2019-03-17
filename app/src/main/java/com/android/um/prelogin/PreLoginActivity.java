package com.android.um.prelogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.um.BaseActivity;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.postLogin.PostLoginActivity;
import com.android.um.signin.SigninActivity;
import com.android.um.signup.SignupActivity;
import com.android.um.splashscreen.SplashScreenContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreLoginActivity extends BaseActivity implements SplashScreenContract.View{


    @BindView(R.id.sign_btn)
    Button signBtn;
    @BindView(R.id.tv_create_account)
    TextView tvCreateAccount;
    private SplashScreenContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_register);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void handleLogged() {
        Intent intent=new Intent(this, PostLoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToSignin() {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
    }

    public void goToSignup() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.sign_btn, R.id.tv_create_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_btn:
                goToSignin();
                break;
            case R.id.tv_create_account:
                goToSignup();
                break;
        }
    }

    @Override
    public void setPresenter(SplashScreenContract.Presenter presenter) {
        this.mPresenter = presenter;
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
}
