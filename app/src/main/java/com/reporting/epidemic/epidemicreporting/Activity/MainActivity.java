package com.reporting.epidemic.epidemicreporting.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.reporting.epidemic.epidemicreporting.DataService.ServiceSampleCalls;
import com.reporting.epidemic.epidemicreporting.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServiceSampleCalls.getInstance().sampleConfirmProcessEpidemicSituation();
//        ServiceSampleCalls.getInstance().sampleLogin();

    }
}
