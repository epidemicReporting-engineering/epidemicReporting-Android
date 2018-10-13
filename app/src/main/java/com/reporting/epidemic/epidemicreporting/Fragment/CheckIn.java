package com.reporting.epidemic.epidemicreporting.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.amap.api.maps.MapView;
import com.reporting.epidemic.epidemicreporting.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckIn extends Fragment {

    @BindView(R.id.map)
    private MapView mMapView;

    @BindView(R.id.calendarView)
    private CalendarView mCv;

    public CheckIn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View CheckIn = inflater.inflate(R.layout.fragment_check_in, container, false);
        mMapView.onCreate(savedInstanceState);
        mCv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getActivity(), "birthday is:" + year + "-" + month + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
            }
        });
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

}
