package com.reporting.epidemic.epidemicreporting.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reporting.epidemic.epidemicreporting.Activity.ShowAllStatusOfOneReport;
import com.reporting.epidemic.epidemicreporting.Adapter.MyReporterAdapter;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.Model.AllReportsResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;
import com.reporting.epidemic.epidemicreporting.Presenter.MyReportsPresenter;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Views.IMyReportsView;
import com.reporting.epidemic.epidemicreporting.Views.IRecyclerViewClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportSummary extends Fragment implements IMyReportsView, IRecyclerViewClick, View.OnClickListener {


    @BindView(value = R.id.filter_report)
    FloatingActionButton mNewReportButton;


    @BindView(value = R.id.admin_report_recyclerView)
    RecyclerView recyclerView;

    MyReportsPresenter myReportsPresenter;

    List<EpidemicSituationResponseModel> dataList;

    List<EpidemicSituationResponseModel> filterDataList;

    MyReporterAdapter adapt;

    public ReportSummary() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_report:

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final String[] finalActions = new String[] {Constants.ADMIN_ALL, Constants.ADMIN_UNASIGN, Constants.ADMIN_ASIGN, Constants.ADMIN_BLOCK, Constants.ADMIN_FINISH, Constants.ADMIN_START, Constants.ADMIN_CANTDO, Constants.ADMIN_MARK};
                builder.setItems(finalActions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String clicked = finalActions[i];
                        if (clicked.equals(Constants.ADMIN_UNASIGN)) {
                            filterChange("0");
                        } else if (clicked.equals(Constants.ADMIN_BLOCK)){
                            filterChange("3");
                        } else if (clicked.equals(Constants.ADMIN_FINISH)) {
                            filterChange("4");
                        } else if (clicked.equals(Constants.ADMIN_START)) {
                            filterChange("2");
                        } else if (clicked.equals(Constants.ADMIN_CANTDO)) {
                            filterChange("6");
                        } else if (clicked.equals(Constants.ADMIN_MARK)) {
                            filterChange("5");
                        } else if (clicked.equals(Constants.ADMIN_ALL)) {
                            filterChange("ALL");
                        } else if (clicked.equals(Constants.ADMIN_ASIGN)) {
                            filterChange("1");
                        }
                    }
                }).show();
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_report_summary, container, false);
        ButterKnife.bind(this, view);
        mNewReportButton.setOnClickListener(this);
        //mNewReportButton.setVisibility(View.INVISIBLE);
        filterDataList = new ArrayList<EpidemicSituationResponseModel>();
        myReportsPresenter = new MyReportsPresenter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        myReportsPresenter.getAllMyReports(null);
        return view;

    }

    @Override
    public void onGetMyReportsResult(int code, AllReportsResponseModel response) {
        dataList = response.getList();

        for (EpidemicSituationResponseModel model: dataList) {
            filterDataList.add(model);
        }
            System.out.print(dataList);
        if (adapt == null) {
            adapt = new MyReporterAdapter(dataList, this);
            recyclerView.setAdapter(adapt);
        }
        adapt.notifyDataSetChanged();
    }

    private void filterChange(String status) {
        dataList.clear();


        for (EpidemicSituationResponseModel model: filterDataList) {
            if (status.equals("ALL")) {
                dataList.add(model);
            } else if (model.getDutyStatus().equals(status)) {
                dataList.add(model);
            }
        }

        adapt.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view) {
        final EpidemicSituationResponseModel data = dataList.get(Integer.valueOf(view.getTag().toString()));
        long dutyID = data.getId();
        Intent intent = new Intent(getActivity(), ShowAllStatusOfOneReport.class);
        intent.putExtra("dutyId", String.valueOf(dutyID));
        startActivity(intent);
    }
}
