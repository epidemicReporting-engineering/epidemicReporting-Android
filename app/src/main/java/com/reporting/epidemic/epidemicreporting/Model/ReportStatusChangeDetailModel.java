package com.reporting.epidemic.epidemicreporting.Model;

import java.util.ArrayList;

/**
 * Created by eleven on 2018/10/2.
 */

public class ReportStatusChangeDetailModel {

    private long dutyId;
    private String dutyOwner;
    private String dutyOwnerName;
    private String dutyDescription;
    private String dutyStatus;
    private int leaderPoint;
    private String leaderComment;
    private ArrayList<String> dutyMultiMedia;

    public String getDutyOwnerName() {
        return dutyOwnerName;
    }

    public void setDutyOwnerName(String dutyOwnerName) {
        this.dutyOwnerName = dutyOwnerName;
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

    public long getDutyId() {
        return dutyId;
    }

    public void setDutyId(long dutyId) {
        this.dutyId = dutyId;
    }

    public String getDutyOwner() {
        return dutyOwner;
    }

    public void setDutyOwner(String dutyOwner) {
        this.dutyOwner = dutyOwner;
    }

    public String getDutyDescription() {
        return dutyDescription;
    }

    public void setDutyDescription(String dutyDescription) {
        this.dutyDescription = dutyDescription;
    }

    public String getDutyStatus() {
        return dutyStatus;
    }

    public void setDutyStatus(String dutyStatus) {
        this.dutyStatus = dutyStatus;
    }

    public ArrayList<String> getDutyMultiMedia() {
        return dutyMultiMedia;
    }

    public void setDutyMultiMedia(ArrayList<String> dutyMultiMedia) {
        this.dutyMultiMedia = dutyMultiMedia;
    }
}
