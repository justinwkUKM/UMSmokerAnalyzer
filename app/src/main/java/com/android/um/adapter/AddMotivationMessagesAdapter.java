package com.android.um.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.um.Model.DataModels.MotivationMessageModel;
import com.android.um.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMotivationMessagesAdapter  extends RecyclerView.Adapter<AddMotivationMessagesAdapter.ViewHolder> {

    ArrayList<String> messageModels;


    public AddMotivationMessagesAdapter(ArrayList<String> messageModels) {
        this.messageModels = messageModels;
    }

    @NonNull
    @Override
    public AddMotivationMessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = View.inflate(viewGroup.getContext(), R.layout.add_motivation_message_item, null);
        AddMotivationMessagesAdapter.ViewHolder holder = new AddMotivationMessagesAdapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(AddMotivationMessagesAdapter.ViewHolder viewHolder, int position) {

        viewHolder.tvMessage.setText(messageModels.get(position));

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_message)
        TextView tvMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}