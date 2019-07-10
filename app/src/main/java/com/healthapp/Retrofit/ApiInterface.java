package com.healthapp.Retrofit;

import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("user/login")
    Call<JsonObject> loginUser(@Query("email") String email, @Query("password") String password, @Query("device_token") String device_token);


    @POST("user/signup")
    Call<JsonObject> signUpUser(@Query("name") String name, @Query("email") String email
            , @Query("phone") String phone, @Query("password") String pss);

    @GET("governorates/details")
    Call<HealthUnitModel> getGovernoratesList();

    @GET("administrations/details/{id}")
    Call<HealthUnitModel> getAdministrations(@Path("id") int id);

    @GET("units/details/{id}")
    Call<HealthUnitModel> getUnits(@Path("id") int id);


    @GET("user/details")
    Call<UserModel> getUserData();

    @GET("user/hostry/units/details/{id}")
    Call<LastVisitsModel> getLastVisits(@Path("id") int userId);

    @GET("user/hostry/forms/details/{id}")
    Call<LastVisitsFormTypeModel> getLastVisitsFormType(@Path("id") int unit_id);

    @GET("user/hostry/questions/details/{id}")
    Call<LastVisitsDetailsModel> getLastVisitsDetails(@Path("id") int form_id);


    @GET("forms/details")
    Call<FormTypesModel> getAllFormTypes();

    @GET("answer/no/details")
    Call<NoDetailsModel> getNoDetailsList();

    @GET("questions/details/{id}")
    Call<QuestionModel> getAllQuestions(@Path("id") int id_forms);

    @Headers("Content-Type: application/json")
    @POST("add/answer/details")
    Call<JsonObject> sendReport(@Body HashMap<String, Object> params);

    @GET("user/messages/{id}")
    Call<NotificationsModel> getNotifications(@Path("id") int userId);
}