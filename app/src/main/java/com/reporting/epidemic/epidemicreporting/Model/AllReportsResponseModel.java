package com.reporting.epidemic.epidemicreporting.Model;

import java.util.List;

/**
 * Created by eleven on 2018/10/16.
 */

public class AllReportsResponseModel {

    private int page;

    private int total;

    private List<EpidemicSituationResponseModel> list;

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

    public List<EpidemicSituationResponseModel> getList() {
        return list;
    }

    public void setList(List<EpidemicSituationResponseModel> list) {
        this.list = list;
    }
}
