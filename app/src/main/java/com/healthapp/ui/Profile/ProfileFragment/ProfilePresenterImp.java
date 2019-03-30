package com.healthapp.ui.Profile.ProfileFragment;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.Retrofit.ApiInterface;
import com.healthapp.Retrofit.LastVisitsDetailsModel;
import com.healthapp.Retrofit.LastVisitsFormTypeModel;
import com.healthapp.Retrofit.LastVisitsModel;
import com.healthapp.Retrofit.RetrofitInstance;
import com.healthapp.Retrofit.UserModel;
import com.healthapp.ui.LoadingDialog;
import com.healthapp.ui.Profile.ProfileFragment.IProfileContract;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenterImp implements IProfileContract.Presenter {

    IProfileContract.View mView;
    Context context;
    public ProfilePresenterImp( IProfileContract.View mView, Context context ) {
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void getUserData() {
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<UserModel> call = apiInterface.getUserData();
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse( Call<UserModel> call, Response<UserModel> response ) {
                if (response.isSuccessful()) {
                    UserModel userModel = response.body();
                    mView.showName(userModel.getUser().getName());
                }
            }

            @Override
            public void onFailure( Call<UserModel> call, Throwable t ) {
            }

        });

    }

    @Override
    public void getUserVisits( int userId ) {
        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<LastVisitsModel> call = apiInterface.getLastVisits(userId);
        call.enqueue(new Callback<LastVisitsModel>() {
            @Override
            public void onResponse( Call<LastVisitsModel> call, Response<LastVisitsModel> response ) {
                LoadingDialog.hideProgress();
                if (response.isSuccessful()) {
                    LastVisitsModel lastVisitsModel = response.body();
                    if (lastVisitsModel.getResponse().size() > 0)
                        mView.showUserVisitsList(lastVisitsModel.getResponse());
                }
            }

            @Override
            public void onFailure( Call<LastVisitsModel> call, Throwable t ) {
                LoadingDialog.hideProgress();
                Toast.makeText(context, "من فضلك تحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
