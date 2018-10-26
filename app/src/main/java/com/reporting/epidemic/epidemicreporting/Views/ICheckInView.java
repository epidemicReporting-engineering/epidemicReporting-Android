package com.reporting.epidemic.epidemicreporting.Views;

/**
 * Created by eleven on 2018/10/14.
 */

public interface ICheckInView {

    public void onCheckInResult(Boolean result, int code);

    public void onCheckInStatus(Boolean result);
}
