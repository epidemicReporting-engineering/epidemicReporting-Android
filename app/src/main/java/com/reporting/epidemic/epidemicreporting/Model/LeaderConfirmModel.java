package com.reporting.epidemic.epidemicreporting.Model;

/**
 * Created by eleven on 2018/10/2.
 */

public class LeaderConfirmModel {

    private long dutyId;
    private int leaderPoint;
    private String leaderComment;

    public long getDutyId() {
        return dutyId;
    }

    public void setDutyId(long dutyId) {
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
