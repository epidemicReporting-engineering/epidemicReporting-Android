package com.reporting.epidemic.epidemicreporting.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.reporting.epidemic.epidemicreporting.Activity.PatientsInfoActivity;
import com.reporting.epidemic.epidemicreporting.Activity.SendMessageActivity;
import com.reporting.epidemic.epidemicreporting.Adapter.MyMessageAdapter;
import com.reporting.epidemic.epidemicreporting.Adapter.MyReporterAdapter;
import com.reporting.epidemic.epidemicreporting.App.EpidemicApplication;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.Model.AllReportsResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.PatientResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.ReportStatusChangeDetailModel;
import com.reporting.epidemic.epidemicreporting.Presenter.MyMessagePresenter;
import com.reporting.epidemic.epidemicreporting.Presenter.MyReportsPresenter;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Utils.SharedPreferencesUtil;
import com.reporting.epidemic.epidemicreporting.Views.IMyMessageView;
import com.reporting.epidemic.epidemicreporting.Views.IMyReportsView;
import com.reporting.epidemic.epidemicreporting.Views.IRecyclerViewClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Message extends Fragment implements IMyMessageView, IRecyclerViewClick {

    @BindView(value = R.id.my_message_recyclerView)
    RecyclerView recyclerView;
    @BindView(value = R.id.my_message_upload_pb)
    ProgressBar pb;

    String user;

    MyMessagePresenter myReportsPresenter;

    List<EpidemicSituationResponseModel> dataList;

    MyMessageAdapter adapt;


    public Message() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        myReportsPresenter = new MyMessagePresenter(this);
        user = SharedPreferencesUtil.getInstance(EpidemicApplication.getInstance().getAppContext()).getSharedPreference(Constants.CURRENT_USER, "user").toString();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        myReportsPresenter.getAllReports();

        dataList = new ArrayList<EpidemicSituationResponseModel>();

        return view;
    }

    @Override
    public void onGetMyReportsResult(int code, AllReportsResponseModel response) {
        String role = SharedPreferencesUtil.getInstance(EpidemicApplication.getInstance().getAppContext()).getSharedPreference(Constants.CURRENT_ROLE, "role").toString();
        List<EpidemicSituationResponseModel> listResult = new ArrayList<EpidemicSituationResponseModel>();

        for (EpidemicSituationResponseModel model : response.getList()) {
            if (role.equals("STAFF")) {
                if (model.getDutyOwner().equals(user)) {
                    listResult.add(model);
                }
            } else {
                String s = model.getDutyStatus();
                if (s.equals("0") || s.equals("4") || s.equals("6")) {
                    listResult.add(model);
                }
            }
        }
        dataList.clear();
        dataList.addAll(listResult);
        adapt = new MyMessageAdapter(dataList, this);
        recyclerView.setAdapter(adapt);
        Log.d("change", "change");
        adapt.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        myReportsPresenter.getAllReports();
    }

    @Override
    public void onChangeDetailsResult(int code, boolean success) {
        pb.setVisibility(View.GONE);
        if (!success) {
            Toast.makeText(getActivity(), "提交失败", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(View view) {
        final EpidemicSituationResponseModel data = dataList.get(Integer.valueOf(view.getTag().toString()));
        String status = data.getDutyStatus();
        String[] actions = {Constants.SHOW_DETAILS, Constants.SHOW_PA};
        if (status.equals("0")) {
            actions = new String[]{Constants.SHOW_DETAILS, Constants.SHOW_PA, Constants.ASIGN};
        } else if (status.equals("6")) {
            actions = new String[]{Constants.SHOW_DETAILS, Constants.SHOW_PA, Constants.ASIGN};
        } else if (status.equals("1")) {
            actions = new String[]{Constants.SHOW_DETAILS, Constants.SHOW_PA, Constants.START, Constants.CANTDO, Constants.BLOCK};
        } else if (status.equals("2")) {
            actions = new String[]{Constants.SHOW_DETAILS, Constants.SHOW_PA, Constants.BLOCK, Constants.CANTDO, Constants.FINISH};
        } else if (status.equals("3")) {
            actions = new String[]{Constants.SHOW_DETAILS, Constants.SHOW_PA, Constants.START, Constants.CANTDO};
        } else if (status.equals("4")) {
            actions = new String[]{Constants.SHOW_DETAILS, Constants.SHOW_PA, Constants.MARK};
        } else {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] finalActions = actions;
        builder.setItems(finalActions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                long dutyID = data.getId();
                String clicked = finalActions[i];
                if (clicked.equals(Constants.SHOW_DETAILS)) {
                    // TODO: 显示疫情状态
                } else if (clicked.equals(Constants.SHOW_PA)) {
                    Intent intent = new Intent(getActivity(), PatientsInfoActivity.class);
                    Gson gson = new Gson();
                    String dataGsonString = gson.toJson(data);
                    if (dataGsonString == null) {
                        Toast.makeText(getActivity(), "没有病人信息被上传", Toast.LENGTH_LONG).show();
                    } else {
                        intent.putExtra(dataGsonString, Constants.INTENT_PATIENT_DETAILS);
                        startActivityForResult(intent, Constants.REPORT_PATIENT_DETAILS);
                    }
                } else if (clicked.equals(Constants.ASIGN)) {
                    // TODO: 分配
                } else {
                    String toStatusID = "1";
                    if (clicked.equals(Constants.START)) {
                        toStatusID = "2";
                        ReportStatusChangeDetailModel changeModel = new ReportStatusChangeDetailModel();
                        changeModel.setDutyId(dutyID);
                        changeModel.setDutyStatus(toStatusID);
                        pb.setVisibility(View.VISIBLE);
                        myReportsPresenter.changeDetails(changeModel);
                    } else if (clicked.equals(Constants.CANTDO)) {
                        toStatusID = "6";
                        Intent intent = new Intent(getActivity(), SendMessageActivity.class);
                        intent.putExtra("toStatusID", toStatusID);
                        intent.putExtra("dutyId", dutyID);
                        startActivityForResult(intent, Constants.REPORT_SEND_MESSAGE);
                    } else if (clicked.equals(Constants.BLOCK)) {
                        toStatusID = "3";
                        Intent intent = new Intent(getActivity(), SendMessageActivity.class);
                        intent.putExtra("toStatusID", toStatusID);
                        intent.putExtra("dutyId", dutyID);
                        startActivityForResult(intent, Constants.REPORT_SEND_MESSAGE);
                    } else if (clicked.equals(Constants.FINISH)) {
                        toStatusID = "4";
                        Intent intent = new Intent(getActivity(), SendMessageActivity.class);
                        intent.putExtra("toStatusID", toStatusID);
                        intent.putExtra("dutyId", dutyID);
                        startActivityForResult(intent, Constants.REPORT_SEND_MESSAGE);
                    } else if (clicked.equals(Constants.MARK)) {
                        toStatusID = "5";
                        Intent intent = new Intent(getActivity(), SendMessageActivity.class);
                        intent.putExtra("toStatusID", toStatusID);
                        intent.putExtra("dutyId", dutyID);
                        startActivityForResult(intent, Constants.REPORT_SEND_MESSAGE);
                    }
                }

            }
        }).show();
    }
}
