package com.reporting.epidemic.epidemicreporting.Views;

import com.reporting.epidemic.epidemicreporting.Model.AllReportsResponseModel;

/**
 * Created by jianyu on 19/10/2018.
 */

public interface IMyReportsView {

    public void onGetMyReportsResult(int code, AllReportsResponseModel response);

}
