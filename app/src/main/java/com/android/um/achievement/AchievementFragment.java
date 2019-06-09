package com.android.um.achievement;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.um.Model.DataModels.PersonalityModel;
import com.android.um.PresenterInjector;
import com.android.um.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AchievementFragment extends Fragment implements AchievementContract.View {

    AchievementContract.Presenter mPresenter;
    @BindView(R.id.tv_feature_title)
    TextView tvFeatureTitle;
    @BindView(R.id.tv_feature_desc)
    TextView tvFeatureDesc;
    @BindView(R.id.tv_unlock_feature)
    TextView tvUnlockFeature;
    @BindView(R.id.unlock_feature)
    RelativeLayout unlockFeature;
    @BindView(R.id.progress_health)
    ProgressBar progressHealth;
    @BindView(R.id.progress_wllbeing)
    ProgressBar progressWllbeing;
    @BindView(R.id.progress_money)
    ProgressBar progressMoney;
    @BindView(R.id.progress_cigarettes)
    ProgressBar progressCigarettes;
    @BindView(R.id.progress_time)
    ProgressBar progressTime;
    @BindView(R.id.progress_point)
    ProgressBar progressPoint;
    @BindView(R.id.tv_health)
    TextView tvHealth;
    @BindView(R.id.tv_wllbeing)
    TextView tvWllbeing;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_cigarattes)
    TextView tvCigarattes;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_point)
    TextView tvPoint;

    ArrayList<TextView> titles=new ArrayList<>();
    ArrayList<ProgressBar> scores=new ArrayList<>();

    public static AchievementFragment newInstance() {
        AchievementFragment f = new AchievementFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.achievement_activity, container, false);
        PresenterInjector.injectAchievementPresenter(this);
        ButterKnife.bind(this, rootView);
        mPresenter.getPersonality();

        titles.add(tvHealth);
        titles.add(tvWllbeing);
        titles.add(tvMoney);
        titles.add(tvCigarattes);
        titles.add(tvTime);
        titles.add(tvPoint);

        scores.add(progressHealth);
        scores.add(progressWllbeing);
        scores.add(progressMoney);
        scores.add(progressCigarettes);
        scores.add(progressTime);
        scores.add(progressPoint);

        return rootView;
    }

    @Override
    public void showPersonalityInfo(ArrayList<PersonalityModel> personalityModels) {
        Collections.sort(personalityModels, new Comparator<PersonalityModel>() {
            @Override
            public int compare(PersonalityModel o1, PersonalityModel o2) {
                return o1.getScore() - o2.getScore();
            }
        });

        for (int i=0;i<personalityModels.size();i++)
        {
            titles.get(i).setText(personalityModels.get(i).getTitle());
            scores.get(i).setProgress(personalityModels.get(i).getScore());
        }
    }

    @Override
    public void showErrorMessage(String error) {
        unlockFeature.setVisibility(View.VISIBLE);
        tvFeatureTitle.setText(error);

    }

    private void showMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(AchievementContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
