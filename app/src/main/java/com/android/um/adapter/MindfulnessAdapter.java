package com.android.um.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.um.Interface.VideoListener;
import com.android.um.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MindfulnessAdapter extends RecyclerView.Adapter<MindfulnessAdapter.ViewHolder> {

    ArrayList<String> videos;

    Context mContext;
    VideoListener listener;
    private String[] titles = new String[]{"Kesimpulan", "Kesedaran Tabiat Merokok", "Latihan Kesedaran 5 Min", "Introduction"};
    public MindfulnessAdapter(ArrayList<String> videos, Context mContext,VideoListener listener) {
        this.videos = videos;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = View.inflate(viewGroup.getContext(), R.layout.videolistitem, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.title.setText(titles[position]);
        viewHolder.desc.setText("This is the description of the "+ titles[position] + " video");

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.startVideo(position,videos.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)
        RelativeLayout layout;
        @BindView(R.id.video_bg)
        ImageView img;
        @BindView(R.id.video_title)
        TextView title;
        @BindView(R.id.video_description)
        TextView desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
