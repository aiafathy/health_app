package com.healthapp.Retrofit;

import android.util.Log;

import com.healthapp.Prefs.PreferencesHelper;
import com.healthapp.Prefs.PreferencesHelperImp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit = null;

    public static OkHttpClient getHeader() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept( Chain chain ) throws IOException {
                                Request request = null;
                                Log.d("--Authorization-- ", "Bearer " + PreferencesHelperImp.getInstance().getUserToken());

                                Request original = chain.request();
                                // Request customization: add request headers
                                Request.Builder requestBuilder = original.newBuilder()
                                        .addHeader("Authorization", "Bearer " + PreferencesHelperImp.getInstance().getUserToken())
                                        .addHeader("Accept", "application/json");

                                request = requestBuilder.build();

                                return chain.proceed(request);
                            }
                        })
                .build();
        return okClient;

    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("https://4c2fec04.ngrok.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getHeader())
                    .build();
        }

        return retrofit;
    }

}
