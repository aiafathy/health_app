package com.healthapp.ui.SignUp;

import android.content.Context;

import com.google.gson.JsonObject;
import com.healthapp.Retrofit.ApiInterface;
import com.healthapp.ui.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpPresenterImpl implements SignUpContract.SignUpPresenter {

    SignUpContract.SignUpView upView;

    Context context;

    public SignUpPresenterImpl(Context context, SignUpContract.SignUpView upView) {

        this.context = context;
        this.upView = upView;
    }

    @Override
    public void signUp(String name, String email, String phone, String password) {

        LoadingDialog.showProgress(context);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://81.21.105.203:8080/health/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.signUpUser(name, email, phone, password);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                //TODO Complete the signUp method when the api ready by abdelrahman

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


    }

    @Override
    public void backToLog() {
        upView.backToLogin();
    }
}
