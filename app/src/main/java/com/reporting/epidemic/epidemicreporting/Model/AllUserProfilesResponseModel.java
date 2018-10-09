package com.reporting.epidemic.epidemicreporting.Model;

import java.util.ArrayList;

/**
 * Created by eleven on 2018/10/3.
 */

public class AllUserProfilesResponseModel {

    private int page;
    private int total;
    private ArrayList<UserProfileResponseModel> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<UserProfileResponseModel> getList() {
        return list;
    }

    public void setList(ArrayList<UserProfileResponseModel> list) {
        this.list = list;
    }
}
