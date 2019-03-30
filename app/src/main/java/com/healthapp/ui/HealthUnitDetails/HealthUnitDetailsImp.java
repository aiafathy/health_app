package com.healthapp.ui.HealthUnitDetails;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.Retrofit.ApiInterface;
import com.healthapp.Retrofit.HealthUnitModel;
import com.healthapp.Retrofit.RetrofitInstance;
import com.healthapp.ui.LoadingDialog;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthUnitDetailsImp implements IHealthUnitDetailsContract.Presenter {

    IHealthUnitDetailsContract.View mView;
    Context context;

    public HealthUnitDetailsImp( IHealthUnitDetailsContract.View mView, Context context ) {
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void getTownList() {
        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<HealthUnitModel> call = apiInterface.getGovernoratesList();
        call.enqueue(new Callback<HealthUnitModel>() {
            @Override
            public void onResponse( Call<HealthUnitModel> call, Response<HealthUnitModel> response ) {
                LoadingDialog.hideProgress();
                if (response.isSuccessful()) {
                    HealthUnitModel healthUnitModel = response.body();
                    if (healthUnitModel.getCode() == 200) {
                        if (healthUnitModel.getResponse().size() > 0)
                            mView.showTownList(healthUnitModel.getResponse());
                    }
                }
            }

            @Override
            public void onFailure( Call<HealthUnitModel> call, Throwable t ) {
                LoadingDialog.hideProgress();
                Toast.makeText(context, "من فضلك تحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void getManagementList( int id ) {
        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<HealthUnitModel> call = apiInterface.getAdministrations(id);
        call.enqueue(new Callback<HealthUnitModel>() {
            @Override
            public void onResponse( Call<HealthUnitModel> call, Response<HealthUnitModel> response ) {
                if (response.isSuccessful()) {
                    HealthUnitModel healthUnitModel = response.body();
                    LoadingDialog.hideProgress();
                    if (healthUnitModel.getCode() == 200) {
                        if (healthUnitModel.getResponse().size() > 0)
                            mView.showManagementList(healthUnitModel.getResponse());
                    }
                }
            }

            @Override
            public void onFailure( Call<HealthUnitModel> call, Throwable t ) {
                LoadingDialog.hideProgress();
                Toast.makeText(context, "من فضلك تحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getUnitsList( int id ) {
        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<HealthUnitModel> call = apiInterface.getUnits(id);
        call.enqueue(new Callback<HealthUnitModel>() {
            @Override
            public void onResponse( Call<HealthUnitModel> call, Response<HealthUnitModel> response ) {
                if (response.isSuccessful()) {
                    HealthUnitModel healthUnitModel = response.body();
                    LoadingDialog.hideProgress();
                    if (healthUnitModel.getCode() == 200) {
                        if (healthUnitModel.getResponse().size() > 0)
                            mView.showUnitsList(healthUnitModel.getResponse());
                    }
                }
            }

            @Override
            public void onFailure( Call<HealthUnitModel> call, Throwable t ) {
                LoadingDialog.hideProgress();
                Toast.makeText(context, "من فضلك تحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
