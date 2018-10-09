package com.reporting.epidemic.epidemicreporting.Model;

import java.util.ArrayList;

/**
 * Created by eleven on 2018/10/2.
 */

public class EpidemicSituationResponseModel {

    private long id;
    private String location; // 地址
    private String latitude; // 纬度
    private String longitude; // 经度
    private ArrayList<String> multiMedia; // 照片
    private String description; // 描述
    private long reportTime; // 汇报的时间
    private long happenTime; // 发生时间： example 1530581929574
    private String company; // 地点，example: 定山桥村
    private String department; // 村几组
    private String reporter; // 汇报者id
    private String reporterName; // 用户名
    private String dutyStatus; //状态
    private String dutyOwner; // 责任人id
    private String dutyOwnerName; // 责任人名称
    private String dutyDescription;
    private ArrayList<String> dutyMultiMedia;
    private String leaderPoint;
    private String leaderComment;
    private long processTime;
    private ArrayList<PatientResponseModel> patients;

    public ArrayList<String> getDutyMultiMedia() {
        return dutyMultiMedia;
    }

    public void setDutyMultiMedia(ArrayList<String> dutyMultiMedia) {
        this.dutyMultiMedia = dutyMultiMedia;
    }

    public String getLeaderPoint() {
        return leaderPoint;
    }

    public void setLeaderPoint(String leaderPoint) {
        this.leaderPoint = leaderPoint;
    }

    public String getDutyDescription() {
        return dutyDescription;
    }

    public void setDutyDescription(String dutyDescription) {
        this.dutyDescription = dutyDescription;
    }

    public String getLeaderComment() {
        return leaderComment;
    }

    public void setLeaderComment(String leaderComment) {
        this.leaderComment = leaderComment;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDutyStatus() {
        return dutyStatus;
    }

    public void setDutyStatus(String dutyStatus) {
        this.dutyStatus = dutyStatus;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getDutyOwner() {
        return dutyOwner;
    }

    public void setDutyOwner(String dutyOwner) {
        this.dutyOwner = dutyOwner;
    }

    public String getDutyOwnerName() {
        return dutyOwnerName;
    }

    public void setDutyOwnerName(String dutyOwnerName) {
        this.dutyOwnerName = dutyOwnerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ArrayList<String> getMultiMedia() {
        return multiMedia;
    }

    public void setMultiMedia(ArrayList<String> multiMedia) {
        this.multiMedia = multiMedia;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public ArrayList<PatientResponseModel> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<PatientResponseModel> patients) {
        this.patients = patients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getReportTime() {
        return reportTime;
    }

    public void setReportTime(long reportTime) {
        this.reportTime = reportTime;
    }

    public long getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(long happenTime) {
        this.happenTime = happenTime;
    }
}
