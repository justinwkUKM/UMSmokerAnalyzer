package com.android.um.postLogin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.android.um.BaseActivity;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.questionnaire.questions_a.QuestionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostLoginActivity extends BaseActivity implements PostLoginContract.View {

    PostLoginContract.Presenter mPresenter;
    @BindView(R.id.login_btn)
    Button loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.postlogin_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        PresenterInjector.injectPostLoginPresenter(this);

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

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);
    }

    void goToQuestionsPage()
    {
        //Intent intent=new Intent(this, QuestionActivity.class);
       // startActivity(intent);
    }
    @OnClick(R.id.login_btn)
    public void onViewClicked() {
        goToQuestionsPage();
    }
}
