package com.android.um.mindfulness;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.um.Interface.VideoListener;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.adapter.MindfulnessAdapter;
import com.android.um.questions.QuestionsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MindfulnessFragment extends Fragment implements MindfulnessContract.View, VideoListener {

    MindfulnessContract.Presenter mPresenter;
//    @BindView(R.id.myVideo)
//    VideoView myVideo;
    @BindView(R.id.videos_list)
    RecyclerView videosList;
    MindfulnessAdapter mAdapter;

    int index = 0;
    @BindView(R.id.unlock_feature)
    RelativeLayout unlock_feature;

    @BindView(R.id.tv_feature_title)
    TextView tvFeatureTitle;
    @BindView(R.id.tv_feature_desc)
    TextView tvFeatureDesc;
    @BindView(R.id.tv_unlock_feature)
    TextView tvUnlockFeature;

    String category = "";

    public static MindfulnessFragment newInstance(int index) {
        MindfulnessFragment f = new MindfulnessFragment();
        Bundle b = new Bundle();
        b.putInt("INDEX", index);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.mindfulness_activity, container, false);
        ButterKnife.bind(this, rootView);
        PresenterInjector.injectMindfulnessPresenter(this);
        mPresenter.getMindfulnessVideos();

        if (getArguments() != null) {
            index = getArguments().getInt("INDEX");
        }

        return rootView;
    }

    @Override
    public void showQuestions(String category) {
        unlock_feature.setVisibility(View.VISIBLE);
     //   myVideo.setVisibility(View.GONE);
        tvFeatureTitle.setText("Unlock Video");
        tvFeatureDesc.setText("To unlock the video we want you to answer some questions");
        this.category = category;
    }

    @Override
    public void playVideo(int index, String url) {
      //  myVideo.setVisibility(View.VISIBLE);
        unlock_feature.setVisibility(View.GONE);
        openVideoActivity(url);
    }

    @Override
    public void showMindfulnessVideos(ArrayList<String> videos) {
//        myVideo.setVisibility(View.VISIBLE);
       Uri vidUri = Uri.parse(videos.get(index - 1));
//        openVideoActivity(videos.get(index - 1));

        mAdapter = new MindfulnessAdapter(videos, getContext(), this);
        videosList.setLayoutManager(new LinearLayoutManager(getContext()));
        videosList.setAdapter(mAdapter);
    }

    void openVideoActivity(String url)
    {
        Intent intent=new Intent(getContext(),VideoPlayActivity.class);
        intent.putExtra("URL",url);
        startActivity(intent);
    }
    @Override
    public void startVideo(int index, String url) {
            mPresenter.checkVideoQuestions(index+1, url);
    }

    @Override
    public void failedToGetVideos(String error) {
        showMessage(error);
    }

    private void showMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(MindfulnessContract.Presenter presenter) {
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

    @OnClick(R.id.tv_unlock_feature)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), QuestionsActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
        getActivity().getFragmentManager().popBackStack();
    }
}
