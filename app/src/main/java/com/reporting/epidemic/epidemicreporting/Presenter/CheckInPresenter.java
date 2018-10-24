package com.reporting.epidemic.epidemicreporting.Presenter;

import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;
import com.reporting.epidemic.epidemicreporting.Views.ICheckInView;

/**
 * Created by eleven on 2018/10/14.
 */

public class CheckInPresenter {

    ICheckInView mCheckInView;

    public CheckInPresenter(ICheckInView iCheckInView) {
        this.mCheckInView = iCheckInView;
    }



    public void doCheckIn(String location, String latitude, String longitude, boolean isAbsence, boolean isAvailable) {
        DataService.getInstance().checkIn(location, latitude, longitude, isAbsence, isAvailable, new OnResponseListener(){

            @Override
            public void onSuccess(int code, Object response) {
                System.out.print("Get ALL CHECK IN SUCCESS");
                mCheckInView.onCheckInResult(true,code);
            }

            @Override
            public void onFailure(int code, String msg) {
                System.out.print("CHECK IN FAILED");
            }
        });
    }
}
