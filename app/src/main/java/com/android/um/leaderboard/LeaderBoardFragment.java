package com.android.um.leaderboard;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.um.Model.DataModels.LeaderBoardModel;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.adapter.LeaderBoardAdapter;
import com.android.um.info.InfoFragment;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeaderBoardFragment extends Fragment implements LeaderBoardContract.View {


    LeaderBoardContract.Presenter mPresenter;
    LeaderBoardAdapter adapter;
    @BindView(R.id.list_leaderboard)
    RecyclerView listLeaderboard;

    @BindView(R.id.avi)
    AVLoadingIndicatorView loadingIndicatorView;
    ArrayList<LeaderBoardModel> leaderBoardModels=new ArrayList<>();
    public static LeaderBoardFragment newInstance() {
        LeaderBoardFragment f = new LeaderBoardFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_leader_board, container, false);
        PresenterInjector.injectLeaderBoardPresenter(this);
        ButterKnife.bind(this, rootView);
        loadingIndicatorView.setVisibility(View.VISIBLE);
        loadingIndicatorView.show();
        mPresenter.getLeaderBoard();
        adapter = new LeaderBoardAdapter( leaderBoardModels);
        listLeaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
        listLeaderboard.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void showLeaderBoard(ArrayList<LeaderBoardModel> leaderBoardModels) {
        loadingIndicatorView.hide();
        this.leaderBoardModels.addAll(leaderBoardModels);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void noLeaderBoard(String message) {
        loadingIndicatorView.hide();
        showMessage(message);
    }

    private void showMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(LeaderBoardContract.Presenter presenter) {
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
