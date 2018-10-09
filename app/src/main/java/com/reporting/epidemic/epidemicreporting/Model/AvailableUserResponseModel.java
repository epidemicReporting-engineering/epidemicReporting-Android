package com.reporting.epidemic.epidemicreporting.Model;

/**
 * Created by eleven on 2018/10/4.
 */

public class AvailableUserResponseModel {

    private String username;
    private String location;
    private String latitude;
    private String longitude;
    private int dutyNums;
    private String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getDutyNums() {
        return dutyNums;
    }

    public void setDutyNums(int dutyNums) {
        this.dutyNums = dutyNums;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
