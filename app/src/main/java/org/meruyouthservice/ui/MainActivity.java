package org.meruyouthservice.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.meruyouthservice.CustomOnItemSelectedListener;
import org.meruyouthservice.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.name) EditText name;
    @BindView(R.id.age) EditText age;
    @BindView(R.id.location) EditText location;
    @BindView(R.id.ward) EditText ward;
    @BindView(R.id.County) EditText County;

    @BindView(R.id.btnSubmit)Button mSubmit;

    private String userName;
    private String name1;
    private String age1;
    private String location1;
    private String ward1;
    private String County1;
    private ProgressDialog mAuthProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        userName=intent.getStringExtra("userNames");
        getSupportActionBar().setTitle("Welcome "+userName);

        createAuthProgressDialog();

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

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Meru Youth Service");
        mAuthProgressDialog.setMessage("Please Wait....");
        mAuthProgressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        if (v==mSubmit){
            submitAnswers();
        }
    }

    private void submitAnswers() {
        name1=name.getText().toString();
        age1=age.getText().toString();
        location1=location.getText().toString();
        ward1=ward.getText().toString();
        County1=County.getText().toString();

        boolean nullName = isNameNull(name1);
        boolean nullAge = isAgeNull(age1);
        boolean nullLocation = isLocationNull(location1);
        boolean nullWard = isWardNull(ward1);
        boolean nullCounty = isCountyNull(County1);
        if (!nullName || !nullAge || !nullLocation || !nullWard || !nullCounty)return;

        mAuthProgressDialog.show();

        new SweetAlertDialog(MainActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Submission Successful")
                .show();

        Intent intent=new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra("UserName",userName);
        intent.putExtra("name",name1);
        intent.putExtra("age",age1);
        intent.putExtra("location",location1);
        intent.putExtra("ward",ward1);
        intent.putExtra("County",County1);
        startActivity(intent);
        finish();
    }

    private boolean isNameNull(String names) {
        if (names.equals("")) {
            mAuthProgressDialog.dismiss();
            name.setError("Please fill this field");
            return false;
        }
        return true;
    }

    private boolean isAgeNull(String Ages) {
        if (Ages.equals("")) {
            mAuthProgressDialog.dismiss();
            age.setError("Please fill this field");
            return false;
        }
        return true;
    }

    private boolean isLocationNull(String locations) {
        if (locations.equals("")) {
            mAuthProgressDialog.dismiss();
            location.setError("Please fill this field");
            return false;
        }
        return true;
    }

    private boolean isWardNull(String Wards) {
        if (Wards.equals("")) {
            mAuthProgressDialog.dismiss();
            ward.setError("Please fill this field");
            return false;
        }
        return true;
    }

    private boolean isCountyNull(String Counties) {
        if (Counties.equals("")) {
            mAuthProgressDialog.dismiss();
            County.setError("Please fill this field");
            return false;
        }
        return true;
    }
}