package com.android.um;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;


import com.android.um.prelogin.PreLoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GenderActivity extends BaseActivity {

    @BindView(R.id.male_btn)
    Button maleBtn;
    @BindView(R.id.female_btn)
    Button femaleBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_gender);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    public void goToNextActivity(String gender) {
        Intent intent = new Intent(this, PreLoginActivity.class);
        startActivity(intent);

    }

    @OnClick({R.id.male_btn, R.id.female_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.male_btn:
                goToNextActivity("male");
                break;
            case R.id.female_btn:
                goToNextActivity("female");
                break;
        }
    }
}
