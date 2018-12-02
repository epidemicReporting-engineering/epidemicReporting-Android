package com.reporting.epidemic.epidemicreporting.Networking;

/**
 * Created by eleven on 2018/10/1.
 */

public interface WebServiceURL {

    String SERVICE_ENDPOINT = "http://www.warmgoal.com/xsgov/";

    //login
    String UserRegistraion = "api/users/register/";
    String UserLogin = "api/auth/login";
    String ChangePassword = "api/password/change";
    String RestPassword = "api/password/reset";
    String GetProfile = "api/profile/get";
    String GetAllUserProfile = "api/profile/get-all";
    String CheckIn = "api/dailysignin/signin";
    String GetCheckedInAllStatus = "api/dailysignin/signin";
    String GetCheckInStatusForOneMonth = "api/dailysignin/signin/{user}";
    String UpdateDeviceToken = "api/device/update";
    String GetAllAvailableStuffs = "api/duty/query-available-stuff";

    //upload media
    String UploadMedia = "api/media/upload";

    //report
    String DutyReport = "api/duty/report";
    String DutyProcess = "api/duty/process";
    String DutyConfirm = "api/duty/confirm";
    String DutyLatestStatus = "api/duty/status/latest";
    String DutyAllReports = "api/duty/status/latestall";
    String DutyAllStatus = "api/duty/status";
    String DutyQuery = "api/duty/query";

    //admin
    String DutyAssign = "api/duty/assign";
    String GetStuff = "api/duty/getstuff";
    String GetStuffOwns = "api/duty/getstuffowns/get";
}
