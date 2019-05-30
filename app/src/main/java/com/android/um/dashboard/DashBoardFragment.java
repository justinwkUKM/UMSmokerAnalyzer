package com.android.um.dashboard;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.smokediary.SmokeDiaryActivity;
import com.android.um.targetTosave.TargetToSaveActivity;
import com.android.um.unlockfeature.UnlockFeatureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashBoardFragment extends Fragment implements DashboardContract.View {

    @BindView(R.id.tv_money_saved)
    TextView tvMoneySaved;
    @BindView(R.id.tv_target_save)
    TextView tvTargetSave;
    @BindView(R.id.tv_target_save_amount)
    TextView tvTargetSaveAmount;
    @BindView(R.id.btn_smoke_diary)
    FloatingActionButton btn_smoke_diary;

    DashboardContract.Presenter mPresenter;
    @BindView(R.id.tv_smoke_free)
    TextView tvSmokeFree;
    @BindView(R.id.tv_smoke_free_time)
    TextView tvSmokeFreeTime;
    @BindView(R.id.tv_unlock_achievement_feature)
    TextView tvUnlockAchievementFeature;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.dashboard_fragment, container, false);
        ButterKnife.bind(this, rootView);
        PresenterInjector.injectDashboardPresenter(this);
        mPresenter.getTargetToSave();

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter!=null)
            mPresenter.startSmokeFreeTime();
    }

    @Override
    public void showTargetToSave(String total) {
        tvTargetSaveAmount.setText("RM" + total);
    }

    public static DashBoardFragment newInstance() {
        DashBoardFragment f = new DashBoardFragment();
        return f;
    }

    public void goToTargetToSave() {
        Intent intent = new Intent(getActivity(), TargetToSaveActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSmokeFreeTime() {
        tvSmokeFree.setVisibility(View.VISIBLE);
    }

    @Override
    public void unlockFeature() {
        tvUnlockAchievementFeature.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateSmokeFreeTimer(long seconds,long minutes,long hours) {
        tvSmokeFreeTime.setText(hours+"H "+minutes+"M "+seconds+"S");
    }

    public void goToSmokeDiary() {
        Intent intent = new Intent(getActivity(), SmokeDiaryActivity.class);
        startActivity(intent);

    }

    public void goToUnlockFeature()
    {
        Intent intent = new Intent(getActivity(), UnlockFeatureActivity.class);
        intent.putExtra("FEATURE","Smoke Free 24 Hours");
        startActivity(intent);

    }

    @OnClick({R.id.tv_target_save, R.id.btn_smoke_diary,R.id.tv_unlock_achievement_feature})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_target_save:
                goToTargetToSave();
                break;
            case R.id.btn_smoke_diary:
                goToSmokeDiary();
                break;
            case R.id.tv_unlock_achievement_feature:
                goToUnlockFeature();
                break;
        }

    }


    @Override
    public void setPresenter(DashboardContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.stopTimer();
    }
}
