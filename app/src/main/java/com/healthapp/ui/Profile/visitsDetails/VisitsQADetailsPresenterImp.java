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
                    if (lastVisitsDetailsModel.getResponse().getData().size() > 0)
                        mView.showUserVisitsDetailsList(lastVisitsDetailsModel.getResponse().getData(), lastVisitsDetailsModel.getResponse().getFeedBack());
                }
            }

            @Override
            public void onFailure( Call<LastVisitsDetailsModel> call, Throwable t ) {
                LoadingDialog.hideProgress();
                Toast.makeText(context, "من فضلك تحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
