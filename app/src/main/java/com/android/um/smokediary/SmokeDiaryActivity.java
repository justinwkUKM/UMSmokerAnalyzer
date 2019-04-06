package com.android.um.smokediary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.um.BaseActivity;
import com.android.um.Model.DataModels.SmokeDiaryModel;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.adapter.SmokeDiaryAdapter;
import com.android.um.addsmokediary.AddSmokeDiaryActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SmokeDiaryActivity extends BaseActivity implements SmokeDiaryContract.View{

    @BindView(R.id.add_smoke_diary)
    FloatingActionButton addSmokeDiary;
    @BindView(R.id.rv_smoke_diary)
    RecyclerView rvSmokeDiary;
    ArrayList<SmokeDiaryModel> list;
    private SmokeDiaryAdapter adapter;
    SmokeDiaryContract.Presenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectSmokeDiaryPresenter(this);
        setLocale(mPresenter.getLanguage(),R.layout.smoke_diary_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        init();
        mPresenter.getSmokeDiary();
    }

    public void init()
    {
        list=new ArrayList<>();
        adapter=new SmokeDiaryAdapter(list);
        rvSmokeDiary.setLayoutManager(new LinearLayoutManager(this));
        rvSmokeDiary.setAdapter(adapter);

    }
    @Override
    public void setLocale(String localeName, int layout) {
        super.setLocale(localeName, layout);
    }

    @Override
    public void showDiary(ArrayList<SmokeDiaryModel> smokeDiaryModels) {
        list.clear();
        list.addAll(smokeDiaryModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDiaryFailed(String error) {
        showMessage(this,error);
    }

    @Override
    public void setPresenter(SmokeDiaryContract.Presenter presenter) {
        mPresenter=presenter;
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

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void addSmokeDiary()
    {
        Intent intent=new Intent(this, AddSmokeDiaryActivity.class);
        startActivityForResult(intent,1010);
    }
    @OnClick(R.id.add_smoke_diary)
    public void onViewClicked() {
        addSmokeDiary();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1010) {
            if(resultCode == Activity.RESULT_OK){
                mPresenter.getSmokeDiary();
            }
        }
    }//onActivityResult

}
