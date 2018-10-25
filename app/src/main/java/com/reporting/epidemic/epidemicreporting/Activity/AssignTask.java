package com.reporting.epidemic.epidemicreporting.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.Model.AvailableUserResponseModel;
import com.reporting.epidemic.epidemicreporting.Presenter.AssignTaskPresenter;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Utils.LocationPermissionUtil;
import com.reporting.epidemic.epidemicreporting.Views.IAssignTaskView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AssignTask extends AppCompatActivity implements IAssignTaskView, LocationSource, AMapLocationListener,AMap.OnMarkerClickListener {

    @BindView(R.id.assignMap)
    MapView mAssignMapView;

    private AMap aMap;

    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    //声明mListener对象，定位监听器
    private LocationSource.OnLocationChangedListener mListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    //定位蓝点
    MyLocationStyle myLocationStyle;

    private AssignTaskPresenter mAssignTaskPresenter;

    private ArrayList<AvailableUserResponseModel> mCurrentUser;

    private String dutyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);
        ButterKnife.bind(this);
        mAssignMapView.onCreate(savedInstanceState);

        mAssignTaskPresenter = new AssignTaskPresenter(this);
        mAssignTaskPresenter.getAllAvaialbleEmpployees();

        if(!LocationPermissionUtil.selfPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION, getApplicationContext())){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.LOCATION_STATE);
        }

        if (aMap == null) {
            aMap = mAssignMapView.getMap();
            aMap.setLocationSource(this);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(13));

            //蓝点初始化
            myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            aMap.getUiSettings().setMyLocationButtonEnabled(true);
            aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
            myLocationStyle.showMyLocation(true);

            aMap.setOnMarkerClickListener(this);

            //TODO: get the duty is
            Intent intent = getIntent();
            dutyId=intent.getStringExtra("dutyId");
            dutyId = "2";
        }

        initLoc();
    }

    //定位
    private void initLoc() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onGetAllAvailableStuffsResult(final ArrayList<AvailableUserResponseModel> result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result != null && result.size() > 0) {
                    mCurrentUser = result;
                    for (int i = 0; i < result.size(); i++) {
                        aMap.addMarker(new MarkerOptions().anchor(1.5f, 3.5f)
                                .position(new LatLng(Double.valueOf(result.get(i).getLatitude()),//设置纬度
                                        Double.valueOf(result.get(i).getLongitude())))//设置经度
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.doctor)));
                    }
                }
            }
        });
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                Log.e("位置：", String.valueOf(aMapLocation.getLatitude()) + " and " + String.valueOf(aMapLocation.getLongitude()));
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String latitude = String.valueOf(marker.getPosition().latitude);
        String longitude = String.valueOf(marker.getPosition().longitude);
        if (mCurrentUser !=null && mCurrentUser.size() > 0) {
            for (AvailableUserResponseModel user: mCurrentUser) {
                if (user.getLongitude().equals(longitude) && user.getLatitude().equals(latitude)){
                    showSureOrCancleDialog(this, user.getName(), user.getUsername(),user.getLocation());
                    break;
                }
            }
        }
        return false;
    }

    public void showSureOrCancleDialog(final Context context, final String userName, final String userId, final String location) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("处理人员：" + userName).setMessage(location)//
                .setCancelable(false)
                .setNegativeButton("取消", null)//
                .setPositiveButton("分配", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mAssignTaskPresenter.assignToEmployee(userId, dutyId);
                    }
                });
        builder.create().show();
    }

    @Override
    public void onAssignTaskResutl(boolean result) {
        // TODO:
        if (result) {
            System.out.print("the result is:" + result);
        }
    }
}
