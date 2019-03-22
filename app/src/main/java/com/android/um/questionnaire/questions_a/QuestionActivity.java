package com.android.um.questionnaire.questions_a;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import com.android.um.BaseActivity;
import com.android.um.Fragment1;
import com.android.um.Fragment2;
import com.android.um.Fragment3;
import com.android.um.Interface.OnNextQuestion;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.DataModels.options;
import com.android.um.PresenterInjector;
import com.android.um.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionActivity extends BaseActivity implements OnNextQuestion, QuestionContract.View {
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    public static OnNextQuestion listener;
    LinkedHashMap<String, options> hmapSelectedOptions;
    LinkedHashMap<String, options> hmapAllOptions;
    HashMap<String, Integer> hmapSelectedOptionsPostion;

    QuestionContract.Presenter mPresenter;
    private ArrayList<Question> questions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.test);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        hmapSelectedOptions = new LinkedHashMap<>();
        hmapSelectedOptionsPostion = new HashMap<>();
        listener = this;
        PresenterInjector.injectQuestionPresenter(this);

        mPresenter.getQuestions();

    }

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);

    }

    @OnClick(R.id.button2)
    public void onViewClicked() {

        if (hmapSelectedOptions.size() == 0) {
            viewPager.setCurrentItem(0, true);
            showMessage(this, "Error");
            return;

        }
        for (int i = 0; i < questions.size(); i++) {
            try {
                if ( hmapSelectedOptions.get(questions.get(i).getDescription()).getValue() == null || hmapSelectedOptions.get(questions.get(i).getDescription()).getValue().length() == 0) {
                    viewPager.setCurrentItem(i, true);
                    showMessage(this, "Error");
                    return;
                }

            }
            catch (NullPointerException e)
            {
                viewPager.setCurrentItem(i, true);
                showMessage(this, "Error");
                return;
            }
            catch (IndexOutOfBoundsException e)
            {
                viewPager.setCurrentItem(i, true);
                showMessage(this, "Error");
                return;
            }
        }

    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Question> questions;

        public MyPagerAdapter(FragmentManager fm, ArrayList<Question> questions) {
            super(fm);
            this.questions = questions;
        }

        @Override
        public Fragment getItem(int pos) {

            switch (questions.get(pos).getType()) {
                case "RadioButton":
                    return Fragment1.newInstance(questions.get(pos).getDescription(), 0, questions.get(pos).getQustionOptions());
                case "EditText":
                    return Fragment2.newInstance(questions.get(pos).getDescription(), 1);
                case "Race":
                    return Fragment3.newInstance();
                default:
                    return Fragment1.newInstance(questions.get(pos).getDescription(), 0, questions.get(pos).getQustionOptions());
            }
        }

        @Override
        public int getCount() {
            return questions.size();
        }
    }

    @Override
    public void setSelectedOption(String key, options option, int position) {
        hmapSelectedOptions.put(key, option);
        hmapSelectedOptionsPostion.put(key, position);
    }

    @Override
    public void getQuestions(ArrayList<Question> questions) {
        this.questions = questions;
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), questions);
        viewPager.setAdapter(adapter);

    }

    @Override
    public void failedToLoadQuestions(String error) {
        showMessage(this, error);
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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 1) {
            //no fragments left
            finish();
        } else {
            super.onBackPressed();
        }

    }
}
