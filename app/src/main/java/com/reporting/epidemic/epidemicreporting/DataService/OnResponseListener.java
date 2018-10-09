package com.reporting.epidemic.epidemicreporting.DataService;

/**
 * Created by eleven on 2018/10/1.
 */

public interface OnResponseListener {

    void onSuccess(int code, Object response);

    void onFailure(int code, String msg);
}
