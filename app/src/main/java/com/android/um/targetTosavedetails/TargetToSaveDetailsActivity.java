package com.android.um.targetTosavedetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.android.um.BaseActivity;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TargetToSaveDetailsActivity extends BaseActivity implements TargetToSaveDetailsContract.View {

    TargetToSaveDetailsContract.Presenter mPresenter;
    @BindView(R.id.tv_target_save_amount)
    TextView tvTargetSaveAmount;
    @BindView(R.id.tv_day_amount)
    TextView tvDayAmount;
    @BindView(R.id.tv_week_amount)
    TextView tvWeekAmount;
    @BindView(R.id.tv_month_amount)
    TextView tvMonthAmount;
    @BindView(R.id.tv_year_amount)
    TextView tvYearAmount;
    @BindView(R.id.save_btn)
    Button saveBtn;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectTargetToSaveDetailsPresenter(this);
        setLocale(mPresenter.getLanguage(), R.layout.target_save_details_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mPresenter.start(getIntent().getExtras());
    }

    @Override
    public void showAmounts(double totalAmount) {
        tvTargetSaveAmount.setText("" + totalAmount);
        tvDayAmount.setText("" + (totalAmount / 365));
        tvWeekAmount.setText("" + (totalAmount / 52));
        tvMonthAmount.setText("" + (totalAmount / 12));
        tvYearAmount.setText("" + (totalAmount));
    }

    @Override
    public void goToDashBoard() {
        hideLoading();
        finish();
    }

    @Override
    public void failedToSave(String error) {
        hideLoading();
        showMessage(this, error);
    }

    @Override
    public void setPresenter(TargetToSaveDetailsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading() {
        avi.show();
    }

    @Override
    public void hideLoading() {
        avi.hide();
    }

    @Override
    public void setLocale(String localeName, int layout) {
        super.setLocale(localeName, layout);
    }

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);
    }

    @OnClick(R.id.save_btn)
    public void onViewClicked() {
        showLoading();
        mPresenter.saveTarget();
    }
}
