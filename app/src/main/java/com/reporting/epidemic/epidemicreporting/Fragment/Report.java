package com.reporting.epidemic.epidemicreporting.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;
import com.reporting.epidemic.epidemicreporting.ImageLoader.ProgressRequestBody;
import com.reporting.epidemic.epidemicreporting.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Report extends Fragment implements View.OnClickListener, ProgressRequestBody.UploadCallbacks {

    @BindView(value = R.id.new_report)
    FloatingActionButton mNewReportButton;

    ProgressBar progressBar;

    List<Uri> mSelected;

    public Report() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        ButterKnife.bind(this, view);
        mNewReportButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_report:
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, Constants.IMAGE_PICKER);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constants.IMAGE_PICKER) {
                // TODO: multiple image upload, need to upload into another ancivity
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (ImageItem item: images) {
                    File newFile = new File(item.path);
                    ProgressRequestBody request = new ProgressRequestBody(newFile,this);
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

            } else {
                Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onProgressUpdate(int percentage) {
        System.out.print("the percentage is: " + percentage);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }
}
