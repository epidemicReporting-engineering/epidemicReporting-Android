package com.reporting.epidemic.epidemicreporting.Networking;

import android.content.Context;

import com.reporting.epidemic.epidemicreporting.App.EpidemicApplication;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationRequestModel;
import com.reporting.epidemic.epidemicreporting.Model.LeaderConfirmModel;
import com.reporting.epidemic.epidemicreporting.Model.ReportStatusChangeDetailModel;
import com.reporting.epidemic.epidemicreporting.Utils.JSONUtil;
import com.reporting.epidemic.epidemicreporting.Utils.SharedPreferencesUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NetworkingRetrofitManager implements Interceptor {

   private Context mContext;
   private NetworkingServices mNetworkingServices;

   public static NetworkingRetrofitManager mNetworkingRetrofitManager;

   public synchronized static NetworkingRetrofitManager getInstance(){
      if (mNetworkingRetrofitManager == null) {
         mNetworkingRetrofitManager = new NetworkingRetrofitManager();
      }
      return mNetworkingRetrofitManager;
   }

   private NetworkingRetrofitManager() {

      OkHttpClient.Builder builder = new OkHttpClient.Builder();
      builder.readTimeout(10, TimeUnit.SECONDS);
      builder.connectTimeout(10, TimeUnit.SECONDS);
      builder.addNetworkInterceptor(this);

      HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
      logging.setLevel(HttpLoggingInterceptor.Level.BODY);
      builder.addInterceptor(logging);

      OkHttpClient client = builder.build();

      Retrofit retrofit = new Retrofit.Builder()
              .client(client)
              .addConverterFactory(GsonConverterFactory.create())
              .baseUrl(WebServiceURL.SERVICE_ENDPOINT)
              .build();

      mNetworkingServices = retrofit.create(NetworkingServices.class);

   }


   @Override
   public Response intercept(Chain chain) throws IOException {
      Request original = chain.request();

      if (original.url().toString().contains(WebServiceURL.UserLogin)) {
         return chain.proceed(original.newBuilder()
                 .addHeader("Content-Type","application/json")
                 .addHeader("X-Requested-With","XMLHttpRequest")
                 .build());
      } else {
         String accessToken = (String)SharedPreferencesUtil.getInstance(EpidemicApplication.getInstance().getAppContext()).getSharedPreference("accessToken", Constants.DEFAULT_TOKEN);
         return chain.proceed(original.newBuilder()
                 .addHeader("X-Authorization","Bearer " + accessToken)
                 .build());
      }
   }

   public final void loginUser(@NotNull String username, @NotNull String password, @NotNull Callback callback) {
      JSONObject requestData = new JSONObject();
      try {
         requestData.put("username", username);
         requestData.put("password", password);
      } catch (JSONException e) {
         e.printStackTrace();
      }
      RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),requestData.toString());
      Call call = this.mNetworkingServices.loginUser(requestBody);
      call.enqueue(callback);
   }

   public final void changePassword(@NotNull String username, @NotNull String oldPassword, @NotNull String newPassword, @NotNull Callback callback) {
      JSONObject requestData = new JSONObject();
      try {
         requestData.put("username", username);
         requestData.put("oldPassword", oldPassword);
         requestData.put("newPassword", newPassword);
      } catch (JSONException e) {
         e.printStackTrace();
      }
      RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),requestData.toString());
      Call call = this.mNetworkingServices.changePassword(requestBody);
      call.enqueue(callback);
   }

   public final void resetPassword(@NotNull String username, @NotNull Callback callback) {
      JSONObject requestData = new JSONObject();
      try {
         requestData.put("username", username);
      } catch (JSONException e) {
         e.printStackTrace();
      }
      RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),requestData.toString());
      Call call = this.mNetworkingServices.resetPassword(requestBody);
      call.enqueue(callback);
   }

   public final void getProfile(@NotNull String username, @NotNull Callback callback) {
      Call call = this.mNetworkingServices.getProfile(username);
      call.enqueue(callback);
   }

   public final void getProfiles(@NotNull Callback callback) {
      Call call = this.mNetworkingServices.getProfiles();
      call.enqueue(callback);
   }

   public final void updateDeviceToken(@NotNull String deviceToken, @NotNull String deviceType, @NotNull Callback callback) {
      JSONObject requestData = new JSONObject();
      try {
         requestData.put("deviceToken", deviceToken);
         requestData.put("deviceType", deviceType);
      } catch (JSONException e) {
         e.printStackTrace();
      }
      RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),requestData.toString());
      Call call = this.mNetworkingServices.updateDeviceToken(requestBody);
      call.enqueue(callback);
   }

   public final void checkIn(@NotNull String location, String latitude, String longitude, boolean isAbsence, boolean isAvailable, @NotNull Callback callback) {
      JSONObject requestData = new JSONObject();
      try {
         requestData.put("location", location);
         requestData.put("latitude", latitude);
         requestData.put("longitude", longitude);
         requestData.put("isAbsence", isAbsence);
         requestData.put("isAvailable", isAvailable);
      } catch (JSONException e) {
         e.printStackTrace();
      }
      RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),requestData.toString());
      Call call = this.mNetworkingServices.checkIn(requestBody);
      call.enqueue(callback);
   }

   public final void getAllCheckInStatusForToday(@NotNull Callback callback) {
      Call call = this.mNetworkingServices.getAllCheckInStatusForToday();
      call.enqueue(callback);
   }

   public final void getCheckInStatusForOneMonth( @NotNull String year, @NotNull String month,@NotNull String username, @NotNull Callback callback) {
      Call call = this.mNetworkingServices.getCheckInStatusForOneMonth(username, year, month);
      call.enqueue(callback);
   }

   public final void getAllAvailableStuffs(@NotNull Callback callback) {
      Call call = this.mNetworkingServices.getAllAvailableStuffs();
      call.enqueue(callback);
   }

   public final void reportEpidemicSituation(@NotNull EpidemicSituationRequestModel report, @NotNull Callback callback) {
      try {
         RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),JSONUtil.obj2json(report));
         Call call = this.mNetworkingServices.reportEpidemicSituation(requestBody);
         call.enqueue(callback);
      }catch (Exception e) {
         e.printStackTrace();
      }
   }

   public final void assignReport(@NotNull String dutyId, @NotNull String dutyOwner, @NotNull Callback callback) {
      JSONObject requestData = new JSONObject();
      try {
         requestData.put("dutyId", dutyId);
         requestData.put("dutyOwner", dutyOwner);
      } catch (JSONException e) {
         e.printStackTrace();
      }
      RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),requestData.toString());
      Call call = this.mNetworkingServices.assignReport(requestBody);
      call.enqueue(callback);
   }

   public final void processReport(@NotNull ReportStatusChangeDetailModel report, @NotNull Callback callback) {
      try {
         RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),JSONUtil.obj2json(report));
         Call call = this.mNetworkingServices.processReport(requestBody);
         call.enqueue(callback);
      }catch (Exception e) {
         e.printStackTrace();
      }
   }

   public final void leaderConfirm(@NotNull LeaderConfirmModel confirm, @NotNull Callback callback) {
      try {
         RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),JSONUtil.obj2json(confirm));
         Call call = this.mNetworkingServices.leaderConfirm(requestBody);
         call.enqueue(callback);
      }catch (Exception e) {
         e.printStackTrace();
      }
   }

   public final void getReportList( String state, String page, String size, String username, String happenTimeFrom, String happenTimeTo, @NotNull Callback callback) {
      Call call = this.mNetworkingServices.getReportList(state,page,size,username,happenTimeFrom,happenTimeTo);
      call.enqueue(callback);
   }

   public final void getAllStatusForOneReport(@NotNull String id, @NotNull Callback callback) {
      Call call = this.mNetworkingServices.getAllStatusForOneReport(id);
      call.enqueue(callback);
   }
}
