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

import com.android.um.Interface.OnNextQuestion;
import com.android.um.Model.DataModels.options;
import com.android.um.questionnaire.questions_a.QuestionActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Fragment2 extends Fragment {
    static OnNextQuestion mlistener;
    static String mKey="";
    @BindView(R.id.editText)
    EditText editText;
    Unbinder unbinder;
    int position=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment2, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mlistener= QuestionActivity.listener;

        if (getArguments() != null) {
            mKey = getArguments().getString("key");
            position=getArguments().getInt("position");
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                options op=new options();
                op.setValue("123");
                mlistener.setSelectedOption(mKey,op,position);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return rootView;
    }



    public static Fragment2 newInstance(String key,int position) {

        Fragment2 f = new Fragment2();
        Bundle b = new Bundle();
        b.putString("key", key);
        b.putInt("position",position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
