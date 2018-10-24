package com.reporting.epidemic.epidemicreporting.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.reporting.epidemic.epidemicreporting.Activity.ReportDutyActivity;
import com.reporting.epidemic.epidemicreporting.Activity.SendMessageWithImageActivity;
import com.reporting.epidemic.epidemicreporting.Adapter.MyReporterAdapter;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;
import com.reporting.epidemic.epidemicreporting.ImageLoader.ProgressRequestBody;
import com.reporting.epidemic.epidemicreporting.Model.AllReportsResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationRequestModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;
import com.reporting.epidemic.epidemicreporting.Presenter.MyReportsPresenter;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Views.IMyReportsView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Report extends Fragment implements View.OnClickListener, ProgressRequestBody.UploadCallbacks, IMyReportsView {

    @BindView(value = R.id.new_report)
    FloatingActionButton mNewReportButton;

    @BindView(value = R.id.my_report_recyclerView)
    RecyclerView recyclerView;

    ProgressBar progressBar;

    List<Uri> mSelected;

    MyReportsPresenter myReportsPresenter;

    List<EpidemicSituationResponseModel> dataList;

    ArrayList<ImageItem> images;

    MyReporterAdapter adapt;

    public Report() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        ButterKnife.bind(this, view);
        mNewReportButton.setOnClickListener(this);

        myReportsPresenter = new MyReportsPresenter(this);

        // TODO: - 获取真实当前用户 userName
        myReportsPresenter.getAllMyReports("user001");

        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_report:
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, Constants.IMAGE_PICKER);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constants.IMAGE_PICKER) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Intent intent = new Intent(getActivity(), ReportDutyActivity.class);
                startActivityForResult(intent, Constants.REPORT_DUTY);
            } else {
                Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == Constants.REPORT_DUTY) {
//            Gson gson = new Gson();
            String dutyReportGson = data.getStringExtra(Constants.INTENT_DUTY_REPORT_GJSON);
//            EpidemicSituationRequestModel dutyReportDataModel = gson.fromJson(dutyReportGson, EpidemicSituationRequestModel.class);
            Intent intent = new Intent(getActivity(), SendMessageWithImageActivity.class);
            intent.putExtra(Constants.INTENT_IMAGES, images);
            intent.putExtra(Constants.INTENT_DUTY_REPORT_GJSON, dutyReportGson);
            startActivityForResult(intent, Constants.REPORT_SEND_MESSAGE_WITH_IMAGES);
        } else {
            myReportsPresenter.getAllMyReports("user001");
        }
    }

    @Override
    public void onProgressUpdate(int percentage) {
        System.out.print("the percentage is: " + percentage);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }


    @Override
    public void onGetMyReportsResult(int code, AllReportsResponseModel response) {
        dataList = response.getList();
        if (adapt == null) {
            adapt = new MyReporterAdapter(dataList);
            recyclerView.setAdapter(adapt);
        }
        adapt.notifyDataSetChanged();

    }
}
