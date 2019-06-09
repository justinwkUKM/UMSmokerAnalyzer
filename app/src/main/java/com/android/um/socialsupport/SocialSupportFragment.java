package com.android.um.socialsupport;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.premotivationmessage.PreMotivationMessageActivity;
import com.android.um.smokediary.SmokeDiaryActivity;
import com.android.um.targetTosave.TargetToSaveActivity;
import com.android.um.unlockfeature.UnlockFeatureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SocialSupportFragment extends Fragment implements SocialSupportContract.View {

    SocialSupportContract.Presenter mPresenter;
    @BindView(R.id.et_name)
    EditText etName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.social_support_activity, container, false);
        ButterKnife.bind(this, rootView);
        PresenterInjector.injectSocialSupportPresenter(this);


        return rootView;
    }


    @Override
    public void setPresenter(SocialSupportContract.Presenter presenter) {
        this.mPresenter=presenter;
    }

    public static SocialSupportFragment newInstance() {
        SocialSupportFragment f = new SocialSupportFragment();
        return f;
    }



    public void goToPreMotivationMessage()
    {
        Intent intent=new Intent(getContext(), PreMotivationMessageActivity.class);
        intent.putExtra("MOTIVATOR_NAME",etName.getEditableText().toString());
        getContext().startActivity(intent);
    }
    @OnClick(R.id.next_btn)
    public void onViewClicked() {
        if (etName.getEditableText().toString().length()!=0)
            goToPreMotivationMessage();
        else
            etName.setError(getContext().getResources().getString(R.string.text_empty_error));
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


}
