package com.android.um.mindfulness;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.MediaController;
import android.widget.VideoView;

import com.android.um.BaseActivity;
import com.android.um.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoPlayActivity extends BaseActivity {

    @BindView(R.id.myVideo)
    VideoView myVideo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplayer_activity);
        ButterKnife.bind(this);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(myVideo);
        myVideo.setMediaController(mediaController);
        Uri vidUri = Uri.parse(getIntent().getStringExtra("URL"));
        myVideo.setVideoURI(vidUri);
        myVideo.start();

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
