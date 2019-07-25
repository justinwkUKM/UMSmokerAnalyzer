package com.android.um.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.um.Model.DataModels.LeaderBoardModel;
import com.android.um.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {



    ArrayList<LeaderBoardModel> leaderBoardModels;
    Context mContext;
    public LeaderBoardAdapter(ArrayList<LeaderBoardModel> leaderBoardModels) {
       this.leaderBoardModels=leaderBoardModels;
      // this.mContext=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = View.inflate(viewGroup.getContext(), R.layout.leaderboard_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.leaderIndex.setText((position+1)+". ");
        viewHolder.leaderName.setText(leaderBoardModels.get(position).getName());
        viewHolder.leader_hours.setText(leaderBoardModels.get(position).getHours()+" Hours");
//        if (position==0)
//            viewHolder.leaderBadge.setImageDrawable(mContext.getResources().getDrawable(R.drawable.gold_medal));
//        else if (position==1)
//            viewHolder.leaderBadge.setImageDrawable(mContext.getResources().getDrawable(R.drawable.silver_medal));
//        else
//            viewHolder.leaderBadge.setImageDrawable(mContext.getResources().getDrawable(R.drawable.normal_medal));
    }

    @Override
    public int getItemCount() {
        return leaderBoardModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.leader_index)
        TextView leaderIndex;
        @BindView(R.id.leader_name)
        TextView leaderName;
        @BindView(R.id.leader_hours)
        TextView leader_hours;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}