package com.android.um.signin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.um.BaseActivity;
import com.android.um.main.MainActivity;
import com.android.um.Model.DataModels.User;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.Utils.AppUtils;
import com.android.um.questions.QuestionsActivity;
import com.android.um.resetpassword.ForgetPasswordActivity;
import com.android.um.signup.SignupActivity;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SigninActivity extends BaseActivity implements SigninContract.View {


    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.ch_remember_me)
    CheckBox chRememberMe;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.tv_new_user)
    TextView tvNewUser;

    @BindView(R.id.google_login)
    SignInButton googleLogin;
    @BindView(R.id.facebook_login)
    LoginButton facebookLogin;
    private FirebaseAuth auth;
    private CallbackManager callbackManager;
    SigninContract.Presenter mPresenter;
    GoogleSignInClient mGoogleSignInClient;


    ArrayList<EditText> fields = new ArrayList<>();
    @BindView(R.id.avi)
    AVLoadingIndicatorView loadingIndicatorView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterInjector.injectSignInPresenter(this);
        setLocale(mPresenter.getLanguage(),R.layout.activity_signin);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        init();
        AppUtils.hideKeyboard(SigninActivity.this);
    }

    public void init() {
        callbackManager = CallbackManager.Factory.create();
        facebookLogin.setReadPermissions(Arrays.asList( "email"));
//        facebookLogin.setReadPermissions(Arrays.asList( "email", "user_birthday","user_gender"));

        fields.add(etUsername);
        fields.add(etPassword);
    }

    public boolean validate(ArrayList<EditText> validateFields) {
        //TODO move this to presenter
        for (int i = 0; i < validateFields.size(); i++) {
            if (validateFields.get(i).getId() == R.id.et_username) {
                String email = validateFields.get(i).getEditableText().toString();
                if (email.length() <= 0) {
                    validateFields.get(i).setError("Email cant be empty");
                    return false;
                } else {
                    Pattern pattern = Pattern.compile("^.+@.+\\..+$");
                    Matcher matcher = pattern.matcher(email);
                    if (!matcher.matches()) {
                        validateFields.get(i).setError("Invalid email");
                        return false;
                    }
                }
            }
            if (validateFields.get(i).getId() == R.id.et_password) {
                String password=validateFields.get(i).getEditableText().toString();
                if (password.length() <= 0)
                {
                    validateFields.get(i).setError("Password cant be empty");
                    return false;
                }

                else if (password.length() < 6)
                {
                    validateFields.get(i).setError("Password at least 6 characters");
                    return false;
                }
            }
        }

        return true;
    }

    public void login() {
        if (validate(fields))
        {
            User user=new User();
            user.setEmail(etUsername.getEditableText().toString());
            user.setPassword(etPassword.getEditableText().toString());
            showLoading();
            mPresenter.signInUser(user);

        }
    }

    @Override
    public void signInFailed(String message) {
        hideLoading();
        showMessage(this, message);
    }

    @Override
    public void goToDemographicQuestionsScreen() {
        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra("category","demographicQuestions");
        startActivity(intent);
        finish();
    }

    @Override
    public void goToLevelAddictionScreen() {
        Intent intent = new Intent(this, QuestionsActivity.class);
        intent.putExtra("category","leveladdictionQuestions");
        startActivity(intent);
        finish();
    }

    @Override
    public void goToMainScreen() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void signInSuccess() {
        hideLoading();
        mPresenter.showQuestionsA();
    }

    @Override
    public void continueToSignUp(User user) {
        hideLoading();
        Intent intent = new Intent(this, SignupActivity.class);
        intent.putExtra("mUser",user);
        startActivity(intent);
    }

    @Override
    public void startGoogleIntent() {
        showLoading();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void setPresenter(SigninContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        googleLogin.setEnabled(false);
        facebookLogin.setEnabled(false);
        loginBtn.setEnabled(false);
        loadingIndicatorView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        googleLogin.setEnabled(true);
        facebookLogin.setEnabled(true);
        loginBtn.setEnabled(true);

        loadingIndicatorView.setVisibility(View.GONE);

    }

    public void resetPassword() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void signup() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.tv_forget_password, R.id.login_btn, R.id.tv_new_user, R.id.google_login, R.id.facebook_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password:
                resetPassword();
                break;
            case R.id.login_btn:
                login();
                break;
            case R.id.tv_new_user:
                signup();
                break;
            case R.id.google_login:
                startGoogleIntent();
                break;
            case R.id.facebook_login:
                showLoading();
                mPresenter.signinWithFaceBook(callbackManager);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== RESULT_OK && requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                mPresenter.signInWithGoogle(account);
                Log.e(account.getDisplayName(), account.getEmail());
            } catch (ApiException e) {
                Log.e("Error", "Google sign in failed", e);
            }
        }
        hideLoading();
    }


}
