package com.reporting.epidemic.epidemicreporting.Views;

import com.reporting.epidemic.epidemicreporting.Model.AllReportsResponseModel;

/**
 * Created by jianyu on 28/10/2018.
 */

public interface IMyMessageView {

    public void onGetMyReportsResult(int code, AllReportsResponseModel response);

    public void onChangeDetailsResult(int code, boolean success);
}
