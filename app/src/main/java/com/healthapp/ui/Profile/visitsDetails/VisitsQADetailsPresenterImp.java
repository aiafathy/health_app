package com.healthapp.ui.Profile.visitsDetails;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.healthapp.Retrofit.ApiInterface;
import com.healthapp.Retrofit.LastVisitsDetailsModel;
import com.healthapp.Retrofit.RetrofitInstance;
import com.healthapp.ui.LoadingDialog;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitsQADetailsPresenterImp implements IVisitsQADetailsContract.Presenter {

    IVisitsQADetailsContract.View mView;
    Context context;
    private String errorType;
    private String errorDesc;

    public VisitsQADetailsPresenterImp( IVisitsQADetailsContract.View mView, Context context ) {
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void getUserVisitsDetails( int form_id ) {
        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<LastVisitsDetailsModel> call = apiInterface.getLastVisitsDetails(form_id);
        call.enqueue(new Callback<LastVisitsDetailsModel>() {
            @Override
            public void onResponse( Call<LastVisitsDetailsModel> call, Response<LastVisitsDetailsModel> response ) {
                LoadingDialog.hideProgress();
                if (response.isSuccessful()) {
                    LastVisitsDetailsModel lastVisitsDetailsModel = response.body();
                    Log.i("lastVisitsDetails_api", "success");
                    if (lastVisitsDetailsModel.getResponse().size() > 0)
                        mView.showUserVisitsDetailsList(lastVisitsDetailsModel.getResponse());
                } else {
                    Log.i("lastVisitsDetails_api", "not success");
                }
            }

            @Override
            public void onFailure( Call<LastVisitsDetailsModel> call, Throwable t ) {
                Log.i("lastVisitsDetails_api", "failure" + t.getMessage());
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
                Log.i("lastVisitsDetails_api", "failure with error : " + errorType + " ," + errorDesc);
            }

        });
    }
}
