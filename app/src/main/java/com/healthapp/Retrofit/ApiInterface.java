package com.healthapp.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("api/v1/user/login")
    Call<JsonObject> loginUser( @Query("email") String email, @Query("password") String password );


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
}