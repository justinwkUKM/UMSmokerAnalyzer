package com.android.um.questionnaire.questions_a;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.um.BaseActivity;
import com.android.um.Interface.OnNextQuestion;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.DataModels.options;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.adapter.OptionsListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionActivity extends BaseActivity implements QuestionContract.View, OnNextQuestion {
    QuestionContract.Presenter mPresenter;
    @BindView(R.id.tv_question)
    TextView tvQuestion;
    @BindView(R.id.options_list)
    RecyclerView optionsList;
    public static OnNextQuestion questionListener;
    @BindView(R.id.next_question)
    Button nextQuestion;
    ArrayList<com.android.um.Model.DataModels.options> options;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private int questionIndex = 0;
    private ArrayList<Question> questions;
    OptionsListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        PresenterInjector.injectQuestionPresenter(this);
        showLoading();
        mPresenter.getQuestions();
    }

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);
    }

    @Override
    public void getQuestions(ArrayList<Question> questions) {
        hideLoading();
        this.questions = questions;
        Question question=questions.get(questionIndex);
        tvQuestion.setText(question.getId()+"."+question.getDescription());
        options = question.getQustionOptions();
        adapter = new OptionsListAdapter(options);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        optionsList.setLayoutManager(mLayoutManager);
        optionsList.setItemAnimator(new DefaultItemAnimator());
        optionsList.setAdapter(adapter);
    }

    public void showQuestion(int questionIndex) {
        Question question=questions.get(questionIndex);
        tvQuestion.setText(question.getId()+"."+question.getDescription());
        options = question.getQustionOptions();
        adapter = new OptionsListAdapter(options);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        optionsList.setLayoutManager(mLayoutManager);
        optionsList.setItemAnimator(new DefaultItemAnimator());
        optionsList.setAdapter(adapter);
    }

    @Override
    public void failedToLoadQuestions(String error) {

    }

    @Override
    public options getSelectedOption() {
        return questionListener.getSelectedOption();
    }

    @Override
    public void setPresenter(QuestionContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.next_question)
    public void onViewClicked() {

        //look inside the questions options for the option that matches the selected option the reason to do this is to be able to change
        //the value of this option so while navigating the value selected will be saved

        ArrayList<options> optionsTemp=questions.get(questionIndex).getQustionOptions();
        optionsTemp.set(getSelectedOption().getIndex(),getSelectedOption());
        questions.get(questionIndex).setQustionOptions(optionsTemp);

        questionIndex++;
        if (questionIndex >= questions.size()) {
            //go to next screen
        } else {
            showQuestion(questionIndex);
        }
    }

    @Override
    public void onBackPressed() {
        questionIndex--;
        if (questionIndex>=0)
        {
            showQuestion(questionIndex);
        }
    }
}
