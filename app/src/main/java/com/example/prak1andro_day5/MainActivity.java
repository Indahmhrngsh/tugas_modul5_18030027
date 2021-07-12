package com.example.prak1andro_day5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText mEmailView;
    private EditText mPasswordView;
    private CheckBox checkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailView = (EditText) findViewById(R.id.email);
        int imeActionId = getResources().getInteger(R.integer.customImeActionId);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == imeActionId || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Button mEmailsignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailsignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        checkBoxRememberMe = (CheckBox) findViewById(R.id.checkBoxRememberMe);
        if (!new shared_preference(this).isUserLogedOut()) {
            startHomeActivity();
        }
    }

    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        /*Validasi email*/
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;

        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();

        } else {
            if (checkBoxRememberMe.isChecked())
                saveLoginDetails(email, password);
            startHomeActivity();
        }
    }
    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    private void saveLoginDetails(String email, String password) {
        new shared_preference(this).saveLoginDetails(email, password);

        }
        private boolean isEmailValid(String email) {
            return email.contains("@");
        }
        private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}