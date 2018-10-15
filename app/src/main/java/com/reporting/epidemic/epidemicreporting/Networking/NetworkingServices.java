package com.reporting.epidemic.epidemicreporting.Networking;


import com.reporting.epidemic.epidemicreporting.ImageLoader.ProgressRequestBody;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkingServices {

   /*
   * user related apis: login / change password / get user profile / get all user info
   *
   * */
   // 登录
   @POST(WebServiceURL.UserLogin)
   Call<Object> loginUser(@Body RequestBody requestBody);

   // 重置密码
   @POST(WebServiceURL.RestPassword)
   Call<Object> resetPassword(@Body RequestBody requestBody);

   // 修改密码
   @POST(WebServiceURL.ChangePassword)
   Call<Object> changePassword(@Body RequestBody requestBody);

   // 获取用户信息
   @GET(WebServiceURL.GetProfile)
   Call<Object> getProfile(@Query("username") String username);

   // 获取全部用户信息
   @GET(WebServiceURL.GetAllUserProfile)
   Call<Object> getProfiles();

   // 更新DEVICE_TOKEN
   @POST(WebServiceURL.UpdateDeviceToken)
   Call<Object> updateDeviceToken(@Body RequestBody requestBody);


   /*
   *
   * checkin related apis: daily checkin / get all the checkin info / get the status of one employee in one month
   *
   * */

   // 员工签到
   @POST(WebServiceURL.CheckIn)
   Call<Object> checkIn(@Body RequestBody requestBody);

   // 查询签到-当日所有员工签到信息
   @GET(WebServiceURL.GetCheckedInAllStatus)
   Call<Object> getAllCheckInStatusForToday();

   // 查询签到-员工某月签到列表
   @GET(WebServiceURL.GetCheckInStatusForOneMonth)
   Call<Object> getCheckInStatusForOneMonth(@Path("user") String user, @Query("year") String year, @Query("month") String month);

   // 查询签到-当日所有可接受任务员工列表
   @GET(WebServiceURL.GetAllAvailableStuffs)
   Call<Object> getAllAvailableStuffs();

   /*
   * Epidmic Reporting related API
   * */

   // 汇报疫情
   @POST(WebServiceURL.DutyReport)
   Call<Object> reportEpidemicSituation(@Body RequestBody requestBody);

   // 分配疫情
   @POST(WebServiceURL.DutyAssign)
   Call<Object> assignReport(@Body RequestBody requestBody);

   // 处理疫情-开始: dutyStatus = 2 / 处理疫情-暂停: dutyStatus = 3 / 处理疫情-无法处理: dutyStatus = 6 / 处理疫情-完成: dutyStatus = 4
   @POST(WebServiceURL.DutyProcess)
   Call<Object> processReport(@Body RequestBody requestBody);

   // 处理疫情-领导验收
   @POST(WebServiceURL.DutyConfirm)
   Call<Object> leaderConfirm(@Body RequestBody requestBody);

   // 查询疫情-列表
   @GET(WebServiceURL.DutyQuery)
   Call<Object> getReportList(@Query("state") String state, @Query("page") String page, @Query("size") String size, @Query("username") String username,@Query("happenTimeFrom") String happenTimeFrom,@Query("happenTimeTo") String happenTimeTo);

   // 查询疫情-单条疫情全部状态
   @GET(WebServiceURL.DutyAllStatus)
   Call<Object> getAllStatusForOneReport(@Query("id") String id);


      /*
   * Epidmic Reporting upload image API
   * */

   @Multipart
   @POST(WebServiceURL.UploadMedia)
   Call<Object> uploadImage(@Part("file\"; filename=\"reportImgName\" ") ProgressRequestBody file);
}

