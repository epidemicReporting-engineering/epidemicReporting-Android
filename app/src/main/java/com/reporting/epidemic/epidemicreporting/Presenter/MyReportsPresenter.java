package com.reporting.epidemic.epidemicreporting.Presenter;

import android.util.Log;

import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;
import com.reporting.epidemic.epidemicreporting.Model.AllReportsResponseModel;
import com.reporting.epidemic.epidemicreporting.Views.IMyReportsView;

/**
 * Created by jianyu on 19/10/2018.
 */

public class MyReportsPresenter {

    IMyReportsView mMyReportsView;

    public MyReportsPresenter(IMyReportsView iMyReportsView) {
        this.mMyReportsView = iMyReportsView;
    }

    public void getAllMyReports(String reporter) {
        DataService.getInstance().getReportList(null, null, null, null, reporter, null, null, new OnResponseListener() {
            @Override
            public void onSuccess(int code, Object response) {
                mMyReportsView.onGetMyReportsResult(code, (AllReportsResponseModel)response);
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.d("IMyReportsView", msg);
            }
        });
    }

    public void getAllMyReportsByStatus(String status) {
        DataService.getInstance().getReportList(status, null, null, null, null, null, null, new OnResponseListener() {
            @Override
            public void onSuccess(int code, Object response) {
                mMyReportsView.onGetMyReportsResult(code, (AllReportsResponseModel)response);
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.d("IMyReportsView", msg);
            }
        });
    }


}
