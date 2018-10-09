package com.reporting.epidemic.epidemicreporting;

import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;

import org.junit.Test;

/**
 * Created by eleven on 2018/10/2.
 */


public class DataServiceUnitTest {

    @Test
    public void loginUser() {
        DataService.getInstance().loginUser("user001","123456", new OnResponseListener(){
            @Override
            public void onSuccess(int code, String response) {
                if (code == Constants.API_SUCCESS_CODE) {
                    System.out.print("login sucess:" + response);
                }
            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });
    }
}
