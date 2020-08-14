package org.meruyouthservice.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.meruyouthservice.R;
import org.meruyouthservice.models.Users;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.email) EditText mEmail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.mysLogin) Button mLogin;
    @BindView(R.id.attempts) TextView mAttempts;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog mAuthProgressDialog;
    private int counter=5;
    private String email;

    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLogin.setOnClickListener(this);
        createAuthProgressDialog();
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
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
         email = mEmail.getText().toString().trim();
         password = mPassword.getText().toString().trim();

        boolean nullEmail = isEmailNull(email);
        boolean nullPassword = isPasswordNull(password);
        if (!nullEmail || !nullPassword )return;

        mAuthProgressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mAuthProgressDialog.dismiss();
                        if (!task.isSuccessful()) {
                            new SweetAlertDialog(Login.this,SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Wrong credentials")
                            .show();
                    counterCheck();
                        }else{
                            new SweetAlertDialog(Login.this,SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Login Successful")
                                .show();
                        }
                    }
                });
//        Users users=new Users(userNames,password);
//        MysApi mysApi= MysClient.getClient();
//        Call<Users>call=mysApi.login(users);
//        call.enqueue(new Callback<Users>() {
//            @Override
//            public void onResponse(Call<Users> call, Response<Users> response) {
//                mAuthProgressDialog.dismiss();
//                if (response.isSuccessful()){
//                    mUsers=response.body();
//                    userName= mUsers.getUsername();
//                    if (userNames.equals(userName)){
//                        new SweetAlertDialog(Login.this,SweetAlertDialog.SUCCESS_TYPE)
//                                .setTitleText("Login Successful")
//                                .show();
//                        Intent intent=new Intent(Login.this,MainActivity.class);
//                        intent.putExtra("userNames",userNames);
//                        startActivity(intent);
//                        finish();
//                    }
//                }else {
//                    new SweetAlertDialog(Login.this,SweetAlertDialog.SUCCESS_TYPE)
//                            .setTitleText("Wrong credentials")
//                            .show();
//                    counterCheck();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Users> call, Throwable t) {
//                new SweetAlertDialog(Login.this,SweetAlertDialog.SUCCESS_TYPE)
//                        .setTitleText("Network Error")
//                        .show();
//                mAuthProgressDialog.dismiss();
//            }
//        });
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
//            mLogin.setText("Account Blocked. Contact admin");
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

    private boolean isEmailNull(String uName) {
        if (uName.equals("")) {
            mAuthProgressDialog.dismiss();
            mEmail.setError("Please enter your email");
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}