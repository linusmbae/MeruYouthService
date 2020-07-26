package org.meruyouthservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btnSubmit)Button mSubmit;

    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        getSupportActionBar().setTitle("Welcome "+email);

        ButterKnife.bind(this);
        mSubmit.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logoutMYS) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        AlertDialog dialog=new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_baseline_exit_to_app_24)
                .setTitle("Meru Youth Service")
                .setMessage("Confirm Logout")
                .setPositiveButton("Ok",null)
                .setNegativeButton("Cancel",null)
                .show();
        Button positiveButton=dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v==mSubmit){
            submitAnswers();
        }
    }

    private void submitAnswers() {
        new SweetAlertDialog(MainActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Submission Successful")
                .show();
    }
}