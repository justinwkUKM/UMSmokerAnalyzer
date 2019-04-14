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

public class MotivationMessagesAdapter extends RecyclerView.Adapter<MotivationMessagesAdapter.ViewHolder> {

    ArrayList<MotivationMessageModel> messageModels;



    public MotivationMessagesAdapter(ArrayList<MotivationMessageModel> messageModels) {
        this.messageModels = messageModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = View.inflate(viewGroup.getContext(), R.layout.motivation_messages_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        if (messageModels.get(position).getImageUrl()!=null && messageModels.get(position).getImageUrl().length()>0)
        {
            byte[] decodedString = Base64.decode(messageModels.get(position).getImageUrl(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            viewHolder.imageView.setImageBitmap(decodedByte);
        }
        viewHolder.tvName.setText(messageModels.get(position).getName());
        viewHolder.tvMessageDate.setText(messageModels.get(position).getMessage_date());
        viewHolder.tvMessage.setText(messageModels.get(position).getMessage());

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_view)
        CircularImageView imageView;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_message_date)
        TextView tvMessageDate;
        @BindView(R.id.tv_message)
        TextView tvMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
