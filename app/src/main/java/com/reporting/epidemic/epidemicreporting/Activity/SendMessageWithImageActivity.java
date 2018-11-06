package com.reporting.epidemic.epidemicreporting.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.google.gson.Gson;
import com.lzy.imagepicker.bean.ImageItem;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;
import com.reporting.epidemic.epidemicreporting.ImageLoader.ProgressRequestBody;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationRequestModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.ImageUploaderResponseModel;
import com.reporting.epidemic.epidemicreporting.Presenter.SendMessageWithImagePresenter;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Views.ISendMessageWithImageView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.reporting.epidemic.epidemicreporting.R;

public class SendMessageWithImageActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks, ISendMessageWithImageView, LocationSource, AMapLocationListener {

    @BindView(value = R.id.send_message_image_desc_et)
    AppCompatEditText mDescEt;
    @BindView(value = R.id.send_message_iamge_upload_pb)
    ProgressBar mDataUploadPb;
    @BindView(value = R.id.send_message_image_upload_image_pb)
    ProgressBar mImageUploadPb;
    @BindView(value = R.id.send_message_user_location_tv)
    TextView mLocationTv;
    @BindView(value = R.id.send_message_image_image)
    AppCompatImageView mImageView;

    ArrayList<ImageItem> images;

    SendMessageWithImagePresenter mPresenter;

    EpidemicSituationRequestModel dutyReportDataModel;

    ArrayList<String> imagesUUID = new ArrayList<String>();

    int countOfUploadedImages = 0;

    private OnLocationChangedListener mListener = null;

    double la;
    double lo;
    String location;

    boolean uploadImageSuccess = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message_with_image);
        ButterKnife.bind(this);

        mPresenter = new SendMessageWithImagePresenter(this);
        String dutyReportGson = getIntent().getStringExtra(Constants.INTENT_DUTY_REPORT_GJSON);
        Gson gson = new Gson();
        dutyReportDataModel = gson.fromJson(dutyReportGson, EpidemicSituationRequestModel.class);
        images = (ArrayList<ImageItem>) getIntent().getSerializableExtra(Constants.INTENT_IMAGES);
//        if (images != null || images.size() > 0) {
//        }
        uploadImages();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.report_send_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                send();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void uploadImages() {
        if (images == null || images.size() == 0) {
            return;
        }
        final int countOfIMages = images.size();
        for (ImageItem item: images) {
            File newFile = new File(item.path);
            ProgressRequestBody request = new ProgressRequestBody(newFile, this);
            DataService.getInstance().uploadImage(request, new OnResponseListener(){
                @Override
                public void onSuccess(int code, Object response) {
                    System.out.print("==============================");
                    System.out.print(response);
                    ImageUploaderResponseModel responseModel = (ImageUploaderResponseModel) response;
                    imagesUUID.add(responseModel.getUuid());
                    countOfUploadedImages ++;
                    if (countOfUploadedImages == countOfIMages) {
                        uploadImageSuccess = true;
                    }
                }

                @Override
                public void onFailure(int code, String msg) {
                    System.out.print("==============================");
                    System.out.print("Upload image failed");
                }
            });
        }
    }

    private void send() {
        if (!uploadImageSuccess) {
            Toast.makeText(this, "请等待图片上传完成", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mDescEt.getText().toString().length() == 0) {
            Toast.makeText(this, "请输入详情描述", Toast.LENGTH_SHORT).show();
        } else {
            if (dutyReportDataModel == null) {
                finish();
            } else {
                dutyReportDataModel.setDescription(mDescEt.getText().toString());
                dutyReportDataModel.setLatitude(String.valueOf(la));
                dutyReportDataModel.setLongitude(String.valueOf(lo));
                dutyReportDataModel.setLocation(location);
                dutyReportDataModel.setMultiMedia(imagesUUID.size() == 0 ? null : imagesUUID);
                mDataUploadPb.setVisibility(View.VISIBLE);
                mPresenter.sendMessage(dutyReportDataModel);
            }
        }
    }

    @Override
    public void onProgressUpdate(int percentage) {
        System.out.print("the percentage is: " + percentage);
        Log.d("the percentage is: ", String.valueOf(percentage));
        mImageUploadPb.setProgress(percentage);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "上传图片失败", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onGetSendMessageResult(int code, EpidemicSituationResponseModel response) {
        finish();
    }

    @Override
    public void onGetSendMessageResultFailed(int code) {
        Toast.makeText(this, "上传数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
        mLocationTv.setText(aMapLocation.getAddress());
        location = aMapLocation.getAddress();
        la = aMapLocation.getLatitude();
        lo = aMapLocation.getLongitude();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }
}
