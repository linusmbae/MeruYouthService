package org.meruyouthservice.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.meruyouthservice.R;
import org.meruyouthservice.models.Questions;
import org.meruyouthservice.services.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity4 extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity3.class.getSimpleName();
    @BindView(R.id.btnSubmit3) Button mSubmit3;
    @BindView(R.id.personalDetails3) TextView pDetail3;
    @BindView(R.id.Health_condition) EditText Health_condition;
    @BindView(R.id.regional_activities) EditText regional_activities;
    @BindView(R.id.access_to_technology)Spinner access_to_technology;
    @BindView(R.id.incomeSources)Spinner incomeSources;
    @BindView(R.id.rage)Spinner incomeRage;
    @BindView(R.id.farming)Spinner farming;
    @BindView(R.id.cropType)Spinner cropType;
    @BindView(R.id.challenges_in_the_area) EditText challenges_in_the_area;
    @BindView(R.id.liveStock) EditText liveStock;
    @BindView(R.id.cropsTtype) EditText cropsType;

    private String userName;
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
    private String Opportunity1;
    private String Property1;
    private String FamSize;
    private String Challenge;

    private String HealthCondition;
    private String RegionalActivities;
    private String AccessToTechnology;
    private String IncomeSources;
    private String IncomeRage;
    private String Farming;
    private String ChallengesInTheArea;
    private String incomeSelection=null;
    private String LiveStock;
    private String CropType;
    private String CropsType;


    private ProgressDialog mAuthProgressDialog;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseUser user;

    private Questions questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Intent intent=getIntent();
        userName=intent.getStringExtra("UserName");
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

        item=intent.getStringExtra("item");
        Opportunity1=intent.getStringExtra("Opportunity");
        Property1=intent.getStringExtra("Property");
        FamSize=intent.getStringExtra("FamSize");
        Challenge=intent.getStringExtra("Challenge");
        getSupportActionBar().setTitle("Welcome "+userName);

        createAuthProgressDialog();
        ButterKnife.bind(this);
        mSubmit3.setOnClickListener(this);
        itemSelectionListener();
    }

    private void itemSelectionListener() {
        incomeSources.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Salary")){
                    IncomeSources=incomeSources.getSelectedItem().toString();
                    incomeRage.setVisibility(View.VISIBLE);
                    incomeSources.setVisibility(View.INVISIBLE);
                }else if (parent.getItemAtPosition(position).equals("Farming")){
                    IncomeSources=incomeSources.getSelectedItem().toString();
                    farming.setVisibility(View.VISIBLE);
                    incomeSources.setVisibility(View.INVISIBLE);

                    farming.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (parent.getItemAtPosition(position).equals("Livestocks")){
                                Farming=farming.getSelectedItem().toString();
                                farming.setVisibility(View.INVISIBLE);
                                liveStock.setVisibility(View.VISIBLE);
                            }else if (parent.getItemAtPosition(position).equals("Crops")){
                                Farming=farming.getSelectedItem().toString();
                                farming.setVisibility(View.INVISIBLE);
                                cropType.setVisibility(View.VISIBLE);
                                cropType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if (parent.getItemAtPosition(position).equals("Cash Crops")){
                                            CropType=cropType.getSelectedItem().toString();
                                            cropType.setVisibility(View.INVISIBLE);
                                            cropsType.setVisibility(View.VISIBLE);
                                        }else if (parent.getItemAtPosition(position).equals("Food Crops")){
                                            CropType=cropType.getSelectedItem().toString();
                                            cropType.setVisibility(View.INVISIBLE);
                                            cropsType.setVisibility(View.VISIBLE);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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
        if (v==mSubmit3){
            submitAnswersToFirebase();
        }
    }

    private void submitAnswersToFirebase() {
        HealthCondition=Health_condition.getText().toString();
        RegionalActivities=regional_activities.getText().toString();
        AccessToTechnology=access_to_technology.getSelectedItem().toString();
        IncomeRage=incomeRage.getSelectedItem().toString();
        ChallengesInTheArea=challenges_in_the_area.getText().toString();

        boolean nullHealthCondition = isHealthConditionNull(HealthCondition);
        boolean nullRegionalActivities = isRegionalActivitiesNull(RegionalActivities);
        boolean nullChallengesInTheArea = isChallengesInTheAreaNull(ChallengesInTheArea);

        if (!nullHealthCondition || !nullRegionalActivities || !nullChallengesInTheArea)return;

        mAuthProgressDialog.show();

        if (AccessToTechnology.equals("--Select Technology Access--")){
            Toast.makeText(this, "Kindly Select Access to Technology", Toast.LENGTH_SHORT).show();
            mAuthProgressDialog.dismiss();
        }else if (IncomeSources.equals("--Select Income Source--")){
            Toast.makeText(this, "Kindly Select Income Source", Toast.LENGTH_SHORT).show();
            mAuthProgressDialog.dismiss();
        }else{

            if (IncomeSources.equals("Salary")&&!IncomeRage.equals("--Select Income Rage--")){
                incomeSelection=IncomeSources+" (Range: "+IncomeRage+" )";
            }else if (IncomeSources.equals("Farming")&&Farming.equals("Livestocks")&&!Farming.equals("--Select Farming Type--")){
                LiveStock=liveStock.getText().toString();
                boolean nullLiveStock=isLiveStockNull(LiveStock);
                if (!nullLiveStock)return;
                incomeSelection=IncomeSources+" (Type of Farming: "+Farming+" (Type Of Livestock: "+LiveStock+" ))";
            }else if (IncomeSources.equals("Farming")&&Farming.equals("Crops ")&&CropType.equals("Cash Crops")){
                CropsType=cropsType.getText().toString();
                boolean nullCropType=isCropTypeNull(CropsType);
                if (!nullCropType)return;
                incomeSelection=IncomeSources+"(Type of Farming: "+Farming+" (Crop type: "+CropType+" <"+CropsType+"> ) )";
            }else if (IncomeSources.equals("Farming")&&Farming.equals("Crops ")&&CropType.equals("Food Crops")){
                CropsType=cropsType.getText().toString();
                boolean nullCropType=isCropTypeNull(CropsType);
                if (!nullCropType)return;
                incomeSelection=IncomeSources+"(Type of Farming: "+Farming+" (Crop type: "+CropType+" <"+CropsType+"> ) )";
            }

            questions = new Questions("Posted By: "+userName,name1, age1, location1, ward1, County1, SubCounty, Skills, Zone, Education, ProffSkills, Opportunity1, item, Property1, FamSize, Challenge, HealthCondition, RegionalActivities, AccessToTechnology,incomeSelection, ChallengesInTheArea);

            rootNode = FirebaseDatabase.getInstance();

            reference = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_MYS);

            DatabaseReference pushRef = reference.push();
            pushRef.setValue(questions);

            mAuthProgressDialog.dismiss();

            new SweetAlertDialog(MainActivity4.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Submission Successful")
                    .show();

            Intent intent = new Intent(MainActivity4.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean isHealthConditionNull(String healthCondition) {
        if (healthCondition.equals("")) {
            mAuthProgressDialog.dismiss();
            Health_condition.setError("Please fill this field");
            return false;
        }
        return true;
    }

    private boolean isRegionalActivitiesNull(String Regional) {
        if (Regional.equals("")) {
            mAuthProgressDialog.dismiss();
            regional_activities.setError("Please fill this field");
            return false;
        }
        return true;
    }

    private boolean isChallengesInTheAreaNull(String Challenged) {
        if (Challenged.equals("")) {
            mAuthProgressDialog.dismiss();
            challenges_in_the_area.setError("Please fill this field");
            return false;
        }
        return true;
    }

    private boolean isLiveStockNull(String LiveStock) {
        if (LiveStock.equals("")) {
            mAuthProgressDialog.dismiss();
            liveStock.setError("Please fill this field");
            return false;
        }
        return true;
    }

    private boolean isCropTypeNull(String CropType) {
        if (CropType.equals("")) {
            mAuthProgressDialog.dismiss();
            cropsType.setError("Please fill this field");
            return false;
        }
        return true;
    }

}