package com.android.um.addmotivationmessage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.um.BaseActivity;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.adapter.AddMotivationMessagesAdapter;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMotivationMessageActivity extends BaseActivity implements AddMotivationMessagesContract.View {

    AddMotivationMessagesContract.Presenter mPresenter;
    @BindView(R.id.image_view)
    CircularImageView imageView;
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.et_message)
    EditText etMessage;

    ArrayList<String> messages = new ArrayList<>();
    AddMotivationMessagesAdapter mAdapter;
    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectAddMotivationMessagesPresenter(this);
        setLocale(mPresenter.getLanguage(), R.layout.add_message_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mPresenter.start(getIntent().getExtras());
        init();
    }

    @Override
    public void showMotivator(String name, String url) {
        tvName.setText(name);
        byte[] decodedString = Base64.decode(url, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
    }


    public Bitmap getImageBitmapByPath(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path)); // 2nd line
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
            return bitmap;
        } catch (IOException e) {
            showMessage(this, "Image Not Found");
            return null;
        }


    }

    public Bitmap getImageBitmapByUri(Uri path) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);

            return bitmap;
        } catch (IOException e) {
            showMessage(this, "Image Not Found");
            return null;
        }
    }

    public void init() {
        mAdapter = new AddMotivationMessagesAdapter(messages);

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
                if (s.length() > 0)
                    etMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.send_message_btn, 0);
                else
                    etMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        });
        etMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;


                if (etMessage.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (etMessage.getRight() - etMessage.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            mPresenter.addMessage(etMessage.getEditableText().toString());
                            etMessage.setText("");
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
        showMessage(this, error);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.deleteImageUrl();
    }
}
