package com.android.um.addsmokediary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.um.BaseActivity;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.smokediary.SmokeDiaryActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddSmokeDiaryActivity extends BaseActivity implements AddSmokeDiaryContract.View {
    @BindView(R.id.ll_happy)
    LinearLayout llHappy;
    @BindView(R.id.ll_bored)
    LinearLayout llBored;
    @BindView(R.id.ll_lonely)
    LinearLayout llLonely;
    @BindView(R.id.ll_stressed)
    LinearLayout llStressed;

    ArrayList<LinearLayout> ll_list = new ArrayList<>();
    @BindView(R.id.ll_tv_happy)
    TextView llTvHappy;
    @BindView(R.id.ll_tv_bored)
    TextView llTvBored;
    @BindView(R.id.ll_tv_lonely)
    TextView llTvLonely;
    @BindView(R.id.ll_tv_stressed)
    TextView llTvStressed;

    ArrayList<TextView> tv_list = new ArrayList<>();

    String feelSelected = "";
    @BindView(R.id.carving_1)
    View carving1;
    @BindView(R.id.carving_2)
    View carving2;
    @BindView(R.id.carving_3)
    View carving3;
    @BindView(R.id.carving_4)
    View carving4;
    @BindView(R.id.carving_5)
    View carving5;
    @BindView(R.id.carving_6)
    View carving6;
    @BindView(R.id.carving_7)
    View carving7;
    @BindView(R.id.carving_8)
    View carving8;
    @BindView(R.id.carving_9)
    View carving9;
    @BindView(R.id.carving_10)
    View carving10;

    ArrayList<View> view_list = new ArrayList<>();

    int carvingsLevel = 0;
    @BindView(R.id.save_btn)
    Button saveBtn;
    AddSmokeDiaryContract.Presenter mPresenter;
    @BindView(R.id.rb_smoked)
    RadioButton rbSmoked;
    @BindView(R.id.rb_resisted)
    RadioButton rbResisted;

    String smoked="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectAddSmokeDiaryPresenter(this);
        setLocale(mPresenter.getLanguage(), R.layout.add_smoke_diary_activity);
        ButterKnife.bind(this);

        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {

        ll_list.add(llHappy);
        ll_list.add(llBored);
        ll_list.add(llLonely);
        ll_list.add(llStressed);

        tv_list.add(llTvHappy);
        tv_list.add(llTvBored);
        tv_list.add(llTvLonely);
        tv_list.add(llTvStressed);

        carving1.setTag(0);
        carving2.setTag(1);
        carving3.setTag(2);
        carving4.setTag(3);
        carving5.setTag(4);
        carving6.setTag(5);
        carving7.setTag(6);
        carving8.setTag(7);
        carving9.setTag(8);
        carving10.setTag(9);

        view_list.add(carving1);
        view_list.add(carving2);
        view_list.add(carving3);
        view_list.add(carving4);
        view_list.add(carving5);
        view_list.add(carving6);
        view_list.add(carving7);
        view_list.add(carving8);
        view_list.add(carving9);
        view_list.add(carving10);


    }

    @Override
    public void savingDiarySuccess() {
        Intent returnIntent = new Intent(this, SmokeDiaryActivity.class);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public void savingDiaryFailed(String error) {
        showMessage(this, error);
    }

    @Override
    public void setLocale(String localeName, int layout) {
        super.setLocale(localeName, layout);
    }

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);
    }

    @Override
    public void addSmokeDiary() {
        mPresenter.addSmokeDiary(smoked,carvingsLevel,feelSelected);
    }

    @Override
    public void setPresenter(AddSmokeDiaryContract.Presenter presenter) {
        mPresenter = presenter;
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

    public void unselectAll() {
        for (LinearLayout ll : ll_list) {
            ll.setBackground(getResources().getDrawable(R.drawable.rounder_border));
        }

        for (TextView tv : tv_list) {
            tv.setTextColor(getResources().getColor(R.color.black));
        }
    }

    @OnClick({R.id.ll_happy, R.id.ll_bored, R.id.ll_lonely, R.id.ll_stressed})
    public void onViewClicked(View view) {
        unselectAll();
        switch (view.getId()) {
            case R.id.ll_happy:
                llHappy.setBackground(getResources().getDrawable(R.drawable.selected_feeling_bg));
                llTvHappy.setTextColor(getResources().getColor(R.color.white));
                feelSelected = "Happy";
                break;
            case R.id.ll_bored:
                llBored.setBackground(getResources().getDrawable(R.drawable.selected_feeling_bg));
                llTvBored.setTextColor(getResources().getColor(R.color.white));
                feelSelected = "Bored";
                break;
            case R.id.ll_lonely:
                llLonely.setBackground(getResources().getDrawable(R.drawable.selected_feeling_bg));
                llTvLonely.setTextColor(getResources().getColor(R.color.white));
                feelSelected = "Lonely";
                break;
            case R.id.ll_stressed:
                llStressed.setBackground(getResources().getDrawable(R.drawable.selected_feeling_bg));
                llTvStressed.setTextColor(getResources().getColor(R.color.white));
                feelSelected = "Stressed";
                break;
        }
    }


    public void selectCarvings(int position) {
        carvingsLevel = position + 1;
        for (View v : view_list)
            v.setBackground(getResources().getDrawable(R.drawable.unselected_carvings));

        for (int i = 0; i <= position; i++) {
            view_list.get(i).setBackground(getResources().getDrawable(R.drawable.selected_carvings));
        }
    }

    @OnClick({R.id.carving_1, R.id.carving_2, R.id.carving_3, R.id.carving_4, R.id.carving_5, R.id.carving_6, R.id.carving_7, R.id.carving_8, R.id.carving_9, R.id.carving_10})
    public void onCarvingsViewClicked(View view) {
        switch (view.getId()) {
            case R.id.carving_1:
                selectCarvings((int) carving1.getTag());
                break;
            case R.id.carving_2:
                selectCarvings((int) carving2.getTag());
                break;
            case R.id.carving_3:
                selectCarvings((int) carving3.getTag());
                break;
            case R.id.carving_4:
                selectCarvings((int) carving4.getTag());
                break;
            case R.id.carving_5:
                selectCarvings((int) carving5.getTag());
                break;
            case R.id.carving_6:
                selectCarvings((int) carving6.getTag());
                break;
            case R.id.carving_7:
                selectCarvings((int) carving7.getTag());
                break;
            case R.id.carving_8:
                selectCarvings((int) carving8.getTag());
                break;
            case R.id.carving_9:
                selectCarvings((int) carving9.getTag());
                break;
            case R.id.carving_10:
                selectCarvings((int) carving10.getTag());
                break;
        }
    }

    @OnClick(R.id.save_btn)
    public void onViewClicked() {
        addSmokeDiary();
    }

    @OnClick({R.id.rb_smoked, R.id.rb_resisted})
    public void onRadioButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_smoked:
                smoked="I Smoked";
                break;
            case R.id.rb_resisted:
                smoked="I Resisted";
                break;
        }
    }
}
