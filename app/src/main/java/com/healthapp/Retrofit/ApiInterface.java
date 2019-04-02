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

    @POST("api/v1/user/login")
    Call<JsonObject> loginUser( @Query("email") String email, @Query("password") String password, @Query("device_token") String device_token );


    @GET("api/v1/governorates/details")
    Call<HealthUnitModel> getGovernoratesList();

    @GET("api/v1/administrations/details/{id}")
    Call<HealthUnitModel> getAdministrations( @Path("id") int id );

    @GET("api/v1/units/details/{id}")
    Call<HealthUnitModel> getUnits( @Path("id") int id );


    @GET("api/v1/user/details")
    Call<UserModel> getUserData();

    @GET("api/v1/user/hostry/units/details/{id}")
    Call<LastVisitsModel> getLastVisits( @Path("id") int userId );

    @GET("api/v1/user/hostry/forms/details/{id}")
    Call<LastVisitsFormTypeModel> getLastVisitsFormType( @Path("id") int unit_id );

    @GET("api/v1/user/hostry/questions/details/{id}")
    Call<LastVisitsDetailsModel> getLastVisitsDetails( @Path("id") int form_id );


    @GET("api/v1/forms/details")
    Call<FormTypesModel> getAllFormTypes();

    @GET("api/v1/answer/no/details")
    Call<NoDetailsModel> getNoDetailsList();

    @GET("api/v1/questions/details/{id}")
    Call<QuestionModel> getAllQuestions( @Path("id") int id_forms );

    @Headers("Content-Type: application/json")
    @POST("api/v1/add/answer/details")
    Call<JsonObject> sendReport( @Body HashMap<String,Object> params );

}