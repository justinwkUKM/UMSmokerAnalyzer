package com.android.um.unlockfeature;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.android.um.BaseActivity;
import com.android.um.PresenterInjector;
import com.android.um.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UnlockFeatureActivity extends BaseActivity implements UnlockFeatureContract.View {

    UnlockFeatureContract.Presenter mPresenter;
    @BindView(R.id.tv_unlock_feature)
    TextView tvUnlockFeature;
    @BindView(R.id.tv_feature)
    TextView tvFeature;
    @BindView(R.id.btn_collect_points)
    Button btnCollectPoints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectUnlockFeaturePresenter(this);
        setLocale(mPresenter.getLanguage(), R.layout.unlock_feature_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        mPresenter.start(getIntent().getExtras());
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
    public void setFeature(String feature) {
        tvFeature.setText(feature);
    }

    @Override
    public void setPresenter(UnlockFeatureContract.Presenter presenter) {
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

    @OnClick(R.id.btn_collect_points)
    public void onViewClicked() {
    }
}
