package com.android.um.terms;

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
import com.android.um.prelogin.PreLoginActivity;
import com.android.um.signin.SigninActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermsActivity extends BaseActivity implements TermsContract.View {
    @BindView(R.id.text_term1)
    TextView textTerm1;
    @BindView(R.id.text_term2)
    TextView textTerm2;
    @BindView(R.id.text_term3)
    TextView textTerm3;
    @BindView(R.id.accept_btn)
    Button acceptBtn;
    @BindView(R.id.decline_btn)
    Button declineBtn;
    TermsContract.Presenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectTermsPresenter(this);
        setLocale(mPresenter.getLanguage(),R.layout.terms_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void goToPreLogin() {
        Intent intent=new Intent(this,PreLoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToLogin() {
        Intent intent=new Intent(this, SigninActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);
    }


    @Override
    public void setPresenter(TermsContract.Presenter presenter) {
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

    @OnClick({R.id.accept_btn, R.id.decline_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.accept_btn:
                mPresenter.saveTermAcceptence(true);
                break;
            case R.id.decline_btn:
                mPresenter.saveTermAcceptence(false);
                break;
        }
    }
}
