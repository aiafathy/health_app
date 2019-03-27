package com.healthapp.ui.Profile;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.Retrofit.ApiInterface;
import com.healthapp.Retrofit.HealthUnitModel;
import com.healthapp.Retrofit.RetrofitInstance;
import com.healthapp.Retrofit.UserModel;
import com.healthapp.ui.LoadingDialog;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenterImp implements IProfileContract.Presenter {

    IProfileContract.View mView;
    Context context;
    private String errorType;
    private String errorDesc;

    public ProfilePresenterImp( IProfileContract.View mView, Context context ) {
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void getUserData() {
        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Log.i("token_user", PreferencesHelperImp.getInstance().getUserToken());
        Call<UserModel> call = apiInterface.getUserData();
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse( Call<UserModel> call, Response<UserModel> response ) {
                if (response.isSuccessful()) {
                    UserModel userModel = response.body();
                    Log.i("user_data_api", "success");
                    LoadingDialog.hideProgress();
                    mView.showName(userModel.getUser().getName());

                } else {
                    Log.i("user_data_api", "not success");
                    LoadingDialog.hideProgress();
                }

            }

            @Override
            public void onFailure( Call<UserModel> call, Throwable t ) {
                Log.i("user_data_api", "failure" + t.getMessage());
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
                Log.i("user_data_api", "failure with error : " + errorType + " ," + errorDesc);
            }

        });

    }
}
