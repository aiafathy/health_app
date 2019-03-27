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
    private String errorType;
    private String errorDesc;

    public HealthUnitDetailsImp( IHealthUnitDetailsContract.View mView, Context context ) {
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void getTownList() {
        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Log.i("token_town", PreferencesHelperImp.getInstance().getUserToken());
        Call<HealthUnitModel> call = apiInterface.getGovernoratesList();
        call.enqueue(new Callback<HealthUnitModel>() {
            @Override
            public void onResponse( Call<HealthUnitModel> call, Response<HealthUnitModel> response ) {
                if (response.isSuccessful()) {
                    HealthUnitModel healthUnitModel = response.body();
                    Log.i("town_api", "success");
                    Log.i("town_api_code", String.valueOf(healthUnitModel.getCode()));
                    LoadingDialog.hideProgress();
                    if (healthUnitModel.getCode() == 200) {
                        Log.i("town_api", String.valueOf(healthUnitModel.getCode()) + ", " + healthUnitModel.getResponse().size());
                        if (healthUnitModel.getResponse().size() > 0)
                            mView.showTownList(healthUnitModel.getResponse());
                    }

                } else {
                    Log.i("town_api", "not success");
                    LoadingDialog.hideProgress();
                }

            }

            @Override
            public void onFailure( Call<HealthUnitModel> call, Throwable t ) {
                Log.i("town_api", "failure" + t.getMessage());
                LoadingDialog.hideProgress();
                if (t instanceof IOException) {
                    errorType = "Timeout";
                    errorDesc = String.valueOf(t.getMessage());
                    Toast.makeText(context, "من فضلك تحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    errorDesc = String.valueOf(t.getMessage());
                } else {
                    errorType = "Other Error";
                    errorDesc = String.valueOf(t.getLocalizedMessage());
                }
                Log.i("town_api", "failure with error : " + errorType + " ," + errorDesc);
            }

        });
    }

    @Override
    public void getManagementList( int id ) {
        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Log.i("token_management", PreferencesHelperImp.getInstance().getUserToken());
        Call<HealthUnitModel> call = apiInterface.getAdministrations(id);
        call.enqueue(new Callback<HealthUnitModel>() {
            @Override
            public void onResponse( Call<HealthUnitModel> call, Response<HealthUnitModel> response ) {
                if (response.isSuccessful()) {
                    HealthUnitModel healthUnitModel = response.body();
                    Log.i("management_api", "success");
                    Log.i("management_api_code", String.valueOf(healthUnitModel.getCode()));
                    LoadingDialog.hideProgress();
                    if (healthUnitModel.getCode() == 200) {
                        Log.i("management_api", String.valueOf(healthUnitModel.getCode()) + ", " + healthUnitModel.getResponse().size());
                        if (healthUnitModel.getResponse().size() > 0)
                            mView.showManagementList(healthUnitModel.getResponse());
                    }

                } else {
                    Log.i("management_api", "not success");
                    LoadingDialog.hideProgress();
                }

            }

            @Override
            public void onFailure( Call<HealthUnitModel> call, Throwable t ) {
                Log.i("management_api", "failure" + t.getMessage());
                LoadingDialog.hideProgress();
                if (t instanceof IOException) {
                    errorType = "Timeout";
                    errorDesc = String.valueOf(t.getMessage());
                    Toast.makeText(context, "من فضلك تحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    errorDesc = String.valueOf(t.getMessage());
                } else {
                    errorType = "Other Error";
                    errorDesc = String.valueOf(t.getLocalizedMessage());
                }
                Log.i("management_api", "failure with error : " + errorType + " ," + errorDesc);
            }

        });
    }

    @Override
    public void getUnitsList( int id ) {
        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Log.i("token_unit", PreferencesHelperImp.getInstance().getUserToken());
        Call<HealthUnitModel> call = apiInterface.getUnits(id);
        call.enqueue(new Callback<HealthUnitModel>() {
            @Override
            public void onResponse( Call<HealthUnitModel> call, Response<HealthUnitModel> response ) {
                if (response.isSuccessful()) {
                    HealthUnitModel healthUnitModel = response.body();
                    Log.i("units_api", "success");
                    Log.i("units_api_code", String.valueOf(healthUnitModel.getCode()));
                    LoadingDialog.hideProgress();
                    if (healthUnitModel.getCode() == 200) {
                        Log.i("units_api", String.valueOf(healthUnitModel.getCode()) + ", " + healthUnitModel.getResponse().size());
                        if (healthUnitModel.getResponse().size() > 0)
                            mView.showUnitsList(healthUnitModel.getResponse());
                    }

                } else {
                    Log.i("units_api", "not success");
                    LoadingDialog.hideProgress();
                }

            }

            @Override
            public void onFailure( Call<HealthUnitModel> call, Throwable t ) {
                Log.i("units_api", "failure" + t.getMessage());
                LoadingDialog.hideProgress();
                if (t instanceof IOException) {
                    errorType = "Timeout";
                    errorDesc = String.valueOf(t.getMessage());
                    Toast.makeText(context, "من فضلك تحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
                } else if (t instanceof IllegalStateException) {
                    errorType = "ConversionError";
                    errorDesc = String.valueOf(t.getMessage());
                } else {
                    errorType = "Other Error";
                    errorDesc = String.valueOf(t.getLocalizedMessage());
                }
                Log.i("units_api", "failure with error : " + errorType + " ," + errorDesc);
            }

        });
    }
}
