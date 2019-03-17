package com.android.um.signin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.um.BaseActivity;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.postLogin.PostLoginActivity;
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
    ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        PresenterInjector.injectSignInPresenter(this);

        callbackManager = CallbackManager.Factory.create();
        final String EMAIL = "email";
        facebookLogin.setReadPermissions(Arrays.asList(EMAIL));

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
            showLoading();
            mPresenter.signInUser(etUsername.getEditableText().toString(), etPassword.getEditableText().toString());

        }
    }

    @Override
    public void signInFailed(String message) {
        hideLoading();
        showMessage(this, message);
    }

    @Override
    public void signInSuccess() {
        hideLoading();
        Intent intent = new Intent(this, PostLoginActivity.class);
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
        dialog = ProgressDialog.show(SigninActivity.this, "",
                "Loading. Please wait...", true);
        dialog.setCancelable(false);

    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
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
                mPresenter.signinWithFaceBook(callbackManager);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
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
    }


}
