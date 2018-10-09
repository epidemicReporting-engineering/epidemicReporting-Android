package com.reporting.epidemic.epidemicreporting.Model;

/**
 * Created by eleven on 2018/10/2.
 */

public class LeaderConfirmModel {

    private String dutyId;
    private int leaderPoint;
    private String leaderComment;

    public String getDutyId() {
        return dutyId;
    }

    public void setDutyId(String dutyId) {
        this.dutyId = dutyId;
    }

    public int getLeaderPoint() {
        return leaderPoint;
    }

    public void setLeaderPoint(int leaderPoint) {
        this.leaderPoint = leaderPoint;
    }

    public String getLeaderComment() {
        return leaderComment;
    }

    public void setLeaderComment(String leaderComment) {
        this.leaderComment = leaderComment;
    }
}
