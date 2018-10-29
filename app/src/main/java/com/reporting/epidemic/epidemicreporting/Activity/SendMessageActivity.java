package com.reporting.epidemic.epidemicreporting.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.Model.AllReportsResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.ReportStatusChangeDetailModel;
import com.reporting.epidemic.epidemicreporting.Presenter.MyMessagePresenter;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Views.IMyMessageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendMessageActivity extends AppCompatActivity implements IMyMessageView {

    @BindView(value = R.id.send_message_only_upload_pb)
    ProgressBar pb;
    @BindView(value = R.id.send_message_image_desc_et_word_only)
    AppCompatEditText mEdit;

    MyMessagePresenter myReportsPresenter;

    long dutyId;
    String changeToStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        ButterKnife.bind(this);

        String dutyReportGson = getIntent().getStringExtra(Constants.INTENT_DUTY_REPORT_GJSON);
        dutyId = getIntent().getLongExtra("dutyId", 0);
        changeToStatus = getIntent().getStringExtra("toStatusID");
        myReportsPresenter = new MyMessagePresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.report_send_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                ReportStatusChangeDetailModel changeModel = new ReportStatusChangeDetailModel();
                changeModel.setDutyId(dutyId);
                changeModel.setDutyStatus(changeToStatus);
                pb.setVisibility(View.VISIBLE);
                myReportsPresenter.changeDetails(changeModel);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onGetMyReportsResult(int code, AllReportsResponseModel response) {

    }

    @Override
    public void onChangeDetailsResult(int code, boolean success) {
        finish();
    }
}
