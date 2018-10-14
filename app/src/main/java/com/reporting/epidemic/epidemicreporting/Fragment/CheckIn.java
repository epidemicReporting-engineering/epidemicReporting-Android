package com.reporting.epidemic.epidemicreporting.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.MapView;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.Presenter.CheckInPresenter;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Views.ICheckInView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckIn extends Fragment implements View.OnClickListener, ICheckInView {

    @BindView(value = R.id.map)
    MapView mMapView;

    @BindView(value = R.id.calendarView)
    CalendarView mCv;

    @BindView(value = R.id.checkInImg)
    ImageView mCheckImage;

    @BindView(value = R.id.check_in_text)
    TextView mCheckText;

    private CheckInPresenter mCheckInPresenter;

    public CheckIn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View CheckIn = inflater.inflate(R.layout.fragment_check_in, container, false);
        ButterKnife.bind(this, CheckIn);
        mMapView.onCreate(savedInstanceState);
        mCv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getActivity(), "birthday is:" + year + "-" + month + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
            }
        });

        mCheckImage.setOnClickListener(this);
        mCheckInPresenter = new CheckInPresenter(this);

        return CheckIn;
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onPause() {
        Log.i("sys", "mf onPause");
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i("sys", "mf onSaveInstanceState");
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        // String location, String latitude, String longitude, boolean isAbsence, boolean isAvailable
        switch (view.getId()) {
            case R.id.checkInImg:
                mCheckInPresenter.doCheckIn("海曙区宝善路166号", "29.892328","121.368493", false, true);
                break;
        }
    }

    @Override
    public void onCheckInResult(Boolean result, int code) {
        if (result) {
            changeCheckStatusView();
        }
    }

    public void changeCheckStatusView() {
        mCheckText.setText(Constants.CHECK_SUCCESS);
    }
}
