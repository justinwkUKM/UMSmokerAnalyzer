package com.android.um.targetTosave;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.um.BaseActivity;
import com.android.um.Model.DataModels.TargetToSaveModel;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.targetTosavedetails.TargetToSaveDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TargetToSaveActivity extends BaseActivity implements TargetToSaveContract.View {

    TargetToSaveContract.Presenter mPresenter;
    @BindView(R.id.et_travel)
    EditText et_travel;
    @BindView(R.id.il_et_travel)
    TextInputLayout ilEtTravel;
    @BindView(R.id.et_luxrious)
    EditText etLuxrious;
    @BindView(R.id.il_et_luxrious)
    TextInputLayout ilEtLuxrious;
    @BindView(R.id.et_medical)
    EditText etMedical;
    @BindView(R.id.il_et_medical)
    TextInputLayout ilEtMedical;
    @BindView(R.id.et_saving)
    EditText etSaving;
    @BindView(R.id.il_et_saving)
    TextInputLayout ilEtSaving;
    @BindView(R.id.tv_divider)
    View tvDivider;
    @BindView(R.id.tv_save_amount)
    TextView tvSaveAmount;
    @BindView(R.id.ll_target_save)
    LinearLayout llTargetSave;
    @BindView(R.id.ll_note)
    LinearLayout llNote;
    @BindView(R.id.save_btn)
    Button saveBtn;

    TextWatcher textWatcher;

    double travelAmount;
    double luxuriosAmount;
    double medicalAmount;
    double savingsAmount;
    double totalAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectTargetToSavePresenter(this);
        setLocale(mPresenter.getLanguage(), R.layout.target_save_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        init();

    }

    public void init() {
        addTextWatcher(et_travel);
        addTextWatcher(etLuxrious);
        addTextWatcher(etMedical);
        addTextWatcher(etSaving);
    }

    public void addTextWatcher(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeAmount();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void changeAmount() {

        if (et_travel.getEditableText().toString() != null && et_travel.getEditableText().toString().length() > 0)
            travelAmount = Double.parseDouble(et_travel.getEditableText().toString());
        else
            travelAmount = 0;

        if (etLuxrious.getEditableText().toString() != null && etLuxrious.getEditableText().toString().length() > 0)
            luxuriosAmount = Double.parseDouble(etLuxrious.getEditableText().toString());
        else
            luxuriosAmount = 0;
        if (etMedical.getEditableText().toString() != null && etMedical.getEditableText().toString().length() > 0)
            medicalAmount = Double.parseDouble(etMedical.getEditableText().toString());
        else
            medicalAmount = 0;
        if (etSaving.getEditableText().toString() != null && etSaving.getEditableText().toString().length() > 0)
            savingsAmount = Double.parseDouble(etSaving.getEditableText().toString());
        else
            savingsAmount = 0;
        totalAmount=travelAmount + luxuriosAmount + medicalAmount + savingsAmount;
        tvSaveAmount.setText(""+(travelAmount + luxuriosAmount + medicalAmount + savingsAmount));
    }

    @Override
    public void goToDetails() {
        Intent intent = new Intent(this, TargetToSaveDetailsActivity.class);
        intent.putExtra("TOTAL_AMOUNT",totalAmount);
        intent.putExtra("TARGET",new TargetToSaveModel(travelAmount,luxuriosAmount,medicalAmount,savingsAmount));
        startActivity(intent);
        finish();
    }

    public boolean validate()
    {
        if (et_travel.getEditableText().toString() == null || et_travel.getEditableText().toString().length() == 0)
        {
            et_travel.setError(getResources().getString(R.string.text_empty_error));
            return false;
        }

        if (etLuxrious.getEditableText().toString() == null || etLuxrious.getEditableText().toString().length() == 0)
        {
            etLuxrious.setError(getResources().getString(R.string.text_empty_error));
            return false;
        }
        if (etMedical.getEditableText().toString() == null || etMedical.getEditableText().toString().length() == 0)
        {
            etMedical.setError(getResources().getString(R.string.text_empty_error));
            return false;
        }

        if (etSaving.getEditableText().toString() == null || etSaving.getEditableText().toString().length() == 0)
        {
            etSaving.setError(getResources().getString(R.string.text_empty_error));
            return false;
        }

        return true;
    }
    @Override
    public void setPresenter(TargetToSaveContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setLocale(String localeName, int layout) {
        super.setLocale(localeName, layout);
    }

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);
    }

    @OnClick(R.id.save_btn)
    public void onViewClicked() {
        if (validate())
            goToDetails();
    }
}
