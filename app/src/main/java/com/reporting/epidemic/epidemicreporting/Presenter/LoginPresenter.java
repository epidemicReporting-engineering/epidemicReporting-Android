package com.reporting.epidemic.epidemicreporting.Presenter;

import com.reporting.epidemic.epidemicreporting.App.EpidemicApplication;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;
import com.reporting.epidemic.epidemicreporting.Model.UserProfileResponseModel;
import com.reporting.epidemic.epidemicreporting.Utils.SharedPreferencesUtil;
import com.reporting.epidemic.epidemicreporting.Views.ILoginView;

/**
 * Created by eleven on 2018/10/14.
 */

public class LoginPresenter {

    private ILoginView mLoginView;

    public LoginPresenter(ILoginView mLoginView) {
        this.mLoginView = mLoginView;
    }

    public void doLogin(final String usernme, final String password) {
        DataService.getInstance().loginUser(usernme, password, new OnResponseListener() {

            @Override
            public void onSuccess(int code, Object response) {
                DataService.getInstance().getProfile(usernme, new OnResponseListener() {
                    @Override
                    public void onSuccess(int code, Object response) {
                        UserProfileResponseModel profile = (UserProfileResponseModel)response;
                        SharedPreferencesUtil.getInstance(EpidemicApplication.getInstance().getAppContext()).put(Constants.CURRENT_ROLE,profile.getRole());
                        mLoginView.onLoginResult(true, 200);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mLoginView.onLoginResult(false, 400);
                    }
                });
            }

            @Override
            public void onFailure(int code, String msg) {
                mLoginView.onLoginResult(false, 400);
            }
        });
    }
}
