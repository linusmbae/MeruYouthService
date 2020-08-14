package org.meruyouthservice.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.meruyouthservice.CustomOnItemSelectedListener;
import org.meruyouthservice.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity3.class.getSimpleName();
    @BindView(R.id.btnSubmit2) Button mSubmit2;
    @BindView(R.id.structure) Spinner structure;
    @BindView(R.id.personalDetails2) TextView pDetail2;
    @BindView(R.id.opportunity_levels) EditText opportunity_levels;
    @BindView(R.id.Property) Spinner Property;
    @BindView(R.id.type) EditText type;
    @BindView(R.id.family_size) EditText family_size;
    @BindView(R.id.physically_challenged) EditText physically_challenged;

//    private String userName;
    private String name1;
    private String age1;
    private String location1;
    private String ward1;
    private String County1;

    private String SubCounty;
    private String Skills;
    private String Zone;
    private String Education;
    private String ProffSkills;

    private String item;
    private String typ;
    private String Opportunity1;
    private String Property1;
    private String FamSize;
    private String Challenge;

    private ProgressDialog mAuthProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        Intent intent=getIntent();
//        userName=intent.getStringExtra("UserName");
        name1=intent.getStringExtra("name");
        age1=intent.getStringExtra("age");
        location1=intent.getStringExtra("location");
        ward1=intent.getStringExtra("ward");
        County1=intent.getStringExtra("County");

        SubCounty=intent.getStringExtra("SubCounty");
        Skills=intent.getStringExtra("Skills");
        Zone=intent.getStringExtra("Zone");
        Education=intent.getStringExtra("Education");
        ProffSkills=intent.getStringExtra("ProffSkills");

        createAuthProgressDialog();
        ButterKnife.bind(this);
        mSubmit2.setOnClickListener(this);

        itemSelectionListener();
    }

    private void itemSelectionListener() {
        Property.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getItemAtPosition(position).equals("Motor Vehicle")||parent.getItemAtPosition(position).equals("Motor Bike")) {
                    Property1=Property.getSelectedItem().toString();
                    type.setVisibility(View.VISIBLE);
                    Property.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        if (v==mSubmit2){
            submitAnswers2();
        }
    }

    private void submitAnswers2() {
        Opportunity1=opportunity_levels.getText().toString();
        FamSize=family_size.getText().toString();
        Challenge=physically_challenged.getText().toString();
        item=structure.getSelectedItem().toString();
        typ = type.getText().toString();

        boolean nullOpportunity = isOpportunityNull(SubCounty);
        boolean nullFamSize = isFamilySizeNull(Zone);
        boolean nullChallenged = isChallengedNull(Education);

        if (!nullOpportunity || !nullFamSize || !nullChallenged)return;

        mAuthProgressDialog.show();

        if (item.equals("--Choose Structure Condition--")){
            Toast.makeText(this, "Please choose structure condition", Toast.LENGTH_SHORT).show();
            mAuthProgressDialog.dismiss();
        }else if (Property1.equals("--Select Property--")){
            Toast.makeText(MainActivity3.this, "Please choose Property", Toast.LENGTH_SHORT).show();
            mAuthProgressDialog.dismiss();
        }else  {

        new SweetAlertDialog(MainActivity3.this,SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Submission Successful")
                .show();

        Intent intent=new Intent(MainActivity3.this, MainActivity4.class);
//        intent.putExtra("UserName",userName);
        intent.putExtra("name",name1);
        intent.putExtra("age",age1);
        intent.putExtra("location",location1);
        intent.putExtra("ward",ward1);
        intent.putExtra("County",County1);

        intent.putExtra("SubCounty",SubCounty);
        intent.putExtra("Skills",Skills);
        intent.putExtra("Zone",Zone);
        intent.putExtra("Education",Education);
        intent.putExtra("ProffSkills",ProffSkills);

        intent.putExtra("item",item);
        intent.putExtra("Opportunity",Opportunity1);
        intent.putExtra("Property",Property1+" (Type: "+typ+")");
        intent.putExtra("FamSize",FamSize);
        intent.putExtra("Challenge",Challenge);
        startActivity(intent);
        }
    }

    private boolean isOpportunityNull(String Opportunity) {
        if (Opportunity.equals("")) {
            mAuthProgressDialog.dismiss();
            opportunity_levels.setError("Please fill this field");
            return false;
        }
        return true;
    }


    private boolean isFamilySizeNull(String familySize) {
        if (familySize.equals("")) {
            mAuthProgressDialog.dismiss();
            family_size.setError("Please fill this field");
            return false;
        }
        return true;
    }

    private boolean isChallengedNull(String Challenged) {
        if (Challenged.equals("")) {
            mAuthProgressDialog.dismiss();
            physically_challenged.setError("Please fill this field");
            return false;
        }
        return true;
    }
}