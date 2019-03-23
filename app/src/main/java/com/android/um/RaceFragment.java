package com.android.um;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.um.Interface.OnNextQuestion;
import com.android.um.Model.DataModels.options;
import com.android.um.questionnaire.questions_a.QuestionActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RaceFragment extends Fragment {
    static OnNextQuestion mlistener;
    static String mKey = "";

    Unbinder unbinder;
    int position = 0;
    @BindView(R.id.option_radiobutton1)
    RadioButton optionRadiobutton1;
    @BindView(R.id.option_radiobutton2)
    RadioButton optionRadiobutton2;
    @BindView(R.id.option_radiobutton3)
    RadioButton optionRadiobutton3;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.race_others)
    EditText raceOthers;
    ArrayList<options> options;
    @BindView(R.id.text_description)
    TextView textDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.race_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mlistener = QuestionActivity.listener;

        if (getArguments() != null) {
            mKey = getArguments().getString("key");
            position = getArguments().getInt("position");
            options = getArguments().getParcelableArrayList("options");

        }

        textDescription.setText(mKey);
        optionRadiobutton1.setText(options.get(0).getDescription());
        optionRadiobutton2.setText(options.get(1).getDescription());
        optionRadiobutton3.setText(options.get(2).getDescription());

        optionRadiobutton1.setTag(options.get(0));
        optionRadiobutton2.setTag(options.get(1));
        optionRadiobutton3.setTag(options.get(2));
        raceOthers.setTag(options.get(3));
        raceOthers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                options option = (options) raceOthers.getTag();
                option.setValue(s.toString());
                mlistener.setSelectedOption(mKey, option, position);
            }
        });


        return rootView;
    }


    public static RaceFragment newInstance(String key, int position, ArrayList<options> options) {

        RaceFragment f = new RaceFragment();
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

    @OnClick({R.id.option_radiobutton1, R.id.option_radiobutton2, R.id.option_radiobutton3,R.id.race_others})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.option_radiobutton1:
                options option1 = (options) optionRadiobutton1.getTag();
                option1.setValue(optionRadiobutton1.getText().toString());
                mlistener.setSelectedOption(mKey, option1, position);
                raceOthers.setText("");
                break;
            case R.id.option_radiobutton2:
                options option2 = (options) optionRadiobutton2.getTag();
                option2.setValue(optionRadiobutton2.getText().toString());
                mlistener.setSelectedOption(mKey, option2, position);
                raceOthers.setText("");
                break;
            case R.id.option_radiobutton3:
                options option3 = (options) optionRadiobutton3.getTag();
                option3.setValue(optionRadiobutton3.getText().toString());
                mlistener.setSelectedOption(mKey, option3, position);
                raceOthers.setText("");
                break;
            case R.id.race_others:
                optionRadiobutton1.setChecked(false);
                optionRadiobutton2.setChecked(false);
                optionRadiobutton3.setChecked(false);
                break;
        }
    }
}
