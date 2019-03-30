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
    private String errorType;
    private String errorDesc;

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
                    Log.i("lastVisitsFormType_api", "success");
                    if (lastVisitsFormTypeModel.getResponse().size() > 0)
                        mView.showUserVisitsFormTypeList(lastVisitsFormTypeModel.getResponse());
                } else {
                    Log.i("lastVisitsFormType_api", "not success");
                }
            }

            @Override
            public void onFailure( Call<LastVisitsFormTypeModel> call, Throwable t ) {
                Log.i("lastVisitsFormType_api", "failure" + t.getMessage());
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
                Log.i("lastVisitsFormType_api", "failure with error : " + errorType + " ," + errorDesc);
            }

        });
    }

}
