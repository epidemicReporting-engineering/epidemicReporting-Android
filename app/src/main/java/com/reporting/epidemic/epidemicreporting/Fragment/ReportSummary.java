package com.reporting.epidemic.epidemicreporting.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.imagepicker.bean.ImageItem;
import com.reporting.epidemic.epidemicreporting.Activity.ShowAllStatusOfOneReport;
import com.reporting.epidemic.epidemicreporting.Adapter.MyReporterAdapter;
import com.reporting.epidemic.epidemicreporting.App.EpidemicApplication;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.Model.AllReportsResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;
import com.reporting.epidemic.epidemicreporting.Presenter.MyReportsPresenter;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Utils.SharedPreferencesUtil;
import com.reporting.epidemic.epidemicreporting.Views.IMyReportsView;
import com.reporting.epidemic.epidemicreporting.Views.IRecyclerViewClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportSummary extends Fragment implements IMyReportsView, IRecyclerViewClick {


    @BindView(value = R.id.new_report)
    FloatingActionButton mNewReportButton;

    @BindView(value = R.id.my_report_recyclerView)
    RecyclerView recyclerView;

    MyReportsPresenter myReportsPresenter;

    List<EpidemicSituationResponseModel> dataList;

    MyReporterAdapter adapt;

    public ReportSummary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_report, container, false);
        mNewReportButton.setVisibility(View.INVISIBLE);
        myReportsPresenter = new MyReportsPresenter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        myReportsPresenter.getAllMyReports(null);
        return view;

    }

    @Override
    public void onGetMyReportsResult(int code, AllReportsResponseModel response) {
        dataList = response.getList();
        System.out.print(dataList);
        if (adapt == null) {
            adapt = new MyReporterAdapter(dataList, this);
            recyclerView.setAdapter(adapt);
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
