package com.reporting.epidemic.epidemicreporting.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.Text;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
//import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;
import com.reporting.epidemic.epidemicreporting.ImageLoader.ProgressRequestBody;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationRequestModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;
import com.reporting.epidemic.epidemicreporting.Presenter.SendMessageWithImagePresenter;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Views.ISendMessageWithImageView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendMessageWithImageActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks, ISendMessageWithImageView {

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
        if (images != null || images.size() > 0) {
            // TODO: set image

        }
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

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void uploadImages() {
        if (images == null || images.size() == 0) {
            return;
        }
        for (ImageItem item: images) {
            File newFile = new File(item.path);
            ProgressRequestBody request = new ProgressRequestBody(newFile, this);
            // TODO: need to move this part the presenter
            DataService.getInstance().uploadImage(request, new OnResponseListener(){
                @Override
                public void onSuccess(int code, Object response) {
                    System.out.print("Upload image success");
                }

                @Override
                public void onFailure(int code, String msg) {
                    System.out.print("Upload image failed");
                }
            });
        }
    }

    private void send() {
        // TODO：- get user location before send message
        if (mDescEt.getText().toString().length() == 0) {
            Toast.makeText(this, "请输入详情描述", Toast.LENGTH_SHORT).show();
        } else {
            if (dutyReportDataModel == null) {
                finish();
            } else {
                mPresenter.sendMessage(dutyReportDataModel);
            }
        }
    }

    @Override
    public void onProgressUpdate(int percentage) {
        System.out.print("the percentage is: " + percentage);
//        mImageUploadPb.setProgress();
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
}
