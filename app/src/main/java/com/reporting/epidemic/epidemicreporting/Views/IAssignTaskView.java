package com.reporting.epidemic.epidemicreporting.Views;

import com.reporting.epidemic.epidemicreporting.Model.AvailableUserResponseModel;

import java.util.ArrayList;

/**
 * Created by eleven on 2018/10/14.
 */

public interface IAssignTaskView {

    public void onGetAllAvailableStuffsResult(ArrayList<AvailableUserResponseModel> result);


    public void onAssignTaskResutl(boolean result);
}
