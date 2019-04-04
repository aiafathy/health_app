package com.healthapp.ui.Notification;

import android.content.Context;
import android.widget.Toast;

import com.healthapp.Retrofit.ApiInterface;
import com.healthapp.Retrofit.LastVisitsModel;
import com.healthapp.Retrofit.NotificationsModel;
import com.healthapp.Retrofit.RetrofitInstance;
import com.healthapp.ui.LoadingDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsPresenterImp implements INotificationContract.Presenter {

    INotificationContract.View mView;
    Context context;

    public NotificationsPresenterImp( INotificationContract.View mView, Context context ) {
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void getListNotification( int userId ) {
        LoadingDialog.showProgress(context);
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<NotificationsModel> call = apiInterface.getNotifications(userId);
        call.enqueue(new Callback<NotificationsModel>() {
            @Override
            public void onResponse( Call<NotificationsModel> call, Response<NotificationsModel> response ) {
                LoadingDialog.hideProgress();
                if (response.isSuccessful()) {
                    NotificationsModel notificationsModel = response.body();
                    if (notificationsModel.getResponse().size() > 0)
                        mView.showListNotifications(notificationsModel.getResponse());
                }
            }

            @Override
            public void onFailure( Call<NotificationsModel> call, Throwable t ) {
                LoadingDialog.hideProgress();
                Toast.makeText(context, "من فضلك تحقق من اتصالك بالانترنت", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
