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
import com.android.um.terms.TermsActivity;
import com.android.um.signin.SigninActivity;
import com.android.um.signup.SignupActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreLoginActivity extends BaseActivity implements PreLoginContract.View {


    @BindView(R.id.sign_btn)
    Button signBtn;
    @BindView(R.id.tv_create_account)
    TextView tvCreateAccount;

    private PreLoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectPreLoginPresenter(this);
        setLocale(mPresenter.getLanguage(), R.layout.prelogin_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);


    }

    @Override
    public void goToSigninPage() {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToSignupPage() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToTermsPage() {
        Intent intent = new Intent(this, TermsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showTermsError() {
        Intent intent=new Intent(this,TermsActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPresenter(PreLoginContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading() { }

    @Override
    public void hideLoading() { }

    @OnClick({R.id.sign_btn, R.id.tv_create_account,R.id.terms_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sign_btn:
                mPresenter.goToSigninPage();
                break;
            case R.id.tv_create_account:
                mPresenter.goToSignupPage();
                break;
            case R.id.terms_text:
                goToTermsPage();
                break;
        }
    }


}
