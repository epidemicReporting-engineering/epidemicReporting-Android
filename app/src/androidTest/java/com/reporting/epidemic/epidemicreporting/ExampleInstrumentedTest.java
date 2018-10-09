package com.reporting.epidemic.epidemicreporting;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.reporting.epidemic.epidemicreporting", appContext.getPackageName());
    }

    @Test
    public void testLoginUser() {
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
