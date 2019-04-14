package com.android.um.addmotivationmessage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.android.um.BaseActivity;
import com.android.um.Model.DataModels.MotivationMessageModel;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.adapter.AddMotivationMessagesAdapter;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import butterknife.BindView;

public class AddMotivationMessageActivity extends BaseActivity implements AddMotivationMessagesContract.View {

    AddMotivationMessagesContract.Presenter mPresenter;
    @BindView(R.id.image_view)
    CircularImageView imageView;
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.et_message)
    EditText etMessage;

    ArrayList<String> messages=new ArrayList<>();
    AddMotivationMessagesAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectAddMotivationMessagesPresenter(this);
        setLocale(mPresenter.getLanguage(), R.layout.add_message_activity);
        super.onCreate(savedInstanceState);
        init();
    }

    public void init()
    {
        mAdapter=new AddMotivationMessagesAdapter(messages);

        rvMessage.setLayoutManager(new LinearLayoutManager(this));
        rvMessage.setAdapter(mAdapter);

        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0)
                    etMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.send_message_btn, 0);
                else
                    etMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        });
        etMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;


                if (etMessage.getCompoundDrawables()[DRAWABLE_RIGHT]!=null)
                {
                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() >= (etMessage.getRight() - etMessage.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                           mPresenter.addMessage(etMessage.getEditableText().toString());
                            return true;
                        }
                    }
                }

                return false;
            }
        });
    }
    @Override
    public void addMessageSuccess(String message) {
        messages.add(message);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addMessageFailed(String error) {
        showMessage(this,error);
    }

    @Override
    public void setPresenter(AddMotivationMessagesContract.Presenter presenter) {
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
}
