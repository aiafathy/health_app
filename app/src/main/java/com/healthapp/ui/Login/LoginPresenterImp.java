package com.healthapp.ui.Login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.healthapp.Retrofit.ApiInterface;
import com.healthapp.ui.LoadingDialog;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPresenterImp implements ILoginContract.Presenter {

    ILoginContract.View mView;
    Context context;

    public LoginPresenterImp( ILoginContract.View mView, Context context ) {
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void login( String email, String pass, String device_token ) {
        LoadingDialog.showProgress(context);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://81.21.105.203:8080/health/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.loginUser(email, pass, device_token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse( Call<JsonObject> call, Response<JsonObject> response ) {
                LoadingDialog.hideProgress();
                if (response.isSuccessful()) {
                    String token = response.body().get("token").toString().replace("\"", "");
                    mView.goToHealthUnitDetails(token, Integer.parseInt(response.body().getAsJsonObject("user").get("id").toString()));
                } else {
                    Toast.makeText(context, "login not success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure( Call<JsonObject> call, Throwable t ) {
                LoadingDialog.hideProgress();
                Toast.makeText(context, "من فضلك تحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
            }

        });
    }


}
