package com.android.um.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.um.BaseActivity;
import com.android.um.MainActivity;
import com.android.um.Model.DataModels.Question;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.language.LanguageActivity;
import com.android.um.prelogin.PreLoginActivity;
import com.android.um.questions.QuestionsActivity;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashScreenActivity extends BaseActivity implements SplashScreenContract.View {


    @BindView(R.id.container)
    ConstraintLayout container;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.next_btn)
    Button nextBtn;

    private SplashScreenContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.spalsh_activity);
        ButterKnife.bind(this);
        PresenterInjector.injectSplashScreenPresenter(this);
        if (mPresenter.checkifLogged()) {
            handleLogged();
        }
        else
        {
            if (mPresenter.getLanguage()!=null && mPresenter.getLanguage().length()>0)
                goToPreLoginActivity();
            else
                container.setVisibility(View.VISIBLE);
        }


        super.onCreate(savedInstanceState);
    }


    @Override
    public void goToDemographicQuestions() {
        hideLoading();
        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra("category","demographicQuestions");
        startActivity(intent);
        finish();
    }

    @Override
    public void goToMainScreen() {
        hideLoading();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void handleLogged() {
        showLoading();
        mPresenter.showDemographicQuestions();
    }

    @Override
    public void goToLevelAddictionQuestions() {
        hideLoading();
        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra("category","leveladdictionQuestions");
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
        avi.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        avi.setVisibility(View.GONE);
    }

    public void goToNextActivity() {
        Intent intent = new Intent(this, LanguageActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToPreLoginActivity()
    {
        Intent intent = new Intent(this, PreLoginActivity.class);
        startActivity(intent);
        finish();

    }

    @OnClick(R.id.next_btn)
    public void onViewClicked() {
        goToNextActivity();
    }
}
