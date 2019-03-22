package com.android.um;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.um.Interface.OnNextQuestion;
import com.android.um.Model.DataModels.options;
import com.android.um.questionnaire.questions_a.QuestionActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Fragment1 extends Fragment {

    static OnNextQuestion mlistener;

    String mKey = "";
    int position = 0;
    ArrayList<options> options;
    @BindView(R.id.option_radiobutton1)
    RadioButton optionRadiobutton1;
    @BindView(R.id.option_radiobutton2)
    RadioButton optionRadiobutton2;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.option_radiobutton3)
    RadioButton optionRadiobutton3;
    @BindView(R.id.option_radiobutton4)
    RadioButton optionRadiobutton4;
    @BindView(R.id.option_radiobutton5)
    RadioButton optionRadiobutton5;
    ArrayList<RadioButton> radioButtons;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment3, container, false);
        mlistener = QuestionActivity.listener;
        ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            mKey = getArguments().getString("key");
            position = getArguments().getInt("position");
            options = getArguments().getParcelableArrayList("options");
        }
        radioButtons=new ArrayList<>(5);

        radioButtons.add(optionRadiobutton1);
        radioButtons.add(optionRadiobutton2);
        radioButtons.add(optionRadiobutton3);
        radioButtons.add(optionRadiobutton4);
        radioButtons.add(optionRadiobutton5);

        for (int i=0;i<options.size();i++)
        {
            radioButtons.get(i).setText(options.get(i).getDescription());
            radioButtons.get(i).setTag(options.get(i));
            radioButtons.get(i).setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    public static Fragment1 newInstance(String key, int position, ArrayList<options> options) {

        Fragment1 f = new Fragment1();
        Bundle b = new Bundle();
        b.putString("key", key);
        b.putInt("position", position);
        b.putParcelableArrayList("options", options);
        f.setArguments(b);
        return f;
    }

    @OnClick({R.id.option_radiobutton1, R.id.option_radiobutton2,R.id.option_radiobutton3, R.id.option_radiobutton4, R.id.option_radiobutton5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.option_radiobutton1:
                options option1=(options) optionRadiobutton1.getTag();
                option1.setValue(optionRadiobutton1.getText().toString());
                mlistener.setSelectedOption(mKey,option1, position);
                break;
            case R.id.option_radiobutton2:
                options option2=(options) optionRadiobutton1.getTag();
                option2.setValue(optionRadiobutton2.getText().toString());
                mlistener.setSelectedOption(mKey,option2, position);
                break;
            case R.id.option_radiobutton3:
                options option3=(options) optionRadiobutton1.getTag();
                option3.setValue(optionRadiobutton3.getText().toString());
                mlistener.setSelectedOption(mKey,option3, position);
                break;
            case R.id.option_radiobutton4:
                options option4=(options) optionRadiobutton1.getTag();
                option4.setValue(optionRadiobutton4.getText().toString());
                mlistener.setSelectedOption(mKey,option4, position);
                break;
            case R.id.option_radiobutton5:
                options option5=(options) optionRadiobutton1.getTag();
                option5.setValue(optionRadiobutton5.getText().toString());
                mlistener.setSelectedOption(mKey,option5, position);
                break;
        }
    }
}
