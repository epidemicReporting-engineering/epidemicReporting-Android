package com.reporting.epidemic.epidemicreporting.Model;

/**
 * Created by eleven on 2018/10/3.
 */

public class UserProfileResponseModel {

    private String username;
    private String name;
    private String role;
    private String sex;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
