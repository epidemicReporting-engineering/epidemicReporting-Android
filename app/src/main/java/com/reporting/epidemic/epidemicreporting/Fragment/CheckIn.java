package com.reporting.epidemic.epidemicreporting.Fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.Model.CheckInRequestModel;
import com.reporting.epidemic.epidemicreporting.Presenter.CheckInPresenter;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Utils.LocationPermissionUtil;
import com.reporting.epidemic.epidemicreporting.Views.ICheckInView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckIn extends Fragment implements View.OnClickListener, ICheckInView, LocationSource, AMapLocationListener {

    @BindView(value = R.id.map)
    MapView mMapView;

    private AMap aMap;

    @BindView(value = R.id.calendarView)
    CalendarView mCv;

    @BindView(value = R.id.checkInImg)
    ImageView mCheckImage;

    @BindView(value = R.id.check_in_text)
    TextView mCheckText;

    private CheckInPresenter mCheckInPresenter;

    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    //声明mListener对象，定位监听器
    private OnLocationChangedListener mListener = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    //定位蓝点
    MyLocationStyle myLocationStyle;

    private boolean isFirstLoc = true;

    private CheckInRequestModel mCheckIn = null;


    public CheckIn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View CheckIn = inflater.inflate(R.layout.fragment_check_in, container, false);
        ButterKnife.bind(this, CheckIn);
        mMapView.onCreate(savedInstanceState);

        if(!LocationPermissionUtil.selfPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION, getActivity().getApplicationContext())){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.LOCATION_STATE);
        }

        if (aMap == null) {
            aMap = mMapView.getMap();
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


        }

        initLoc();

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

    //定位
    private void initLoc() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
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
//                mCheckInPresenter.doCheckIn("海曙区宝善路166号", "29.892328","121.368493", false, true);
                if (mCheckIn != null) {
                    mCheckInPresenter.doCheckIn(mCheckIn.getLocation(),mCheckIn.getLatitude(),mCheckIn.getLongitude(), false, true);
                }
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

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                if (mCheckIn == null) {
                    mCheckIn = new CheckInRequestModel();
                }
                mCheckIn.setLocation(aMapLocation.getAddress());
                mCheckIn.setLatitude(String.valueOf(aMapLocation.getLatitude()));
                mCheckIn.setLongitude(String.valueOf(aMapLocation.getLongitude()));

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length<=0){ return;}

        if (requestCode == Constants.LOCATION_STATE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //initPermission();

            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                //Toast.makeText(EntryActivity.this, "获取位置权限被禁用", Toast.LENGTH_SHORT).show();
                //finish();
            }
        }
    }
}
