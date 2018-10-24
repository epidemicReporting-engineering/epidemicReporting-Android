package com.reporting.epidemic.epidemicreporting.Model;

/**
 * Created by eleven on 2018/10/24.
 */

public class CheckInRequestModel {

    private String location;
    private String latitude;
    private String longitude;
    private boolean isAbsence;
    private boolean isAvailable;

    public CheckInRequestModel() {
    }

    public CheckInRequestModel(String location, String latitude, String longitude, boolean isAbsence, boolean isAvailable) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isAbsence = isAbsence;
        this.isAvailable = isAvailable;
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

    public boolean getIsAbsence() {
        return isAbsence;
    }

    public void setIsAbsence(boolean absence) {
        isAbsence = absence;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }
}
