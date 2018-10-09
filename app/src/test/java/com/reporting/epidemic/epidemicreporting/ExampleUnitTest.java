package com.reporting.epidemic.epidemicreporting;

import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        System.out.print("test purpose");
        assertEquals(4, 2 + 2);
    }

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