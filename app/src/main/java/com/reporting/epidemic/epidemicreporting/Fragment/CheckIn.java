package com.reporting.epidemic.epidemicreporting.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps.MapView;
import com.reporting.epidemic.epidemicreporting.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckIn extends Fragment {

    MapView mMapView = null;

    public CheckIn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View CheckIn =  inflater.inflate(R.layout.fragment_check_in, container, false);
        mMapView = (MapView) CheckIn.findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
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
