package com.reporting.epidemic.epidemicreporting.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.Presenter.LoginPresenter;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Utils.SharedPreferencesUtil;
import com.reporting.epidemic.epidemicreporting.Views.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;

    @BindView(value = R.id.login_progress)
    ProgressBar mLoginProgress;


    private String mCurrentUser;

    private LoginPresenter mLoginPresenter;

    private ProgressDialog mProgressDialog;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginPresenter =  new LoginPresenter(this);
        
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(LoginActivity.this,
//                    R.style.ImagePickerThemeFullScreen);
//        }
//
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setMessage("认证中...");
//        mProgressDialog.show();

        mLoginProgress.setVisibility(View.VISIBLE);



        mCurrentUser = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        mLoginPresenter.doLogin(mCurrentUser,password);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        SharedPreferencesUtil.getInstance(this).put(Constants.CURRENT_USER,mCurrentUser);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        _loginButton.setEnabled(true);
        mLoginProgress.setVisibility(View.GONE);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        mLoginProgress.setVisibility(View.GONE);
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _emailText.setError("请输入用户名");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("请输入密码");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    @Override
    public void onLoginResult(final Boolean result, int code) {
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                if (result) {
                    onLoginSuccess();
                } else {
                    onLoginFailed();
                }
            }
        });
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {

    }
}