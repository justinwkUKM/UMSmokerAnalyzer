package com.android.um;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.um.Model.DataModels.options;
import com.android.um.questionnaire.questions_a.AgeFragment;

import java.util.ArrayList;

public class DashBoardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.dashboard_fragment, container, false);
        return rootView;
    }

    public static DashBoardFragment newInstance() {

        DashBoardFragment f = new DashBoardFragment();
        return f;
    }
}
