package org.meruyouthservice.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.meruyouthservice.CustomOnItemSelectedListener;
import org.meruyouthservice.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btnSubmit1) Button mSubmit1;
    @BindView(R.id.sub_county) Spinner subCounty;
    @BindView(R.id.skill) EditText skills;
    @BindView(R.id.zone) Spinner zone;
    @BindView(R.id.education_level) EditText education_level;
    @BindView(R.id.professional_skills) EditText professional_skills;
    private ProgressDialog mAuthProgressDialog;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ButterKnife.bind(this);

        Intent intent=getIntent();
//        userName=intent.getStringExtra("UserName");
        name1=intent.getStringExtra("name");
        age1=intent.getStringExtra("age");
        location1=intent.getStringExtra("location");
        ward1=intent.getStringExtra("ward");
        County1=intent.getStringExtra("County");

        createAuthProgressDialog();
        mSubmit1.setOnClickListener(this);
    }

    private void createAuthProgressDialog() {
        mAuthProgressDialog = new ProgressDialog(this);
        mAuthProgressDialog.setTitle("Meru Youth Service");
        mAuthProgressDialog.setMessage("Please Wait....");
        mAuthProgressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        if (v==mSubmit1){
            submitAnswers1();
        }
    }

    private void submitAnswers1() {
        SubCounty=subCounty.getSelectedItem().toString();
        Skills=skills.getText().toString();
        Zone=zone.getSelectedItem().toString();
        Education=education_level.getText().toString();
        ProffSkills=professional_skills.getText().toString();

        boolean nullSkill = isSkillNull(Skills);
        boolean nullEducation = isEducation(Education);
        boolean nullProffSkills = isProffSkillsNull(ProffSkills);
        if (!nullSkill || !nullEducation || !nullProffSkills)return;

        mAuthProgressDialog.show();

        if (Zone.equals("--Select Zone--")){
            mAuthProgressDialog.dismiss();
            Toast.makeText(this, "Please Select Zone", Toast.LENGTH_SHORT).show();
        }else if (subCounty.equals("--Select Sub_County--")){
            mAuthProgressDialog.dismiss();
            Toast.makeText(this, "Please Select Sub_County", Toast.LENGTH_SHORT).show();
        }else {
            new SweetAlertDialog(MainActivity2.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Submission Successful")
                    .show();

            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
//        intent.putExtra("UserName",userName);
            intent.putExtra("name", name1);
            intent.putExtra("age", age1);
            intent.putExtra("location", location1);
            intent.putExtra("ward", ward1);
            intent.putExtra("County", County1);

            intent.putExtra("SubCounty", SubCounty);
            intent.putExtra("Skills", Skills);
            intent.putExtra("Zone", Zone);
            intent.putExtra("Education", Education);
            intent.putExtra("ProffSkills", ProffSkills);
            startActivity(intent);
        }
    }


    private boolean isSkillNull(String Skill) {
        if (Skill.equals("")) {
            mAuthProgressDialog.dismiss();
            skills.setError("Please fill this field");
            return false;
        }
        return true;
    }

    private boolean isEducation(String Edu) {
        if (Edu.equals("")) {
            mAuthProgressDialog.dismiss();
            education_level.setError("Please fill this field");
            return false;
        }
        return true;
    }

    private boolean isProffSkillsNull(String Proff) {
        if (Proff.equals("")) {
            mAuthProgressDialog.dismiss();
            professional_skills.setError("Please fill this field");
            return false;
        }
        return true;
    }
}