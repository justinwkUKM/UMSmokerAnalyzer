package com.android.um.questions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.um.Interface.OnNextQuestion;
import com.android.um.Model.DataModels.options;
import com.android.um.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AgeFragment extends Fragment {
    static OnNextQuestion mlistener;
    static String mKey = "";
    @BindView(R.id.age_text)
    EditText age_text;
    Unbinder unbinder;
    int position = 0;
    ArrayList<options> options;
    @BindView(R.id.text_description)
    TextView textDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.age_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mlistener = QuestionsActivity.listener;

        if (getArguments() != null) {
            mKey = getArguments().getString("key");
            position = getArguments().getInt("position");
            options = getArguments().getParcelableArrayList("options");
        }
        textDescription.setText(mKey);

        age_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0)
                {
                    options.get(0).setValue(s.toString());
                    mlistener.setSelectedOption(mKey, options.get(0), position);
                }
                else
                {
                    options.get(0).setValue(" ");
                    mlistener.setSelectedOption(mKey, options.get(0), position);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return rootView;
    }


    public static AgeFragment newInstance(String key, int position, ArrayList<options> options) {

        AgeFragment f = new AgeFragment();
        Bundle b = new Bundle();
        b.putString("key", key);
        b.putInt("position", position);
        b.putParcelableArrayList("options", options);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
