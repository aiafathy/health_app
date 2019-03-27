package com.healthapp.Prefs;

import android.app.Application;
import android.content.Intent;

import com.healthapp.ui.HealthUnitDetails.HealthUnitDetails;
import com.healthapp.ui.Login.LoginActivity;

public class ContextApplication extends Application {

    private static ContextApplication instance;

    public ContextApplication() {
        instance = this;
    }

    public static ContextApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        checkAuth();
    }

    private void checkAuth() {

        if (!PreferencesHelperImp.getInstance().getUserIsLogged()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}