package com.android.um.motivation_messages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.um.BaseActivity;
import com.android.um.Model.DataModels.MotivationMessageModel;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.adapter.MotivationMessagesAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MotivationMessagesActivity extends BaseActivity implements MotivationMessagesContract.View {

    MotivationMessagesContract.Presenter mPresenter;
    @BindView(R.id.rv_motivation_messages)
    RecyclerView rvMotivationMessages;
    @BindView(R.id.add_message_btn)
    FloatingActionButton addMessageBtn;

    MotivationMessagesAdapter mAdapter;
    ArrayList<MotivationMessageModel> messages=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectMotivationMessagesPresenter(this);
        setLocale(mPresenter.getLanguage(), R.layout.motivation_messages_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        mPresenter.getMessages();
        mAdapter=new MotivationMessagesAdapter(messages);
        rvMotivationMessages.setLayoutManager(new LinearLayoutManager(this));
        rvMotivationMessages.setAdapter(mAdapter);
    }

    @Override
    public void showMessages(ArrayList<MotivationMessageModel> messages) {
        this.messages=messages;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(MotivationMessagesContract.Presenter presenter) {
        this.mPresenter = presenter;
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
    public void setLocale(String localeName, int layout) {
        super.setLocale(localeName, layout);
    }

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);
    }

    void addMotivtaionMessage()
    {
        //Intent intent=new Intent(this,)
    }

    @OnClick(R.id.add_message_btn)
    public void onViewClicked() {
        addMotivtaionMessage();
    }
}
