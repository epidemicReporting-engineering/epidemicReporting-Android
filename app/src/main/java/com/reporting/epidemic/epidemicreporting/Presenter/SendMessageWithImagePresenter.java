package com.reporting.epidemic.epidemicreporting.Presenter;

import android.util.Log;

import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;
import com.reporting.epidemic.epidemicreporting.Model.AllReportsResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationRequestModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;
import com.reporting.epidemic.epidemicreporting.Views.IMyReportsView;
import com.reporting.epidemic.epidemicreporting.Views.ISendMessageWithImageView;

/**
 * Created by jianyu on 24/10/2018.
 */

public class SendMessageWithImagePresenter {

    ISendMessageWithImageView mSendMsgView;

    public SendMessageWithImagePresenter(ISendMessageWithImageView ismview) {
        this.mSendMsgView = ismview;
    }

    public void sendMessage(EpidemicSituationRequestModel requestModel) {

        DataService.getInstance().reportEpidemicSituation(requestModel, new OnResponseListener() {
            @Override
            public void onSuccess(int code, Object response) {
                mSendMsgView.onGetSendMessageResult(code, (EpidemicSituationResponseModel) response);
            }

            @Override
            public void onFailure(int code, String msg) {
                mSendMsgView.onGetSendMessageResultFailed(code);
            }
        });

//        DataService.getInstance().getReportList("1", "1", "5", null, reporter, null, null, new OnResponseListener() {
//            @Override
//            public void onSuccess(int code, Object response) {
//                mMyReportsView.onGetMyReportsResult(code, (AllReportsResponseModel)response);
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//                Log.d("IMyReportsView", msg);
//            }
//        });
    }


}
