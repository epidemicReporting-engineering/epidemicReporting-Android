package com.reporting.epidemic.epidemicreporting.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.google.gson.Gson;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationRequestModel;
import com.reporting.epidemic.epidemicreporting.Model.PatientRequestModel;
import com.reporting.epidemic.epidemicreporting.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportDutyActivity extends AppCompatActivity {

    @BindView(value = R.id.report_duty_company_tv)
    EditText mCompanyEt;
    @BindView(value = R.id.report_duty_department_tv)
    EditText mDepartEt;
    @BindView(value = R.id.report_duty_datePicker)
    DatePicker mHappenTimeDp;
    @BindView(value = R.id.report_duty_button)
    Button mAddButton;
    @BindView(value = R.id.paitients_list)
    TextView mPatientstv;

    private EpidemicSituationRequestModel dataModel;
    ArrayList<PatientRequestModel> patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_duty);
        ButterKnife.bind(this);

        patients = new ArrayList<>();
        dataModel = new EpidemicSituationRequestModel();

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportDutyActivity.this, ReportPatientActivity.class);
                startActivityForResult(intent, Constants.REPORT_ADD_PATIENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.REPORT_ADD_PATIENT) {
            Gson gson = new Gson();
            String patientGson = data.getStringExtra(Constants.INTENT_PATIENT_GJSON);
            PatientRequestModel patientModel = gson.fromJson(patientGson, PatientRequestModel.class);
            if (patientModel.getName().length() > 0) {
                patients.add(patientModel);
                String patientsNames = mPatientstv.getText().toString();
                if (patientsNames.length() > 0) {
                    mPatientstv.setText(patientsNames + "," + patientModel.getName());
                } else {
                    mPatientstv.setText(patientModel.getName());
                }
            }
        }
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
                addDutyReport();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addDutyReport() {
        dataModel.setCompany(mCompanyEt.getText().toString());
        dataModel.setPatients(patients);
        dataModel.setDepartment(mDepartEt.getText().toString());
        int y = mHappenTimeDp.getYear();
        int m = mHappenTimeDp.getMonth();
        int d = mHappenTimeDp.getDayOfMonth();
        Calendar calendar = new GregorianCalendar(y, m, d);
        dataModel.setHappenTime(calendar.getTimeInMillis());
        Intent intent = new Intent();
        Gson gson = new Gson();
        String gsonDataModel = gson.toJson(dataModel);
        intent.putExtra(Constants.INTENT_DUTY_REPORT_GJSON, gsonDataModel);
        setResult(Constants.REPORT_DUTY, intent);
        finish();
    }

}
