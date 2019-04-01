package com.healthapp.ui.Reports.Reports1;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.Retrofit.ApiInterface;
import com.healthapp.Retrofit.FormTypesModel;
import com.healthapp.Retrofit.NoDetailsModel;
import com.healthapp.Retrofit.QuestionModel;
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
                    if (formTypesModel.getResponse().size() > 0)
                        mView.showFormsType(formTypesModel.getResponse());

                }
            }

            @Override
            public void onFailure( Call<FormTypesModel> call, Throwable t ) {
                LoadingDialog.hideProgress();
                Toast.makeText(context, "من فضلك تحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
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
                    if (noDetailsModel.getResponse().size() > 0)
                        mView.showNoDetailsList(noDetailsModel.getResponse());
                }
            }

            @Override
            public void onFailure( Call<NoDetailsModel> call, Throwable t ) {

            }
        });
    }

    @Override
    public void getAllQuestions( int formId ) {
        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<QuestionModel> call = apiInterface.getAllQuestions(formId);
        call.enqueue(new Callback<QuestionModel>() {
            @Override
            public void onResponse( Call<QuestionModel> call, Response<QuestionModel> response ) {
                LoadingDialog.hideProgress();
                if (response.isSuccessful()) {
                    QuestionModel questionModel = response.body();
                    if (questionModel.getResponse().size() > 0)
                        mView.showAllQuestions(questionModel.getResponse());
                }
            }

            @Override
            public void onFailure( Call<QuestionModel> call, Throwable t ) {
                LoadingDialog.hideProgress();
                Toast.makeText(context, "من فضلك تحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

