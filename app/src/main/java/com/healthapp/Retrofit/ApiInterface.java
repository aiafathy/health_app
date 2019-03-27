package com.healthapp.Retrofit;

import com.google.gson.JsonObject;
import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.ui.HealthUnitDetails.HealthUnitDetails;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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

}