package com.android.um.resetpassword;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.android.um.BaseActivity;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.signin.SigninContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity implements ForgetPasswordContract.View {

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btn_reset_password)
    Button btnResetPassword;

    ForgetPasswordContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.forget_password_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        PresenterInjector.injectProfilePresenter(this);

    }

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);
    }

    @OnClick(R.id.btn_reset_password)
    public void onViewClicked() {
        mPresenter.resetPassword(etEmail.getEditableText().toString());
    }

    @Override
    public void resetResult(String message) {
        showMessage(this,message);
    }

    @Override
    public void setPresenter(ForgetPasswordContract.Presenter presenter) {
            this.mPresenter=presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public Context getContext() {
        return this;
    }

}
