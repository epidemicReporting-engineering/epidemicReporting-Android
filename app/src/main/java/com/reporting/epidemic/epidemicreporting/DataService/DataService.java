package com.reporting.epidemic.epidemicreporting.DataService;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reporting.epidemic.epidemicreporting.App.EpidemicApplication;
import com.reporting.epidemic.epidemicreporting.Constant.Constants;
import com.reporting.epidemic.epidemicreporting.ImageLoader.ProgressRequestBody;
import com.reporting.epidemic.epidemicreporting.Model.AllReportsResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.AllUserProfilesResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.AvailableUserResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.CheckedInUserInfoResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationAllStatusModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationRequestModel;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.ImageUploaderResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.LeaderConfirmModel;
import com.reporting.epidemic.epidemicreporting.Model.ReportStatusChangeDetailModel;
import com.reporting.epidemic.epidemicreporting.Model.UserProfileResponseModel;
import com.reporting.epidemic.epidemicreporting.Networking.NetworkingRetrofitManager;
import com.reporting.epidemic.epidemicreporting.Utils.JSONUtil;
import com.reporting.epidemic.epidemicreporting.Utils.SharedPreferencesUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.reporting.epidemic.epidemicreporting.Utils.JSONUtil.json2pojo;

/**
 * Created by eleven on 2018/10/1.
 */

public class DataService {

    private static DataService mDataService;

    private static ObjectMapper mObjectMapper = new ObjectMapper();

    public synchronized static DataService getInstance() {
        if (mDataService == null) {
            mDataService = new DataService();
        }
        return mDataService;
    }

    public void loginUser(String username, String password, final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().loginUser(username, password, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        String accessToken = jsonObject.get("token") != null ? jsonObject.get("token").toString(): "";
                        String refreshToken = jsonObject.get("refreshToken") != null ? jsonObject.get("refreshToken").toString(): "";
                        if (!accessToken.isEmpty()) {
                            SharedPreferencesUtil.getInstance(EpidemicApplication.getInstance().getAppContext()).put("accessToken",accessToken);
                            SharedPreferencesUtil.getInstance(EpidemicApplication.getInstance().getAppContext()).put("refreshToken",refreshToken);
                            listener.onSuccess(Constants.API_SUCCESS_CODE, "login success");
                        } else {
                            listener.onFailure(Constants.API_ERROR_CODE, "login failed");
                        }
                    } catch (Exception e) {
                        listener.onFailure(Constants.API_ERROR_CODE, e.getMessage());
                    }
                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "login failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    public void changePassword(@NotNull String username, @NotNull String oldPassword, @NotNull String newPassword, final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().changePassword(username, oldPassword, newPassword, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                listener.onSuccess(Constants.API_SUCCESS_CODE, "change password success");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    public void resetPassword(@NotNull String username, final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().resetPassword(username, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                listener.onSuccess(Constants.API_SUCCESS_CODE, "reset password success");
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    public void getProfile(@NotNull String username, final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().getProfile(username, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        UserProfileResponseModel profile = json2pojo(jsonObject.get("data").toString(), UserProfileResponseModel.class);
                        System.out.print("User profile name is: " + profile.getUsername());
                        listener.onSuccess(Constants.API_SUCCESS_CODE, profile);
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onFailure(Constants.API_ERROR_CODE, "get profile failed");
                    }
                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "get profile failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    public void getProfiles(final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().getProfiles(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        AllUserProfilesResponseModel profiles = json2pojo(jsonObject.getJSONObject("data").toString(), AllUserProfilesResponseModel.class);
                        listener.onSuccess(Constants.API_SUCCESS_CODE, profiles);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onFailure(Constants.API_ERROR_CODE, "get all profiles failed");
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onFailure(Constants.API_ERROR_CODE, "get all profiles failed");
                    }

                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "get all profiles failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    public void updateDeviceToken(@NotNull String deviceToken, @NotNull String deviceType, final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().updateDeviceToken(deviceToken, deviceType, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        int code = json2pojo(jsonObject.get("code").toString(), int.class);
                        if (code == 0) {
                            listener.onSuccess(Constants.API_SUCCESS_CODE, "update device token success");
                            return;
                        } else {
                            listener.onFailure(Constants.API_ERROR_CODE, "update device token failed");
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listener.onFailure(Constants.API_ERROR_CODE, "update device token failed");
                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "update device token failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    public void checkIn(String location, String latitude, String longitude, boolean isAbsence, boolean isAvailable, final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().checkIn(location, latitude,longitude, isAbsence, isAvailable, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200 && response.body() != null) {
                    listener.onSuccess(Constants.API_SUCCESS_CODE, "checkin success");
                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "checkin failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    // admin user only
    public void getAllCheckInStatusForToday(final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().getAllCheckInStatusForToday(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                if (response.code() == 200 && response.body() != null) {
                    try {
                        ArrayList<CheckedInUserInfoResponseModel> checkedUsers = null;
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        List<CheckedInUserInfoResponseModel> checkedUsersInfo = JSONUtil.json2list(jsonObject.get("data").toString(), CheckedInUserInfoResponseModel.class);
                        if (checkedUsersInfo != null && checkedUsersInfo.size() > 0) {
                            checkedUsers = new ArrayList<CheckedInUserInfoResponseModel>();
                            for (CheckedInUserInfoResponseModel checkInUser: checkedUsersInfo) {
                                if (checkInUser.getId() != null) {
                                    checkedUsers.add(checkInUser);
                                } else {
                                    continue;
                                }
                            }
                            listener.onSuccess(Constants.API_SUCCESS_CODE, checkedUsers);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listener.onFailure(Constants.API_ERROR_CODE, "get all checked user info failed");
                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "get all checked user info failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    // admin user only
    public void getCheckInStatusForOneMonth(@NotNull String year, @NotNull String month, @NotNull String username, final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().getCheckInStatusForOneMonth(year, month, username, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        ArrayList<CheckedInUserInfoResponseModel> checkedInDays = null;
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        List<CheckedInUserInfoResponseModel> checkedUsersInfo = JSONUtil.json2list(jsonObject.get("data").toString(), CheckedInUserInfoResponseModel.class);
                        if (checkedUsersInfo != null && checkedUsersInfo.size() > 0) {
                            checkedInDays = new ArrayList<CheckedInUserInfoResponseModel>();
                            for (CheckedInUserInfoResponseModel checkInDay: checkedUsersInfo) {
                                if (checkInDay.getId() != null) {
                                    checkedInDays.add(checkInDay);
                                } else {
                                    continue;
                                }
                            }
                            listener.onSuccess(Constants.API_SUCCESS_CODE, checkedInDays);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listener.onFailure(Constants.API_ERROR_CODE, "get checked in data for one month failed");
                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "get checked in data for one month failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    // admin user only
    public void getAllAvailableStuffs(final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().getAllAvailableStuffs(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        ArrayList<AvailableUserResponseModel> availableUsers = null;
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        List<AvailableUserResponseModel> availableUsersInfo = JSONUtil.json2list(jsonObject.get("data").toString(), AvailableUserResponseModel.class);
                        if (availableUsersInfo != null && availableUsersInfo.size() > 0) {
                            availableUsers = new ArrayList<AvailableUserResponseModel>();
                            for (AvailableUserResponseModel user: availableUsersInfo) {
                                availableUsers.add(user);
                            }
                            listener.onSuccess(Constants.API_SUCCESS_CODE, availableUsers);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listener.onFailure(Constants.API_ERROR_CODE, "get available users failed");
                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "get available users failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    public void reportEpidemicSituation(@NotNull EpidemicSituationRequestModel report, final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().reportEpidemicSituation(report, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        EpidemicSituationResponseModel reportResponse = json2pojo(jsonObject.get("data").toString(), EpidemicSituationResponseModel.class);
                        listener.onSuccess(Constants.API_SUCCESS_CODE, reportResponse);
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listener.onFailure(Constants.API_ERROR_CODE, "submit report failed");
                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "submit report failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    public void assignReport(@NotNull String dutyId, String dutyOwner, final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().assignReport(dutyId, dutyOwner, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        int code = json2pojo(jsonObject.get("code").toString(), int.class);
                        // the code will be "204" if we have duplicate assign the task
                        if (code == 0) {
                            EpidemicSituationResponseModel assignReportResponse = json2pojo(jsonObject.get("data").toString(), EpidemicSituationResponseModel.class);
                            listener.onSuccess(Constants.API_SUCCESS_CODE, assignReportResponse);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listener.onFailure(Constants.API_ERROR_CODE, "assign report failed");
                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "assign report failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    public void processReport(@NotNull ReportStatusChangeDetailModel report,  final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().processReport(report, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        int code = json2pojo(jsonObject.get("code").toString(), int.class);
                        if (code == 0) {
                            EpidemicSituationResponseModel assignReportResponse = json2pojo(jsonObject.get("data").toString(), EpidemicSituationResponseModel.class);
                            listener.onSuccess(Constants.API_SUCCESS_CODE, assignReportResponse);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listener.onFailure(Constants.API_ERROR_CODE, "process report failed");
                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "process report failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    public void leaderConfirm(@NotNull LeaderConfirmModel confirm, final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().leaderConfirm(confirm, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        int code = json2pojo(jsonObject.get("code").toString(), int.class);
                        if (code == 0) {
                            EpidemicSituationResponseModel assignReportResponse = json2pojo(jsonObject.get("data").toString(), EpidemicSituationResponseModel.class);
                            listener.onSuccess(Constants.API_SUCCESS_CODE, assignReportResponse);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listener.onFailure(Constants.API_ERROR_CODE, "confirm report failed");
                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "confirm report failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    public void getReportList(String state, String page, String size, String username, String reporter, String happenTimeFrom, String happenTimeTo, final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().getReportList(state, page, size, username, reporter, happenTimeFrom, happenTimeTo, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        mObjectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
                        mObjectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
                        mObjectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                        //JSONObject jsonObject = new JSONObject(response.body().toString());
                        JsonNode rootNode = mObjectMapper.readTree(mObjectMapper.writeValueAsString(response.body()));
                        JsonNode dateNode = rootNode.get("data");
                        if (dateNode != null) {
                            AllReportsResponseModel assignReportResponse = mObjectMapper.readValue(dateNode.toString(), AllReportsResponseModel.class);
//                            AllReportsResponseModel assignReportResponse = json2pojo(jsonObject.get("data").toString(), AllReportsResponseModel.class);
                            listener.onSuccess(Constants.API_SUCCESS_CODE, assignReportResponse);
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listener.onFailure(Constants.API_ERROR_CODE, "get all reports failed");
                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "get all reports failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    public void getAllStatusForOneReport(@NotNull String id, final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().getAllStatusForOneReport(id, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        int code = json2pojo(jsonObject.get("code").toString(), int.class);
                        if (code == 0) {
                            EpidemicSituationAllStatusModel allStatus = json2pojo(jsonObject.get("data").toString(), EpidemicSituationAllStatusModel.class);
                            listener.onSuccess(Constants.API_SUCCESS_CODE, allStatus);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listener.onFailure(Constants.API_ERROR_CODE, "confirm report failed");
                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "confirm report failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }

    public void uploadImage(final ProgressRequestBody file, final OnResponseListener listener) {
        NetworkingRetrofitManager.getInstance().uploadImage(file, new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        mObjectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
                        mObjectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
                        mObjectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
                        //JSONObject jsonObject = new JSONObject(response.body().toString());
                        JsonNode rootNode = mObjectMapper.readTree(mObjectMapper.writeValueAsString(response.body()));
                        JsonNode dateNode = rootNode.get("data");
                        if (dateNode != null) {
                            ImageUploaderResponseModel assignReportResponse = mObjectMapper.readValue(dateNode.toString(), ImageUploaderResponseModel.class);
//                            AllReportsResponseModel assignReportResponse = json2pojo(jsonObject.get("data").toString(), AllReportsResponseModel.class);
                            listener.onSuccess(Constants.API_SUCCESS_CODE, assignReportResponse);
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    listener.onFailure(Constants.API_ERROR_CODE, "upload image failed");
                } else {
                    listener.onFailure(Constants.API_ERROR_CODE, "upload image failed");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                listener.onFailure(Constants.API_ERROR_CODE, t.getMessage());
            }
        });
    }


}
