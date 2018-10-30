package com.reporting.epidemic.epidemicreporting.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.reporting.epidemic.epidemicreporting.Adapter.ReporterAllStatusAdapter;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationAllStatusModel;
import com.reporting.epidemic.epidemicreporting.Presenter.GetAllStatusPresenter;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Views.IGetAllStatusView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowAllStatusOfOneReport extends AppCompatActivity implements IGetAllStatusView {

    @BindView(value = R.id.report_all_status_recyclerView)
    RecyclerView recyclerView;

    private GetAllStatusPresenter mGetAllStatusPresenter;

    private ReporterAllStatusAdapter mAdapt;

    private EpidemicSituationAllStatusModel data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_status_of_one_report);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (mGetAllStatusPresenter == null) {
            mGetAllStatusPresenter = new GetAllStatusPresenter(this);
        }

        //TODO: get the duty is
        Intent intent = getIntent();
        String dutyId = intent.getStringExtra("dutyId");
        mGetAllStatusPresenter.GetAllStatus(dutyId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.show_patients_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_patients_details:

                // to patients details
                Intent intent = new Intent(this, PatientsInfoActivity.class);
                Gson gson = new Gson();
                String dataGsonString = gson.toJson(data);
                if (dataGsonString == null) {
                    Toast.makeText(this, "没有病人信息被上传", Toast.LENGTH_LONG).show();
                } else {
                    intent.putExtra(dataGsonString, Constants.INTENT_PATIENT_DETAILS);
                    startActivityForResult(intent, Constants.REPORT_PATIENT_DETAILS);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onGetAllStatusViewResult(EpidemicSituationAllStatusModel response) {
        data = response;
        if (mAdapt == null && response != null) {
            mAdapt = new ReporterAllStatusAdapter(response);
            recyclerView.setAdapter(mAdapt);
            mAdapt.notifyDataSetChanged();
        } else if (mAdapt != null) {
            mAdapt.notifyDataSetChanged();
        }
    }
}
