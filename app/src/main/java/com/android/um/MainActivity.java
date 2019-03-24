package com.android.um;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

    }

    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);
    }
}
