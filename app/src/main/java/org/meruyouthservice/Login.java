package org.meruyouthservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.email) EditText mEmail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.mysLogin) Button mLogin;
    @BindView(R.id.attempts) TextView mAttempts;

    private ProgressDialog mAuthProgressDialog;
    private int counter=5;
    private String myEmail="linus@gmail.com";
    private String myPassword="12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLogin.setOnClickListener(this);
        createAuthProgressDialog();
    }

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Meru Youth Service");
        mAuthProgressDialog.setMessage("Authenticating Credentials...");
        mAuthProgressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        if (v==mLogin){
            login();
        }
    }

    private void login() {
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        boolean nullEmail = isEmailNull(email);
        boolean validEmail = isValidEmail(email);
        boolean nullPassword = isPasswordNull(password);
        if (!nullEmail || !nullPassword ||!validEmail)return;

        mAuthProgressDialog.show();

        if ((email.equals(myEmail)) && (password.equals(myPassword))){
            Intent intent=new Intent(Login.this,MainActivity.class);
            intent.putExtra("email",email);
            startActivity(intent);
            finish();
        }else if ((!email.equals(myEmail)) && (password.equals(myPassword))) {
            mEmail.setError("Wrong Email");
            counterCheck();
        }else if ((email.equals(myEmail)) && (!password.equals(myPassword))) {
            mPassword.setError("Wrong password");
           counterCheck();
        }else{
            counterCheck();
            }
    }

    private void counterCheck() {
        counter--;
        mAttempts.setText(String.valueOf(counter));
        mAttempts.setTextColor(Color.RED);
        mAuthProgressDialog.dismiss();
        if (counter==1){
            mLogin.setText("Warning! Attempts remaining: "+String.valueOf(counter));
            mLogin.setTextColor(Color.RED);
        }else if (counter==0) {
            mLogin.setEnabled(false);
            mLogin.setText("Account Blocked. Contact admin");
            mLogin.setBackgroundColor(Color.RED);
            mLogin.setTextColor(Color.WHITE);
        }
    }

    private boolean isPasswordNull(String password) {
        if (password.equals("")) {
            mAuthProgressDialog.dismiss();
            mPassword.setError("Please enter yor password");
            return false;
        }
        return true;
    }

    private boolean isEmailNull(String email) {
        if (email.equals("")) {
            mAuthProgressDialog.dismiss();
            mEmail.setError("Please enter your email");
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        boolean isGoodEmail =
                (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            mEmail.setError("Please enter a valid email address");
            return false;
        }
        return isGoodEmail;
    }

}