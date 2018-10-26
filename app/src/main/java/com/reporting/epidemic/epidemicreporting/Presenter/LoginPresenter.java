package com.reporting.epidemic.epidemicreporting.Presenter;

import com.reporting.epidemic.epidemicreporting.DataService.DataService;
import com.reporting.epidemic.epidemicreporting.DataService.OnResponseListener;
import com.reporting.epidemic.epidemicreporting.Views.ILoginView;

/**
 * Created by eleven on 2018/10/14.
 */

public class LoginPresenter {

    private ILoginView mLoginView;

    public LoginPresenter(ILoginView mLoginView) {
        this.mLoginView = mLoginView;
    }

    public void doLogin(String usernmae, String password) {
        DataService.getInstance().loginUser(usernmae, password, new OnResponseListener() {

            @Override
            public void onSuccess(int code, Object response) {
                mLoginView.onLoginResult(true, 200);
            }

            @Override
            public void onFailure(int code, String msg) {
                mLoginView.onLoginResult(false, 400);
            }
        });
    }
}
