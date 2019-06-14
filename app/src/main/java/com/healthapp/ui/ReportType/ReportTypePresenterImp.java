package com.healthapp.ui.ReportType;

import android.content.Context;
import android.widget.Toast;

import com.healthapp.Retrofit.ApiInterface;
import com.healthapp.Retrofit.FormTypesModel;
import com.healthapp.Retrofit.RetrofitInstance;
import com.healthapp.ui.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportTypePresenterImp implements IReportTypeContract.Presenter {

    IReportTypeContract.View mView;
    Context context;

    public ReportTypePresenterImp( IReportTypeContract.View mView, Context context ) {
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void getReportsType() {
        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<FormTypesModel> call = apiInterface.getAllFormTypes();
        call.enqueue(new Callback<FormTypesModel>() {
            @Override
            public void onResponse( Call<FormTypesModel> call, Response<FormTypesModel> response ) {
                LoadingDialog.hideProgress();
                if (response.isSuccessful()) {
                    FormTypesModel formTypesModel = response.body();
                    if (formTypesModel.getResponse().size() > 0)
                        mView.showReportsType(formTypesModel.getResponse());

                }
            }

            @Override
            public void onFailure( Call<FormTypesModel> call, Throwable t ) {
                LoadingDialog.hideProgress();
                Toast.makeText(context, "من فضلك تحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
