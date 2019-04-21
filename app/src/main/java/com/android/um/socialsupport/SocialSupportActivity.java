package com.android.um.socialsupport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.um.BaseActivity;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.premotivationmessage.PreMotivationMessageActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SocialSupportActivity extends BaseActivity implements SocialSupportContract.View {

    SocialSupportContract.Presenter mPresenter;
    @BindView(R.id.tv_quit_love)
    TextView tvQuitLove;
    @BindView(R.id.tv_quit_love_desc)
    TextView tvQuitLoveDesc;
    @BindView(R.id.tv_unlock_feature)
    TextView tvUnlockFeature;
    @BindView(R.id.tv_social_support)
    TextView tvSocialSupport;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.il_et_username)
    TextInputLayout ilEtUsername;
    @BindView(R.id.next_btn)
    Button nextBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectSocialSupportPresenter(this);
        setLocale(mPresenter.getLanguage(), R.layout.social_support_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
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
    public void setPresenter(SocialSupportContract.Presenter presenter) {
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

    public void goToPreMotivationMessage()
    {
        Intent intent=new Intent(this, PreMotivationMessageActivity.class);
        intent.putExtra("MOTIVATOR_NAME",etName.getEditableText().toString());
        startActivity(intent);
    }
    @OnClick(R.id.next_btn)
    public void onViewClicked() {
        if (etName.getEditableText().toString().length()!=0)
            goToPreMotivationMessage();
        else
            etName.setError(getResources().getString(R.string.text_empty_error));
    }
}
