package com.reporting.epidemic.epidemicreporting.Presenter;

import com.reporting.epidemic.epidemicreporting.App.EpidemicApplication;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;
import com.reporting.epidemic.epidemicreporting.Model.CheckedInUserInfoResponseModel;
import com.reporting.epidemic.epidemicreporting.Utils.SharedPreferencesUtil;
import com.reporting.epidemic.epidemicreporting.Views.ICheckInView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    public void doCheckMyCheckInstatus() {
        Calendar rightNow = Calendar.getInstance();
        Integer year = rightNow.get(Calendar.YEAR);
        Integer month = rightNow.get(Calendar.MONTH)+1;

        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        final String date=sDateFormat.format(new Date());

        String user = SharedPreferencesUtil.getInstance(EpidemicApplication.getInstance().getAppContext()).getSharedPreference(Constants.CURRENT_USER, "user").toString();
        DataService.getInstance().getCheckInStatusForOneMonth(year.toString(), month.toString(), user, new OnResponseListener() {
            @Override
            public void onSuccess(int code, Object response) {
                if (code == 200) {
                    ArrayList<CheckedInUserInfoResponseModel> checkedInDays  = (ArrayList<CheckedInUserInfoResponseModel>)response;
                    if (checkedInDays.size() > 0) {
                        for (CheckedInUserInfoResponseModel user: checkedInDays) {
                            if (date.equals(user.getDate()) && user.getId() != null) {
                                mCheckInView.onCheckInStatus(true);
                                return;
                            }
                        }
                    }
                    mCheckInView.onCheckInStatus(false);
                }
            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });



        DataService.getInstance().getAllCheckInStatusForToday(new OnResponseListener() {

            @Override
            public void onSuccess(int code, Object response) {

            }

            @Override
            public void onFailure(int code, String msg) {
                mCheckInView.onCheckInStatus(false);
            }
        });
    }
}
