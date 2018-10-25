package com.reporting.epidemic.epidemicreporting.Presenter;

import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;
import com.reporting.epidemic.epidemicreporting.Model.AvailableUserResponseModel;
import com.reporting.epidemic.epidemicreporting.Views.IAssignTaskView;

import java.util.ArrayList;

/**
 * Created by eleven on 2018/10/22.
 */

public class AssignTaskPresenter {

    private IAssignTaskView mAssignTaskView;

    public AssignTaskPresenter(IAssignTaskView assignTaskView) {
        mAssignTaskView = assignTaskView;
    }

    public void getAllAvaialbleEmpployees(){

        DataService.getInstance().getAllAvailableStuffs(new OnResponseListener() {

            @Override
            public void onSuccess(int code, Object response) {
                if (response != null) {
                    ArrayList<AvailableUserResponseModel> obj = (ArrayList<AvailableUserResponseModel>) response;
                    mAssignTaskView.onGetAllAvailableStuffsResult(obj);
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mAssignTaskView.onGetAllAvailableStuffsResult(null);
            }
        });

    }

    public void assignToEmployee(String userid, String dutyId) {
        DataService.getInstance().assignReport(dutyId, userid, new OnResponseListener() {

            @Override
            public void onSuccess(int code, Object response) {
                if (code == Constants.API_SUCCESS_CODE) {
                    mAssignTaskView.onAssignTaskResutl(true);
                } else {
                    mAssignTaskView.onAssignTaskResutl(false);
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                mAssignTaskView.onAssignTaskResutl(false);
            }
        });
    }
}
