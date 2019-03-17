package com.android.um.postLogin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.um.BaseActivity;
import com.android.um.Model.DataModels.User;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.signin.SigninActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostLoginActivity extends BaseActivity implements PostLoginContract.View {

    PostLoginContract.Presenter mPresenter;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.logout_btn)
    Button logoutBtn;
    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.postlogin_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        PresenterInjector.injectPostLoginPresenter(this);
        User user = mPresenter.getLoggedUser();
        tvName.setText("Hello,"+user.getUsername());
    }


    @Override
    public void handleLogOut() {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setPresenter(PostLoginContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public void showConfirmationDialog()
    {
        new AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Do you really want to Log Out?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        mPresenter.LogOut();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);
    }

    void goToQuestionsPage() {
        //Intent intent=new Intent(this, QuestionActivity.class);
        // startActivity(intent);
    }

    @OnClick({R.id.login_btn, R.id.logout_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                goToQuestionsPage();
                break;
            case R.id.logout_btn:
                showConfirmationDialog();
                break;

        }

    }

}
