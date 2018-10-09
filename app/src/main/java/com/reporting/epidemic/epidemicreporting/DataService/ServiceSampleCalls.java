package com.reporting.epidemic.epidemicreporting.DataService;

import com.reporting.epidemic.epidemicreporting.Model.AllUserProfilesResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.AvailableUserResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.CheckedInUserInfoResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationRequestModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.PatientRequestModel;
import com.reporting.epidemic.epidemicreporting.Model.ReportStatusChangeDetailModel;
import com.reporting.epidemic.epidemicreporting.Model.UserProfileResponseModel;

import java.util.ArrayList;

/**
 * Created by eleven on 2018/10/3.
 */

public class ServiceSampleCalls {

    private static ServiceSampleCalls mServiceSampleCalls;

    public synchronized static ServiceSampleCalls getInstance(){
        if (mServiceSampleCalls == null) {
            mServiceSampleCalls = new ServiceSampleCalls();
        }
        return mServiceSampleCalls;
    }

    // 登录
    public void sampleLogin() {
        DataService.getInstance().loginUser("admin001", "123456", new OnResponseListener(){

            @Override
            public void onSuccess(int code, Object response) {
                System.out.print("login sucess");
            }

            @Override
            public void onFailure(int code, String msg) {
                System.out.print("login failed");
            }
        });
    }

    // 获取用户信息
    public void sampleGetUserProfile() {
        DataService.getInstance().getProfile("user001",new OnResponseListener(){

            @Override
            public void onSuccess(int code, Object response) {
                System.out.print("get profile sucess, and user name is: " + ((UserProfileResponseModel)response).getUsername());
            }

            @Override
            public void onFailure(int code, String msg) {
                System.out.print("login failed");
            }
        });
    }

    // 获取全部用户信息
    public void sampleGetAllProfile() {
        DataService.getInstance().getProfiles(new OnResponseListener(){

            @Override
            public void onSuccess(int code, Object response) {
                System.out.print("get profile sucess, and user name is: " + ((AllUserProfilesResponseModel)response).getTotal());
            }

            @Override
            public void onFailure(int code, String msg) {
                System.out.print("login failed");
            }
        });
    }

    // 更新DEVICE_TOKEN
    public void sampleUpdateDeviceToken() {
        DataService.getInstance().updateDeviceToken("78654960", "AND",new OnResponseListener(){

            @Override
            public void onSuccess(int code, Object response) {
                System.out.print("UPDATE DEVICE TOKEN SUCCESS");
            }

            @Override
            public void onFailure(int code, String msg) {
                System.out.print("UPDATE DEVICE TOKEN FAILED");
            }
        });
    }

    // 员工签到
    public void sampleCheckIn() {
        DataService.getInstance().checkIn("海曙区宝善路166号", "29.892328", "121.368493", false, true,new OnResponseListener(){

            @Override
            public void onSuccess(int code, Object response) {
                System.out.print("CHECK IN SUCCESS");
            }

            @Override
            public void onFailure(int code, String msg) {
                System.out.print("CHECK IN FAILED");
            }
        });
    }

    // 查询签到-当日所有员工签到信息 (admin user only)
    public void sampleGetAllCheckInStatusForToday() {
        DataService.getInstance().getAllCheckInStatusForToday(new OnResponseListener(){

            @Override
            public void onSuccess(int code, Object response) {
                System.out.print("Get ALL CHECK IN SUCCESS, and size is: " + ((ArrayList<CheckedInUserInfoResponseModel>)response).size());
            }

            @Override
            public void onFailure(int code, String msg) {
                System.out.print("CHECK IN FAILED");
            }
        });
    }

    // 查询签到-员工某月签到列表 (admin user only)
    public void sampleGetCheckInStatusForOneMonth() {
        DataService.getInstance().getCheckInStatusForOneMonth("2018", "10", "user001", new OnResponseListener(){

            @Override
            public void onSuccess(int code, Object response) {
                System.out.print("Get ALL CHECK IN SUCCESS, and size is: " + ((ArrayList<CheckedInUserInfoResponseModel>)response).size());
            }

            @Override
            public void onFailure(int code, String msg) {
                System.out.print("CHECK IN FAILED");
            }
        });
    }

    // 查询签到-员工某月签到列表 (admin user only)
    public void sampleGetAllAvailableStuffs() {
        DataService.getInstance().getAllAvailableStuffs(new OnResponseListener(){

            @Override
            public void onSuccess(int code, Object response) {
                System.out.print("Get ALL CHECK IN SUCCESS, and size is: " + ((ArrayList<AvailableUserResponseModel>)response).size());
            }

            @Override
            public void onFailure(int code, String msg) {
                System.out.print("CHECK IN FAILED");
            }
        });
    }

    // 汇报疫情
    public void sampleReportEpidemicSituation() {
        /*
        "location": "宁波市海曙区鄞江镇",
                "latitude": "29.832328",
                "longitude": "121.568493",
                "multiMedia": ["1bd86d28-ce37-4161-a09d-8d82060f91ee"],
        "description": "肺炎疫情",
                "happenTime": "1530581929574",
                "company": "定山桥村",
                "department": "3组",
                "patients": [{
            "name": "潘石屹",
                    "sex": "男",
                    "age": 30,
                    "career": "老板",
                    "symptom": "发热，头痛，名下无房",
                    "fabing": "严重发病",
                    "treatment": "抗生素"
        },{
            "name": "张继科",
                    "sex": "男",
                    "age": 30,
                    "career": "运动员",
                    "symptom": "发热，头痛，会打一点",
                    "fabing": "严重发病",
                    "treatment": "抗生素"
        }]
        */
        EpidemicSituationRequestModel report = new EpidemicSituationRequestModel();
        report.setLocation("宁波市海曙区鄞江镇");
        report.setLatitude("29.832328");
        report.setLongitude("121.568493");
        report.setHappenTime(System.currentTimeMillis());
        report.setCompany("定山桥村");
        report.setDepartment("5组");

        ArrayList<PatientRequestModel> patients = new ArrayList<PatientRequestModel>();

        PatientRequestModel patient = new PatientRequestModel();
        patient.setAge(11);
        patient.setCareer("运动员");
        patient.setName("张继科");
        patient.setSymptom("发热，头痛，会打一点");
        patient.setFabing("严重发病");
        patient.setTreatment("抗生素");
        patients.add(patient);

        report.setPatients(patients);


        DataService.getInstance().reportEpidemicSituation(report, new OnResponseListener(){

            @Override
            public void onSuccess(int code, Object response) {
                // the initial report status is "0"
                System.out.print("Get ALL CHECK IN SUCCESS, and size is: " + ((EpidemicSituationResponseModel)response).getId());
            }

            @Override
            public void onFailure(int code, String msg) {
                System.out.print("CHECK IN FAILED");
            }
        });
    }


    // 分配疫情
    public void sampleAssignsEpidemicSituation() {

        // the duty status will be changed from "0" to "1"
        DataService.getInstance().assignReport("28", "user001", new OnResponseListener(){

            @Override
            public void onSuccess(int code, Object response) {
                System.out.print("Get ALL CHECK IN SUCCESS, and size is: " + ((EpidemicSituationResponseModel)response).getId());
            }

            @Override
            public void onFailure(int code, String msg) {
                System.out.print("CHECK IN FAILED");
            }
        });
    }


    // 开始处理疫情
    public void sampleStartProcessEpidemicSituation() {

        ReportStatusChangeDetailModel process = new ReportStatusChangeDetailModel();
        process.setDutyId(16);
        process.setDutyDescription("得到上级批复的材料，重新开始处理疫情");
        process.setDutyStatus("2"); //  start set to be as 2

        DataService.getInstance().processReport(process, new OnResponseListener(){

            @Override
            public void onSuccess(int code, Object response) {
                System.out.print("Get ALL CHECK IN SUCCESS, and size is: " + ((EpidemicSituationResponseModel)response).getId());
            }

            @Override
            public void onFailure(int code, String msg) {
                System.out.print("CHECK IN FAILED");
            }
        });
    }





}