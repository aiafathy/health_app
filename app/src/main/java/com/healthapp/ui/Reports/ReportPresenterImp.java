package com.healthapp.ui.Reports;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.healthapp.Retrofit.DataReports;
import com.healthapp.Retrofit.Questions;
import com.healthapp.Retrofit.ApiInterface;
import com.healthapp.Retrofit.FeedBack;
import com.healthapp.Retrofit.FormTypesModel;
import com.healthapp.Retrofit.LocationModel;
import com.healthapp.Retrofit.NoDetailsModel;
import com.healthapp.Retrofit.QuestionModel;
import com.healthapp.Retrofit.RetrofitInstance;
import com.healthapp.Retrofit.SendReportsModel;
import com.healthapp.ui.LoadingDialog;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    if (questionModel.getResponse().size() > 0 && questionModel.getResponse() != null)
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

    @Override
    public void sendReport( List<DataReports> dataReportsList, List<LocationModel> locationModelList, List<FeedBack> feedBackList ) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("data", dataReportsList);
        hashMap.put("feed_back", feedBackList);
        hashMap.put("location", locationModelList);

        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.sendReport(hashMap);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse( Call<JsonObject> call, Response<JsonObject> response ) {
                LoadingDialog.hideProgress();
                if (response.isSuccessful()) {
                    mView.showSuccessfullySendReport(Integer.parseInt(response.body().get("code").toString()));
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

