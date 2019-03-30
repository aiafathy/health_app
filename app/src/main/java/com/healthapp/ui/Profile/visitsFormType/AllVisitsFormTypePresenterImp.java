package com.healthapp.ui.Profile.visitsFormType;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.healthapp.Retrofit.ApiInterface;
import com.healthapp.Retrofit.LastVisitsFormTypeModel;
import com.healthapp.Retrofit.RetrofitInstance;
import com.healthapp.ui.LoadingDialog;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllVisitsFormTypePresenterImp implements IAllVisitsFormTypeContract.Presenter {
    Context context;
    IAllVisitsFormTypeContract.View mView;

    public AllVisitsFormTypePresenterImp( Context context, IAllVisitsFormTypeContract.View mView ) {
        this.context = context;
        this.mView = mView;
    }

    @Override
    public void getUserVisitsFormType( int unit_id ) {
        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<LastVisitsFormTypeModel> call = apiInterface.getLastVisitsFormType(unit_id);
        call.enqueue(new Callback<LastVisitsFormTypeModel>() {
            @Override
            public void onResponse( Call<LastVisitsFormTypeModel> call, Response<LastVisitsFormTypeModel> response ) {
                LoadingDialog.hideProgress();
                if (response.isSuccessful()) {
                    LastVisitsFormTypeModel lastVisitsFormTypeModel = response.body();
                    if (lastVisitsFormTypeModel.getResponse().size() > 0)
                        mView.showUserVisitsFormTypeList(lastVisitsFormTypeModel.getResponse());
                }
            }

            @Override
            public void onFailure( Call<LastVisitsFormTypeModel> call, Throwable t ) {
                LoadingDialog.hideProgress();
                Toast.makeText(context, "من فضلك تحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
            }

        });
    }

}
