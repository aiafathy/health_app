package com.healthapp.ui.Reports.Reports1;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.Retrofit.ApiInterface;
import com.healthapp.Retrofit.FormTypesModel;
import com.healthapp.Retrofit.NoDetailsModel;
import com.healthapp.Retrofit.RetrofitInstance;
import com.healthapp.Retrofit.UserModel;
import com.healthapp.ui.LoadingDialog;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportPresenterImp implements IReportContract.Presenter {
    IReportContract.View mView;
    Context context;
    private String errorType;
    private String errorDesc;

    public ReportPresenterImp( IReportContract.View mView, Context context ) {
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void getFormsType() {
        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<FormTypesModel> call = apiInterface.getAllFormTypes();
        call.enqueue(new Callback<FormTypesModel>() {
            @Override
            public void onResponse( Call<FormTypesModel> call, Response<FormTypesModel> response ) {
                LoadingDialog.hideProgress();
                if (response.isSuccessful()) {
                    FormTypesModel formTypesModel = response.body();
                    Log.i("forms_type_api", "success");
                    if (formTypesModel.getResponse().size() > 0)
                        mView.showFormsType(formTypesModel.getResponse());

                } else {
                    Log.i("forms_type_api", "not success");
                }

            }

            @Override
            public void onFailure( Call<FormTypesModel> call, Throwable t ) {
                Log.i("forms_type_api", "failure" + t.getMessage());
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
                Log.i("forms_type_api", "failure with error : " + errorType + " ," + errorDesc);
            }
        });

    }

    @Override
    public void getNoDetailsList() {
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<NoDetailsModel> call = apiInterface.getNoDetailsList();
        call.enqueue(new Callback<NoDetailsModel>() {
            @Override
            public void onResponse( Call<NoDetailsModel> call, Response<NoDetailsModel> response ) {
                if (response.isSuccessful()) {
                    NoDetailsModel noDetailsModel = response.body();
                    Log.i("no_details_api", "success");
                    if (noDetailsModel.getResponse().size() > 0)
                        mView.showNoDetailsList(noDetailsModel.getResponse());

                } else {
                    Log.i("no_details_api", "not success");
                }

            }

            @Override
            public void onFailure( Call<NoDetailsModel> call, Throwable t ) {
                Log.i("no_details_api", "failure" + t.getMessage());
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
                Log.i("no_details_api", "failure with error : " + errorType + " ," + errorDesc);
            }
        });

    }
}
