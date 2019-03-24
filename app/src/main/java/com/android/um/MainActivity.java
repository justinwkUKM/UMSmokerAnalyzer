package com.android.um;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_activity_pager)
    FrameLayout mainActivityPager;
    @BindView(R.id.profile_btn)
    ImageButton profileBtn;
    @BindView(R.id.dashboard_btn)
    ImageButton dashboardBtn;
    @BindView(R.id.awards_btn)
    ImageButton awardsBtn;
    @BindView(R.id.hyponsis_btn)
    ImageButton hyponsisBtn;
    @BindView(R.id.support_btn)
    ImageButton supportBtn;
    @BindView(R.id.compass_btn)
    ImageButton compassBtn;
    @BindView(R.id.info_btn)
    ImageButton infoBtn;
    @BindView(R.id.logout_btn)
    ImageButton logoutBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);


    }

    private void loadFragment(final Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fm.beginTransaction();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragmentTransaction.replace(R.id.main_activity_pager, fragment);
                fragmentTransaction.commit();
            }
        },100);
    }


    @Override
    public void showMessage(Context context, String message) {
        super.showMessage(context, message);
    }

    @OnClick({R.id.profile_btn, R.id.dashboard_btn, R.id.awards_btn, R.id.hyponsis_btn, R.id.support_btn, R.id.compass_btn, R.id.info_btn, R.id.logout_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_btn:
                break;
            case R.id.dashboard_btn:
                loadFragment(DashBoardFragment.newInstance());
                break;
            case R.id.awards_btn:
                break;
            case R.id.hyponsis_btn:
                break;
            case R.id.support_btn:
                break;
            case R.id.compass_btn:
                break;
            case R.id.info_btn:
                break;
            case R.id.logout_btn:
                break;
        }
    }
}
