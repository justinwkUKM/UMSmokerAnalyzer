package com.android.um.signup;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.um.BaseActivity;
import com.android.um.Model.DataModels.User;
import com.android.um.PresenterInjector;
import com.android.um.R;
import com.android.um.postLogin.PostLoginActivity;
import com.android.um.signin.SigninActivity;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignupActivity extends BaseActivity implements SignupContract.View {


    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;
    @BindView(R.id.et_age)
    EditText et_age;

    @BindView(R.id.et_gender)
    EditText et_gender;

    @BindView(R.id.il_et_gender)
    TextInputLayout il_et_gender;
    SignupContract.Presenter mPresenter;

    ArrayList<EditText> fields = new ArrayList<>();

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        PresenterInjector.injectSignupPresenter(this);
        fields.add(et_username);
        fields.add(et_email);
        fields.add(et_password);
        fields.add(et_confirm_password);
        fields.add(et_age);
        fields.add(et_gender);
        progressDialog= new ProgressDialog(SignupActivity.this);
    }

    @Override
    public void handleSignup(User user) {
        hideLoading();
                Intent intent=new Intent(SignupActivity.this, PostLoginActivity.class);
                startActivity(intent);
                finish();

    }

    public boolean validate(ArrayList<EditText> validateFields) {
        String mainPassword="";
        //TODO move this to presenter
        for (int i = 0; i < validateFields.size(); i++) {
            if (validateFields.get(i).getId() == R.id.et_username) {
                String username=validateFields.get(i).getEditableText().toString();
                if (username.length() <= 0)
                {
                    validateFields.get(i).setError("Username cant be empty");
                    return false;
                }
            }
            if (validateFields.get(i).getId() == R.id.et_email) {
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

                mainPassword=validateFields.get(i).getEditableText().toString();
            }
            if (validateFields.get(i).getId() == R.id.et_confirm_password) {
                String repassword=validateFields.get(i).getEditableText().toString();
                if (repassword.length() <= 0)
                {
                    validateFields.get(i).setError("Password cant be empty");
                    return false;
                }

                else if (!repassword.equals(mainPassword))
                {
                    validateFields.get(i).setError("Password doesnt match");
                    return false;
                }
            }
            if (validateFields.get(i).getId() == R.id.et_age) {
                String age=validateFields.get(i).getEditableText().toString();
                if (age.length() <= 0)
                {
                    validateFields.get(i).setError("Age cant be empty");
                    return false;
                }

                else if (Integer.parseInt(age)<18)
                {
                    validateFields.get(i).setError("Enter a valid age");
                    return false;
                }
            }
            if (validateFields.get(i).getId() == R.id.et_gender) {
                String gender=validateFields.get(i).getEditableText().toString();
                if (gender.length() <= 0)
                {
                    validateFields.get(i).setError("Gender cant be empty");
                    return false;
                }
            }

        }

        return true;
    }
    @Override
    public void setPresenter(SignupContract.Presenter presenter) {
        this.mPresenter=presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading() {
        progressDialog.show(SignupActivity.this, "",
                "Loading. Please wait...", true);
        progressDialog.setCancelable(false);
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    public void signup()
    {
        if (validate(fields))
        {
            User user=new User();
            user.setUsername(et_username.getEditableText().toString());
            user.setEmail(et_email.getEditableText().toString());
            user.setPassword(et_password.getEditableText().toString());
            user.setAge(Integer.valueOf(et_age.getEditableText().toString()));
            user.setGender(et_gender.getEditableText().toString());
            showLoading();
            mPresenter.Signup(user);

        }
    }
    @OnClick({R.id.btn_signup,R.id.il_et_gender})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_signup:
                signup();
                break;
            case R.id.il_et_gender:
                showDialog();
                break;
        }
    }

    @Override
    public void handleFailedSignup(String message) {
        hideLoading();
        showMessage(this,message);
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void showDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.gender_dialog);
        dialog.setTitle("Gender");

       final TextView text_male =  dialog.findViewById(R.id.tv_gender_male);
        final TextView text_female =  dialog.findViewById(R.id.tv_gender_female);

        text_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_gender.setText(getResources().getString(R.string.text_female));
                dialog.dismiss();
            }
        });

        text_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_gender.setText(getResources().getString(R.string.text_male));
                dialog.dismiss();
            }
        });

        customizeDialog(dialog);
        dialog.show();

    }
    public void customizeDialog(Dialog dialog)
    {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        Double width = metrics.widthPixels*.7;
        Double height = metrics.heightPixels*.2;
        Window win = dialog.getWindow();
        win.setLayout(width.intValue(), height.intValue());
    }
    //    public void getData()
//    {
//        FirebaseApp.initializeApp(SignupActivity.this);
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference("questions");
//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ArrayList<Question> questions=new ArrayList<>();
//                for(DataSnapshot dsp : dataSnapshot.getChildren()){
//                    Question question = dsp.getValue(Question.class);
//                    questions.add(question);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

}
