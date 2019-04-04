package com.healthapp.Prefs;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelperImp implements PreferencesHelper {

    SharedPreferences preferences;
    Context context;
    private static PreferencesHelperImp instance;

    public static PreferencesHelperImp getInstance() {
        if (instance == null) {
            instance = new PreferencesHelperImp();
        }
        return instance;
    }

    private PreferencesHelperImp() {
        this.context = ContextApplication.getInstance();
    }


    @Override
    public boolean getUserIsLogged() {
        return context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE).getBoolean(Constant.IS_LOGGED, false);
    }

    @Override
    public void setUserIsLogged( boolean userIsLogged ) {
        preferences = context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constant.IS_LOGGED, userIsLogged);
        editor.apply();
    }

    @Override
    public String getFcmToken() {
        return context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE).getString(Constant.FCM_TOKEN, "null");
    }

    @Override
    public void setFcmToken( String fcmToken ) {
        preferences = context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constant.FCM_TOKEN, fcmToken);
        editor.apply();
    }

    @Override
    public String getUserToken() {
        return context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE).getString(Constant.TOKEN, "null");
    }

    @Override
    public void setUserToken( String userToken ) {
        preferences = context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constant.TOKEN, userToken);
        editor.apply();
    }

    @Override
    public int getUserId() {
        return context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE).getInt(Constant.USER_ID, 0);
    }

    @Override
    public void setUserId( int userId ) {
        preferences = context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constant.USER_ID, userId);
        editor.apply();
    }

    @Override
    public int getUnitId() {
        return context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE).getInt(Constant.UNIT_ID, 0);
    }

    @Override
    public void setUnitId( int unitId ) {
        preferences = context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constant.UNIT_ID, unitId);
        editor.apply();
    }

    @Override
    public void removeAllValues() {
        preferences = context.getSharedPreferences(Constant.SharedPreferencesName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
