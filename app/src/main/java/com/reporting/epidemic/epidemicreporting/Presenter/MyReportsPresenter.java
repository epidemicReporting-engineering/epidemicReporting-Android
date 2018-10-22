package com.reporting.epidemic.epidemicreporting.Presenter;

import android.util.Log;

import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;
import com.reporting.epidemic.epidemicreporting.Model.AllReportsResponseModel;
import com.reporting.epidemic.epidemicreporting.Views.ICheckInView;
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
        DataService.getInstance().getReportList("1", "1", "5", null, reporter, null, null, new OnResponseListener() {
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
