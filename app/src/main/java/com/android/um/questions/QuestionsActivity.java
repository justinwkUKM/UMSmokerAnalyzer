package com.android.um.questions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.android.um.BaseActivity;
import com.android.um.Interface.OnNextQuestion;
import com.android.um.Model.DataModels.AnsweredQuestion;
import com.android.um.main.MainActivity;
import com.android.um.Model.DataModels.Question;
import com.android.um.Model.DataModels.options;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.postLogin.PostLoginActivity;
import com.android.um.signup.SignupActivity;
import com.rd.PageIndicatorView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionsActivity extends BaseActivity implements OnNextQuestion, QuestionsContract.View {
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    public static OnNextQuestion listener;
    //this map to save the question with its selected value to be saved in firebase
    LinkedHashMap<String, AnsweredQuestion> answeredQuestions;
    LinkedHashMap<String, options> hmapSelectedOptions;
    LinkedHashMap<Question, options> hmapAllQuestions;
    HashMap<String, Integer> hmapSelectedOptionsPostion;

    QuestionsContract.Presenter mPresenter;
    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;
    @BindView(R.id.avi)
    AVLoadingIndicatorView loadingIndicatorView;
    private ArrayList<Question> questions;

    private int mPosition = 0;
    private ArrayList<AnsweredQuestion> answers;
    String category = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectDemographicQuestionsPresenter(this);
        setLocale(mPresenter.getLanguage(), R.layout.activity_question);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        hmapSelectedOptions = new LinkedHashMap<>();
        hmapSelectedOptionsPostion = new HashMap<>();
        listener = this;
        category = getIntent().getExtras().getString("category");
        mPresenter.getQuestions(category);
        init();
    }

    private void init() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position) {
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);

    }

    @Override
    public void failedToSaveQuestions(String error) {
        showMessage(this, error);
    }

    @OnClick(R.id.button2)
    public void onViewClicked() {

        if (hmapSelectedOptions.size() == 0) {
            viewPager.setCurrentItem(0, true);
            showMessage(this, "Error");
            return;

        }
        for (int i = 0; i < questions.size(); i++) {
            if (hmapSelectedOptions.get(questions.get(i).getDescription()) == null) {
                viewPager.setCurrentItem(i, true);
                showMessage(this, "Mandatory choice");
                return;
            } else if (hmapSelectedOptions.get(questions.get(i).getDescription()).getValue() == null || hmapSelectedOptions.get(questions.get(i).getDescription()).getValue().length() == 0
                    || hmapSelectedOptions.get(questions.get(i).getDescription()).getValue().equals(" ")) {
                viewPager.setCurrentItem(i, true);
                showMessage(this, "Mandatory choice");
                return;
            }
        }
        answers = new ArrayList<>();
        for (Map.Entry<String, AnsweredQuestion> map : answeredQuestions.entrySet()) {
            AnsweredQuestion question=new AnsweredQuestion();
            question.setDescription(map.getKey());
            question.setCategory(map.getValue().getCategory());
            question.setId(map.getValue().getId());
            question.setIndex(map.getValue().getIndex());
            question.setSelectedOptions(map.getValue().getSelectedOptions());
            answers.add(question);
        }
        if (mPresenter.checkifLogged()) {
            mPresenter.saveAnsweredQuestions(category, answers);
        } else if (category.equals("demographicQuestions"))
            goBackToSignUp();
    }

    @Override
    public void SuccessSaveQuestions() {
        mPresenter.setQuestionsAnswered(category);
        if (category.equals("demographicQuestions"))
            goToPostScreen();
        else if (category.startsWith("videoQuestions"))
        {
            if (category.endsWith("5"))
                goToInfo();
            else
                goToMindfulnessVideos(category);
        }

        else
            goToMainScreen();
    }

    @Override
    public void goToQuestions(String category) {
        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
        finish();
    }

    public void goToPostScreen() {
        Intent intent = new Intent(this, PostLoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToMindfulnessVideos(String category) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("FRAGMENT","MINFFULNESS_FRAGMENT");
        intent.putExtra("INDEX",category.substring(category.length()-1));
        startActivity(intent);
        finish();
    }

    public void goToInfo() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("FRAGMENT","Info_FRAGMENT");
        startActivity(intent);
        finish();
    }


    private void goBackToSignUp() {
        Intent intent = new Intent(this, SignupActivity.class);
        intent.putParcelableArrayListExtra("QUESTIONS", answers);
        setResult(Activity.RESULT_OK, intent);
        finish();
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
                    return RadioButtonFragment.newInstance(questions.get(pos).getDescription(), questions.get(pos).getId()-1, questions.get(pos).getQustionOptions());
//                case "EditText":
//                    return AgeFragment.newInstance(questions.get(pos).getDescription(), 1, questions.get(pos).getQustionOptions());
                case "Race":
                    return RaceFragment.newInstance(questions.get(pos).getDescription(), 2, questions.get(pos).getQustionOptions());
                default:
                    return RadioButtonFragment.newInstance(questions.get(pos).getDescription(), questions.get(pos).getId()-1, questions.get(pos).getQustionOptions());
            }
        }

        @Override
        public int getCount() {
            return questions.size();
        }
    }

    @Override
    public void setSelectedOption(String key, options option, int position) {

        this.mPosition = position;
        hmapSelectedOptions.put(key, option);
        //hmapSelectedOptionsPostion.put(key, position);

        AnsweredQuestion answeredQuestion = new AnsweredQuestion();
        answeredQuestion.AddAnsweredQuestion(questions.get(position), option);
        answeredQuestions.put(key, answeredQuestion);
        if ((option.getValue() != null) && (option.getValue().length() > 0) && (!option.getValue().equals(" "))) {
            if (hmapSelectedOptions.size() == questions.size())
                button2.setVisibility(View.VISIBLE);
            else
                button2.setVisibility(View.GONE);
        }

//        if (option.getValue()!=null || option.getValue().length()>0 || option.getValue().equals(" "))
//        {
//         new Handler().postDelayed(new Runnable() {
//             @Override
//             public void run() {
//                 if (mPosition<=questions.size())
//                     viewPager.setCurrentItem(mPosition+1);
//             }
//         },500);
//        }
    }

    @Override
    public void getQuestions(ArrayList<Question> questions) {
        this.questions = questions;
        this.answeredQuestions = new LinkedHashMap<>(questions.size());
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), questions);
        viewPager.setAdapter(adapter);
        pageIndicatorView.setVisibility(View.VISIBLE);
        pageIndicatorView.setCount(questions.size());
        pageIndicatorView.setSelection(0);
    }

    @Override
    public void failedToLoadQuestions(String error) {
        showMessage(this, error);
    }

    @Override
    public void setPresenter(QuestionsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void showLoading() {
        loadingIndicatorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingIndicatorView.setVisibility(View.GONE);
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
