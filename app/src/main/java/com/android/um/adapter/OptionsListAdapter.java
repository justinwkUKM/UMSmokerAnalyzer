package com.android.um.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.um.Interface.OnNextQuestion;
import com.android.um.Model.DataModels.options;
import com.android.um.R;
import com.android.um.questionnaire.questions_a.QuestionActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionsListAdapter extends RecyclerView.Adapter<OptionsListAdapter.OptionViewHolder> implements OnNextQuestion {

    ArrayList<com.android.um.Model.DataModels.options> options;
    private int lastCheckedPosition = -1;
    private int optionPosition=0;
    private String selectedValue="";
    public OptionsListAdapter(ArrayList<com.android.um.Model.DataModels.options> options)
    {
        selectedValue="";
        this.options=options;
        QuestionActivity.questionListener=this;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = View.inflate(viewGroup.getContext(), R.layout.option_listitem, null);
        OptionViewHolder holder = new OptionViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(OptionViewHolder viewHolder, int i) {


        com.android.um.Model.DataModels.options option=options.get(i);
        if (options.size()==1)
        {
            if (option.getType().equals("EditText"))
            {
                viewHolder.option_description_edittext.setVisibility(View.VISIBLE);
                viewHolder.option_description_edittext.setHint(option.getDescription());
                if (option.getValue().length()>0 ) {
                    optionPosition=i;
                    viewHolder.option_description_edittext.setText(option.getValue());
                }
                    viewHolder.ll_option_selector.setVisibility(View.GONE);
            }
        }
        else
        {

            if (option.getType().equals("RadioButton"))
            {
                viewHolder.option_radiobutton.setVisibility(View.VISIBLE);
                viewHolder.option_radiobutton.setChecked(i == lastCheckedPosition);
                viewHolder.option_description_textview.setVisibility(View.VISIBLE);
                viewHolder.option_description_textview.setText(option.getDescription());
                viewHolder.option_radiobutton.setTag(option);
                viewHolder.option_radiobutton.setTag(viewHolder.option_radiobutton.getId(),i);

                if (option.getValue().length()>0) {
                    optionPosition=i;
                    viewHolder.option_radiobutton.setChecked(true);
                }
            }
            else if (option.getType().equals("EditText"))
            {
                viewHolder.option_description_edittext.setVisibility(View.VISIBLE);
                viewHolder.option_description_edittext.setHint(option.getDescription());
                if (option.getValue().length()>0 ) {
                    optionPosition=i;
                    viewHolder.option_description_edittext.setText(option.getValue());
                }
            }
            else if (option.getType().equals("CheckBox"))
            {
                viewHolder.option_description_textview.setVisibility(View.VISIBLE);
                viewHolder.option_description_textview.setText(option.getDescription());
                viewHolder.option_checkbox.setVisibility(View.VISIBLE);
            }

        }
    }


    @Override
    public com.android.um.Model.DataModels.options getSelectedOption() {
            options.get(optionPosition).setValue(selectedValue);
        return options.get(optionPosition);
    }

    @Override
    public int getItemCount() {
        return this.options.size();
    }

    public class OptionViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.ll_option_selector)
        RelativeLayout ll_option_selector;

        @BindView(R.id.option_checkbox)
        CheckBox option_checkbox;

        @BindView(R.id.option_radiobutton)
        RadioButton option_radiobutton;

        @BindView(R.id.ll_option_description)
        RelativeLayout ll_option_description;

        @BindView(R.id.option_description_textview)
        TextView option_description_textview;

        @BindView(R.id.option_description_edittext)
        EditText option_description_edittext;

        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            option_radiobutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //we want to reset the value of the radio button if a new one was selected
                    com.android.um.Model.DataModels.options opTemp=options.get((int)v.getTag(option_radiobutton.getId()));
                    opTemp.setValue("");
                    options.set((int)v.getTag(option_radiobutton.getId()),opTemp);
                    notifyDataSetChanged();
                    lastCheckedPosition = getAdapterPosition();
                    optionPosition=lastCheckedPosition;
                    com.android.um.Model.DataModels.options op =(com.android.um.Model.DataModels.options)v.getTag();
                    selectedValue=op.getDescription();
                    notifyDataSetChanged();
                }
            });

            option_description_edittext.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    selectedValue=s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}
