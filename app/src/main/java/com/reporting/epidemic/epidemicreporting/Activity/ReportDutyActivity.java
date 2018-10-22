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
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_duty);
        ButterKnife.bind(this);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportDutyActivity.this, ReportPatientActivity.class);
                startActivityForResult(intent, Constants.REPORT_ADD_PATIENT);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
