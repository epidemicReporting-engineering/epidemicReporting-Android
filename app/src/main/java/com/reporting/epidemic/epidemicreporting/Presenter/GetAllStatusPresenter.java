package com.reporting.epidemic.epidemicreporting.Presenter;

import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationAllStatusModel;
import com.reporting.epidemic.epidemicreporting.Views.IGetAllStatusView;

/**
 * Created by eleven on 28/10/2018.
 */

public class GetAllStatusPresenter {

    IGetAllStatusView mGetAllStatusView;

    public GetAllStatusPresenter(IGetAllStatusView iGetAllStatusView) {
        this.mGetAllStatusView = iGetAllStatusView;
    }

    public void GetAllStatus(String dutyId) {
        DataService.getInstance().getAllStatusForOneReport(dutyId, new OnResponseListener() {

            @Override
            public void onSuccess(int code, Object response) {
                mGetAllStatusView.onGetAllStatusViewResult((EpidemicSituationAllStatusModel)response);
            }

            @Override
            public void onFailure(int code, String msg) {
                mGetAllStatusView.onGetAllStatusViewResult(null);
            }
        });
    }

}
