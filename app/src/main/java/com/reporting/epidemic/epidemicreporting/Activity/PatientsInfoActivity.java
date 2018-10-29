package com.reporting.epidemic.epidemicreporting.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.reporting.epidemic.epidemicreporting.Adapter.MyMessageAdapter;
import com.reporting.epidemic.epidemicreporting.Adapter.PatientDetailsAdapter;
import com.reporting.epidemic.epidemicreporting.Components.URLImageView;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;
import com.reporting.epidemic.epidemicreporting.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatientsInfoActivity extends AppCompatActivity {

    @BindView(value = R.id.patients_details_company)
    AppCompatTextView mCompanyTv;
    @BindView(value = R.id.patients_details_department)
    AppCompatTextView mDepartmentTV;
    @BindView(value = R.id.patients_details_image_0)
    URLImageView mImage0;
    @BindView(value = R.id.patients_details_image_1)
    URLImageView mImage1;
    @BindView(value = R.id.patients_details_image_2)
    URLImageView mImage2;
    @BindView(value = R.id.patients_details_report_time)
    AppCompatTextView reportTime;
    @BindView(value = R.id.patients_details_patients_list)
    RecyclerView recyclerView;

    EpidemicSituationResponseModel data;
    PatientDetailsAdapter adapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_info);

        ButterKnife.bind(this);

        String gsonData = getIntent().getStringExtra(Constants.INTENT_PATIENT_DETAILS);

        Gson gson = new Gson();
        if (gsonData == null) {
            finish();
            return;
        } else {
            data = gson.fromJson(gsonData, EpidemicSituationResponseModel.class);
            if (data == null) {
                finish();
                return;
            }
        }

        mCompanyTv.setText("公司: " + data.getCompany());
        mDepartmentTV.setText("部门: " + data.getDepartment());
        if (data.getMultiMedia().size() > 0) {
            mImage0.setImageURL(data.getMultiMedia().get(0));
            if (data.getMultiMedia().size() > 1) {
                mImage0.setImageURL(data.getMultiMedia().get(1));
                if (data.getMultiMedia().size() > 2) {
                    mImage0.setImageURL(data.getMultiMedia().get(2));
                }
            }
        }


        adapt = new PatientDetailsAdapter(data.getPatients());
        recyclerView.setAdapter(adapt);

    }


}
