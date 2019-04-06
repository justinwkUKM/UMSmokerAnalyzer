package com.android.um.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.um.Model.DataModels.SmokeDiaryModel;
import com.android.um.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmokeDiaryAdapter extends RecyclerView.Adapter<SmokeDiaryAdapter.ViewHolder> {

    ArrayList<SmokeDiaryModel> smokeDiaryModels;


    public SmokeDiaryAdapter(ArrayList<SmokeDiaryModel> smokeDiaryModels) {
        this.smokeDiaryModels = smokeDiaryModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = View.inflate(viewGroup.getContext(), R.layout.smoke_diary_item_activity, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.tvDiaryDay.setText(smokeDiaryModels.get(position).getDay());
        viewHolder.tvDiaryMonth.setText(smokeDiaryModels.get(position).getDate());
        viewHolder.tvCravings.setText(""+smokeDiaryModels.get(position).getCravings());
        viewHolder.tvSeverity.setText(""+smokeDiaryModels.get(position).getSeverity());
        viewHolder.tvSmoked.setText(smokeDiaryModels.get(position).getSmoked());
    }

    @Override
    public int getItemCount() {
        return smokeDiaryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_diary_day)
        TextView tvDiaryDay;
        @BindView(R.id.tv_diary_month)
        TextView tvDiaryMonth;
        @BindView(R.id.tv_smoked)
        TextView tvSmoked;
        @BindView(R.id.tv_cravings)
        TextView tvCravings;
        @BindView(R.id.tv_severity)
        TextView tvSeverity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
