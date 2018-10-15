package com.reporting.epidemic.epidemicreporting.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.reporting.epidemic.epidemicreporting.DataService.ServiceSampleCalls;
import com.reporting.epidemic.epidemicreporting.Fragment.CheckIn;
import com.reporting.epidemic.epidemicreporting.Fragment.Message;
import com.reporting.epidemic.epidemicreporting.Fragment.Report;
import com.reporting.epidemic.epidemicreporting.Fragment.ReportSummary;
import com.reporting.epidemic.epidemicreporting.R;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    //声明四个Tab的布局文件
    private LinearLayout mCheckIn;
    private LinearLayout mReport;
    private LinearLayout mMessage;
    private LinearLayout mReportSummary;

    //声明四个Tab的ImageButton
    private ImageButton mCheckInImg;
    private ImageButton mReportImg;
    private ImageButton mMessageImg;
    private ImageButton mReportSummaryImg;


    //声明四个Tab分别对应的Fragment
    private Fragment mFragCheckIn;
    private Fragment mFragReport;
    private Fragment mFragMessage;
    private Fragment mFragReportSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews();//初始化控件
        initEvents();//初始化事件
        selectTab(0);//默认选中第一个Tab
        ServiceSampleCalls.getInstance().sampleLogin();
//        ServiceSampleCalls.getInstance().sampleConfirmProcessEpidemicSituation();
//        ServiceSampleCalls.getInstance().sampleLogin();

    }

    private void initViews() {
        //初始化四个Tab的布局文件
        mCheckIn = (LinearLayout) findViewById(R.id.id_tab_weixin);
        mReport = (LinearLayout) findViewById(R.id.id_tab_frd);
        mMessage = (LinearLayout) findViewById(R.id.id_tab_address);
        mReportSummary = (LinearLayout) findViewById(R.id.id_tab_setting);

        //初始化四个ImageButton
        mCheckInImg = (ImageButton) findViewById(R.id.id_tab_weixin_img);
        mReportImg = (ImageButton) findViewById(R.id.id_tab_frd_img);
        mMessageImg = (ImageButton) findViewById(R.id.id_tab_address_img);
        mReportSummaryImg = (ImageButton) findViewById(R.id.id_tab_setting_img);
    }



    private void initEvents() {
        //初始化四个Tab的点击事件
        mCheckIn.setOnClickListener(this);
        mReport.setOnClickListener(this);
        mMessage.setOnClickListener(this);
        mReportSummary.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        resetImgs();

        switch (view.getId()) {
            case R.id.id_tab_weixin:
                selectTab(0);//当点击的是签到的Tab就选中签到的Tab
                break;
            case R.id.id_tab_frd:
                selectTab(1);
                break;
            case R.id.id_tab_address:
                selectTab(2);
                break;
            case R.id.id_tab_setting:
                selectTab(3);
                break;
        }

    }

    //进行选中Tab的处理
    private void selectTab(int i) {
        //获取FragmentManager对象
        FragmentManager manager = getSupportFragmentManager();
        //获取FragmentTransaction对象
        FragmentTransaction transaction = manager.beginTransaction();
        //先隐藏所有的Fragment
        hideFragments(transaction);
        switch (i) {
            case 0:
                //设置签到的ImageButton为绿色
                mCheckInImg.setImageResource(R.mipmap.tab_weixin_pressed);
                //如果签到对应的Fragment没有实例化，则进行实例化，并显示出来
                if (mFragCheckIn == null) {
                    mFragCheckIn = new CheckIn();
                    transaction.add(R.id.id_content, mFragCheckIn);
                } else {
                    transaction.show(mFragCheckIn);
                }
                break;
            case 1:
                mReportImg.setImageResource(R.mipmap.tab_find_frd_pressed);
                if (mFragReport == null) {
                    mFragReport = new Report();
                    transaction.add(R.id.id_content, mFragReport);
                } else {
                    transaction.show(mFragReport);
                }
                break;
            case 2:
                mMessageImg.setImageResource(R.mipmap.tab_address_pressed);
                if (mFragMessage == null) {
                    mFragMessage = new Message();
                    transaction.add(R.id.id_content, mFragMessage);
                } else {
                    transaction.show(mFragMessage);
                }
                break;
            case 3:
                mReportSummaryImg.setImageResource(R.mipmap.tab_settings_pressed);
                if (mFragReportSummary == null) {
                    mFragReportSummary = new ReportSummary();
                    transaction.add(R.id.id_content, mFragReportSummary);
                } else {
                    transaction.show(mFragReportSummary);
                }
                break;
        }
        transaction.commit();
    }

    //将四个的Fragment隐藏
    private void hideFragments(FragmentTransaction transaction) {
        if (mFragCheckIn != null) {
            transaction.hide(mFragCheckIn);
        }
        if (mFragReport != null) {
            transaction.hide(mFragReport);
        }
        if (mFragMessage != null) {
            transaction.hide(mFragMessage);
        }
        if (mFragReportSummary != null) {
            transaction.hide(mFragReportSummary);
        }
    }

    private void resetImgs() {
        mCheckInImg.setImageResource(R.mipmap.tab_weixin_normal);
        mReportImg.setImageResource(R.mipmap.tab_find_frd_normal);
        mMessageImg.setImageResource(R.mipmap.tab_address_normal);
        mReportSummaryImg.setImageResource(R.mipmap.tab_settings_normal);
    }
}
