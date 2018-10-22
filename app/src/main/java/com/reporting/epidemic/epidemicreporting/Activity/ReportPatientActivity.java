package com.reporting.epidemic.epidemicreporting.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.amap.api.maps.MapView;
import com.reporting.epidemic.epidemicreporting.Model.PatientRequestModel;
import com.reporting.epidemic.epidemicreporting.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportPatientActivity extends AppCompatActivity {

    @BindView(value = R.id.report_patient_name_tv)
    EditText mNameEt;
    @BindView(value = R.id.report_patient_checkBox_male)
    CheckBox mGenderMale;
    @BindView(value = R.id.report_patient_checkBox_female)
    CheckBox mGenderFemale;
    @BindView(value = R.id.report_patient_career_tv)
    EditText mCareerEt;
    @BindView(value = R.id.report_patient_age_tv)
    EditText mAgeEt;
    @BindView(value = R.id.report_patient_method_tv)
    EditText mMethodEt;
    @BindView(value = R.id.report_patient_symptom_group_head)
    CheckBox mSymptomHead;
    @BindView(value = R.id.report_patient_symptom_group_fear)
    CheckBox mSymptomFear;
    @BindView(value = R.id.report_patient_symptom_group_vomit)
    CheckBox mSymptomVomit;
    @BindView(value = R.id.report_patient_symptom_group_lax)
    CheckBox mSymptomLax;
    @BindView(value = R.id.report_patient_symptom_group_shock)
    CheckBox mSymptomShock;
    @BindView(value = R.id.report_patient_symptom_group_db)
    CheckBox mSymptomDB;
    @BindView(value = R.id.report_patient_symptom_group_other)
    CheckBox mSymptomOther;
    @BindView(value = R.id.report_patient_level_high)
    CheckBox mLevelHigh;
    @BindView(value = R.id.report_patient_level_mid)
    CheckBox mLevelMid;
    @BindView(value = R.id.report_patient_level_low)
    CheckBox mLevelLow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_patient);
        ButterKnife.bind(this);

        mGenderMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mGenderFemale.setChecked(false);
                }
            }
        });

        mGenderFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mGenderMale.setChecked(false);
                }
            }
        });

        mLevelHigh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mLevelMid.setChecked(false);
                    mLevelLow.setChecked(false);
                }
            }
        });

        mLevelMid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mLevelHigh.setChecked(false);
                    mLevelLow.setChecked(false);
                }
            }
        });

        mLevelLow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mLevelMid.setChecked(false);
                    mLevelHigh.setChecked(false);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.report_duty_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_complete:
                Log.d("Menu selected", "complete");
//                addPatient();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addPatient() {

    }
}
