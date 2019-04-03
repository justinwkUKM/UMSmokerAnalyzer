package com.android.um;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.um.targetTosave.TargetToSaveActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashBoardFragment extends Fragment {

    @BindView(R.id.tv_money_saved)
    TextView tvMoneySaved;
    @BindView(R.id.tv_target_save)
    TextView tvTargetSave;
    @BindView(R.id.tv_target_save_amount)
    TextView tvTargetSaveAmount;
    @BindView(R.id.btn_smoke_diary)
    FloatingActionButton btn_smoke_diary;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.dashboard_fragment, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    public static DashBoardFragment newInstance() {
        DashBoardFragment f = new DashBoardFragment();
        return f;
    }

    public void goToTargetToSave() {
        Intent intent = new Intent(getActivity(), TargetToSaveActivity.class);
        startActivity(intent);
    }

    public void goToSmokeDiary()
    {
        Intent intent = new Intent(getActivity(), SmokeDiaryActivity.class);
        startActivity(intent);

    }
    @OnClick({R.id.tv_target_save,R.id.btn_smoke_diary})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_target_save:
                goToTargetToSave();
                break;
            case R.id.btn_smoke_diary:
                goToSmokeDiary();
                break;

        }

    }


}
