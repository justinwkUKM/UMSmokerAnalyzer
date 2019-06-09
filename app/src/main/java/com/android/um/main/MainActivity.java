package com.android.um.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.android.um.BaseActivity;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.achievement.AchievementFragment;
import com.android.um.dashboard.DashBoardFragment;
import com.android.um.info.InfoFragment;
import com.android.um.mindfulness.MindfulnessFragment;
import com.android.um.profile.ProfileFragment;
import com.android.um.signin.SigninActivity;
import com.android.um.socialsupport.SocialSupportFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainActivityContract.View{

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
    FragmentManager fm;
    MainActivityContract.Presenter mPresenter;

    String  DASHBOARD_FRAGMENT="DASHBOARD_FRAGMENT";
    String  MINFFULNESS_FRAGMENT="MINFFULNESS_FRAGMENT";
    String  PROFILE_FRAGMENT="PROFILE_FRAGMENT";
    String  SOCIAL_SUPPORT_FRAGMENT="SOCIAL_SUPPORT_FRAGMENT";
    String  ACHIEVEMENT_FRAGMENT="ACHIEVEMENT_FRAGMENT";
    String  INFO_FRAGMENT="INFO_FRAGMENT";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectMainPresenter(this);
        setLocale(mPresenter.getLanguage(),R.layout.activity_main_activity);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra("FRAGMENT") && getIntent().getStringExtra("FRAGMENT").equals("MINFFULNESS_FRAGMENT"))
        {
            int index=Integer.valueOf(getIntent().getStringExtra("INDEX"));
            loadFragment(MindfulnessFragment.newInstance(index),MINFFULNESS_FRAGMENT);
        }
        else
            loadFragment(DashBoardFragment.newInstance(),DASHBOARD_FRAGMENT);

        mPresenter.getToken();

    }

    private void loadFragment(final Fragment fragment,String fragment_tag) {
         fm = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fm.beginTransaction();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                fragmentTransaction.setCustomAnimations(R.animator.fade_in,
                        R.animator.fade_out);
                fragmentTransaction.addToBackStack(fragment_tag);
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
                loadFragment(ProfileFragment.newInstance(),PROFILE_FRAGMENT);
                break;
            case R.id.dashboard_btn:
                loadFragment(DashBoardFragment.newInstance(),DASHBOARD_FRAGMENT);
                break;
            case R.id.awards_btn:
                loadFragment(AchievementFragment.newInstance(),ACHIEVEMENT_FRAGMENT);
                break;
            case R.id.hyponsis_btn:
                loadFragment(MindfulnessFragment.newInstance(1),MINFFULNESS_FRAGMENT);
                break;
            case R.id.support_btn:
                loadFragment(SocialSupportFragment.newInstance(),SOCIAL_SUPPORT_FRAGMENT);
                break;
            case R.id.compass_btn:
                break;
            case R.id.info_btn:
                loadFragment(InfoFragment.newInstance(),INFO_FRAGMENT);
                break;
            case R.id.logout_btn:
                mPresenter.logout();
                break;
        }
    }

    public void goToMindfulnessVideo()
    {
        Intent intent=new Intent(this, MindfulnessFragment.class);
        startActivity(intent);
    }
    @Override
    public void setPresenter(MainActivityContract.Presenter presenter) {
        this.mPresenter=presenter;
    }

    @Override
    public void handleLogOut() {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
        finish();
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
    public void onBackPressed() {


       if (fm.getBackStackEntryCount()>1)
       {

           for (int i=1;i<fm.getBackStackEntryCount();i++)
               fm.popBackStackImmediate();


           if(fm.getBackStackEntryAt(0).getName()!=DASHBOARD_FRAGMENT)
               loadFragment(DashBoardFragment.newInstance(),DASHBOARD_FRAGMENT);

       }
       else
           finish();


    }
}
