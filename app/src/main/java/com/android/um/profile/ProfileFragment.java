package com.android.um.profile;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.um.Model.DataModels.User;
import com.android.um.PresenterInjector;
import com.android.um.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment implements ProfileContract.View {

    ProfileContract.Presenter mPresenter;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_gender)
    TextView tvGender;


    public static ProfileFragment newInstance() {
        ProfileFragment f = new ProfileFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        PresenterInjector.injectProfilePresenter(this);
        ButterKnife.bind(this, rootView);
        mPresenter.getUser();
        return rootView;
    }

    @Override
    public void showUserInfo(User user) {

        tvUserName.setText(user.getUsername());
        tvAge.setText(""+user.getAge());
        tvGender.setText(user.getGender());
    }

    private void showMessage(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
