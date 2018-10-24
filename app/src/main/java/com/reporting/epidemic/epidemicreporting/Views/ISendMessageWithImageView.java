package com.reporting.epidemic.epidemicreporting.Views;

import com.reporting.epidemic.epidemicreporting.Model.AllReportsResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;

/**
 * Created by jianyu on 24/10/2018.
 */

public interface ISendMessageWithImageView {
    public void onGetSendMessageResult(int code, EpidemicSituationResponseModel response);
    public void onGetSendMessageResultFailed(int code);

}
