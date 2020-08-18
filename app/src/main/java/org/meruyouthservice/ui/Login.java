package org.meruyouthservice.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.meruyouthservice.R;
import org.meruyouthservice.models.Users;
import org.meruyouthservice.services.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.email) EditText mEmail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.mysLogin) Button mLogin;
    @BindView(R.id.attempts) TextView mAttempts;


    private ProgressDialog mAuthProgressDialog;
    private int counter=5;
    private String email;
    private String password;
    DatabaseReference RootRef;
    public ArrayList<Users>mUsers;

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
         email = mEmail.getText().toString().trim();
        password = mPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email)){
            mAuthProgressDialog.dismiss();
            mEmail.setError("Please enter your Email");
        }else if (TextUtils.isEmpty(password)){
            mAuthProgressDialog.dismiss();
            mPassword.setError("Please enter yor password");
        }else {
            mAuthProgressDialog.show();
            AllowAccess(email,password);
        }

    }

    private void AllowAccess(final String email,final String password) {
        mUsers=new ArrayList<Users>();
        RootRef= FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_CHILD_MYS_USERS);
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        Users usersData=dataSnapshot1.getValue(Users.class);
                       if (usersData.getEmail().equals(email)){
                           if (usersData.getPassword().equals(password)&&!usersData.getPassword().equals("Blocked")){
                               if (Constants.FIREBASE_CHILD_MYS_USERS.equals("Users")){
                                   mAuthProgressDialog.dismiss();
                                   new SweetAlertDialog(Login.this,SweetAlertDialog.SUCCESS_TYPE)
                                           .setTitleText("Login Successful")
                                           .show();

                                   String name=usersData.getUserName();
                                   Intent intent = new Intent(Login.this,MainActivity.class);
                                   intent.putExtra("userNames",name);
                                   startActivity(intent);
                                   finish();
                               }
                           }else if (usersData.getPassword().equals("Blocked")){
                               mAuthProgressDialog.dismiss();
                               new SweetAlertDialog(Login.this,SweetAlertDialog.ERROR_TYPE)
                                       .setTitleText("Your account is Blocked")
                                       .show();
                           }else {
                               mAuthProgressDialog.dismiss();
                               new SweetAlertDialog(Login.this,SweetAlertDialog.ERROR_TYPE)
                                       .setTitleText("Wrong Password")
                                       .show();
                               counterCheck();
                           }
                       }else {
                           mAuthProgressDialog.dismiss();
                           new SweetAlertDialog(Login.this,SweetAlertDialog.ERROR_TYPE)
                                   .setTitleText("Wrong Email")
                                   .show();
                       }
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mAuthProgressDialog.dismiss();
                new SweetAlertDialog(Login.this,SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .show();
            }
        });
    }


    private void counterCheck() {
        counter--;
        mAttempts.setText(String.valueOf(counter));
        mAttempts.setTextColor(Color.RED);
        mAuthProgressDialog.dismiss();
        if (counter==1){
            new SweetAlertDialog(Login.this,SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Warning! Attempts remaining: "+String.valueOf(counter))
                    .show();
        }else if (counter==0) {
            mLogin.setEnabled(false);
//            DatabaseReference reference = null;
//            String ref;
//            ref=reference.child(Constants.FIREBASE_CHILD_MYS_USERS).getKey();
//            RootRef.child(Constants.FIREBASE_CHILD_MYS_USERS).child(phone).child("password").setValue("Blocked");
            new SweetAlertDialog(Login.this,SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Try Again Later ")
                    .show();
        }
    }


}