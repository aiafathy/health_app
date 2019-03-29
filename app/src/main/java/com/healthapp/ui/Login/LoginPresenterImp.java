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
    private String errorType;
    private String errorDesc;

    public LoginPresenterImp( ILoginContract.View mView, Context context ) {
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void login( String email, String pass ) {
        LoadingDialog.showProgress(context);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://4454c430.ngrok.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.loginUser(email, pass);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse( Call<JsonObject> call, Response<JsonObject> response ) {
                LoadingDialog.hideProgress();
                if (response.isSuccessful()) {
                    Log.i("login_api", "success");
                    String token = response.body().get("token").toString().replace("\"", "");
                    mView.goToHealthUnitDetails(token, Integer.parseInt(response.body().getAsJsonObject("user").get("id").toString()));
                } else {
                    Log.i("login_api", "not success");
                    LoadingDialog.hideProgress();
                }
            }

            @Override
            public void onFailure( Call<JsonObject> call, Throwable t ) {
                Log.i("login_api", "failure" + t.getMessage());
                LoadingDialog.hideProgress();
                if (t instanceof IOException) {
                    errorType = "Timeout";
                    errorDesc = String.valueOf(t.getMessage());
                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    errorDesc = String.valueOf(t.getMessage());
                } else {
                    errorType = "Other Error";
                    errorDesc = String.valueOf(t.getLocalizedMessage());
                }
                Log.i("api_login", "failure with error : " + errorType + " ," + errorDesc);
            }

        });
    }


}
