package com.reporting.epidemic.epidemicreporting.Model;

import java.util.ArrayList;

/**
 * Created by eleven on 2018/10/2.
 */

public class EpidemicSituationRequestModel {

    String location; // 地址
    String latitude; // 纬度
    String longitude; // 经度
    ArrayList<String> multiMedia; // 照片
    String description; // 描述
    long happenTime; // 发生时间： example 1530581929574
    String company; // 地点，example: 定山桥村
    String department; // 村几组
    ArrayList<PatientRequestModel> patients;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(long happenTime) {
        this.happenTime = happenTime;
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

    public ArrayList<PatientRequestModel> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<PatientRequestModel> patients) {
        this.patients = patients;
    }
}
