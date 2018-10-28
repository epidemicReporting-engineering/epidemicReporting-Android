package com.reporting.epidemic.epidemicreporting.Presenter;

import android.util.Log;

import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;
import com.reporting.epidemic.epidemicreporting.Model.AllReportsResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.ReportStatusChangeDetailModel;
import com.reporting.epidemic.epidemicreporting.Views.IMyMessageView;

/**
 * Created by jianyu on 28/10/2018.
 */

public class MyMessagePresenter {
    IMyMessageView mMessageView;

    public MyMessagePresenter(IMyMessageView iv) {
        this.mMessageView = iv;
    }

    public void getAllReports() {
        DataService.getInstance().getReportList(null, null, null, null, null, null, null, new OnResponseListener() {
            @Override
            public void onSuccess(int code, Object response) {
                mMessageView.onGetMyReportsResult(code, (AllReportsResponseModel)response);
            }

            @Override
            public void onFailure(int code, String msg) {
                Log.d("IMyReportsView", msg);
            }
        });
    }

    public void changeDetails(ReportStatusChangeDetailModel model) {
        DataService.getInstance().processReport(model, new OnResponseListener() {
            @Override
            public void onSuccess(int code, Object response) {
                mMessageView.onChangeDetailsResult(code, true);
            }

            @Override
            public void onFailure(int code, String msg) {
                mMessageView.onChangeDetailsResult(code, false);

            }
        });
    }
}
