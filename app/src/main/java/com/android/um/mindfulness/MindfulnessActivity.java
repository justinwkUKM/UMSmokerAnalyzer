package com.android.um.mindfulness;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.android.um.BaseActivity;
import com.android.um.Interface.VideoListener;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.adapter.MindfulnessAdapter;
import com.android.um.adapter.MotivationMessagesAdapter;
import com.android.um.motivation_messages.MotivationMessagesActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MindfulnessActivity extends BaseActivity implements MindfulnessContract.View, VideoListener {

    MindfulnessContract.Presenter mPresenter;
    @BindView(R.id.myVideo)
    VideoView myVideo;
    @BindView(R.id.videos_list)
    RecyclerView videosList;
    MindfulnessAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectMindfulnessPresenter(this);
        setLocale(mPresenter.getLanguage(), R.layout.mindfulness_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        mPresenter.getMindfulnessVideos();
    }

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);
    }

    @Override
    public void showMindfulnessVideos(ArrayList<String> videos) {


        Uri vidUri = Uri.parse(videos.get(0));
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(myVideo);
        myVideo.setMediaController(mediaController);
        myVideo.setVideoURI(vidUri);
        myVideo.start();

        mAdapter=new MindfulnessAdapter(videos, MindfulnessActivity.this,this);
        videosList.setLayoutManager(new LinearLayoutManager(this));
        videosList.setAdapter(mAdapter);


    }

    @Override
    public void playVideo(String url) {

        Uri vidUri = Uri.parse(url);
        myVideo.setVideoURI(vidUri);
        myVideo.start();
    }

    @Override
    public void failedToGetVideos(String error) {
        showMessage(this, error);
    }

    @Override
    public void setPresenter(MindfulnessContract.Presenter presenter) {
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
}
