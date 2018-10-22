package com.reporting.epidemic.epidemicreporting.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.reporting.epidemic.epidemicreporting.Model.AvailableUserResponseModel;
import com.reporting.epidemic.epidemicreporting.Presenter.AssignTaskPresenter;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Views.IAssignTaskView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AssignTask extends AppCompatActivity implements IAssignTaskView {

    @BindView(R.id.assignMap)
    private MapView mAssignMapView;

    private AMap aMap;

    private AssignTaskPresenter mAssignTaskPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);
        mAssignTaskPresenter = new AssignTaskPresenter(this);
        ButterKnife.bind(this);
        mAssignTaskPresenter.getAllAvaialbleEmpployees();
        if (aMap == null) {
            aMap = mAssignMapView.getMap();
        }
    }

    @Override
    public void onGetAllAvailableStuffsResult(final ArrayList<AvailableUserResponseModel> result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result != null && result.size() > 0) {
                    for (int i = 0; i < result.size(); i++) {
                        aMap.addMarker(new MarkerOptions().anchor(1.5f, 3.5f)
                                .position(new LatLng(Double.valueOf(result.get(i).getLatitude()),//设置纬度
                                        Double.valueOf(result.get(i).getLongitude())))//设置经度
                                .icon(BitmapDescriptorFactory.fromResource(R.id.assignMap)));
                    }
                }
            }
        });
    }
}
