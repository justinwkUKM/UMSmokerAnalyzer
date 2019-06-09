package com.android.um.info;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.questions.QuestionsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoFragment extends Fragment implements InfoContract.View {

    InfoContract.Presenter mPresenter;
    @BindView(R.id.tv_feature_title)
    TextView tvFeatureTitle;
    @BindView(R.id.tv_feature_desc)
    TextView tvFeatureDesc;
    @BindView(R.id.tv_unlock_feature)
    TextView tvUnlockFeature;
    @BindView(R.id.unlock_feature)
    RelativeLayout unlockFeature;
    String category;

    public static InfoFragment newInstance() {
        InfoFragment f = new InfoFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.info_fragment, container, false);
        PresenterInjector.injectInfoPresenter(this);
        ButterKnife.bind(this, rootView);
        mPresenter.checkQuestions();
        return rootView;
    }

    @Override
    public void showInfo() {

    }

    @Override
    public void lockInfo(String category) {
        unlockFeature.setVisibility(View.VISIBLE);
        this.category=category;
    }

    private void showMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(InfoContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick(R.id.tv_unlock_feature)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), QuestionsActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
        getActivity().getFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
