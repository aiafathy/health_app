package com.healthapp.Prefs;

public interface PreferencesHelper {

    boolean getUserIsLogged();

    void setUserIsLogged( boolean userIsLogged );

    String getUserToken();

    void setUserToken( String userToken );

    int getUserId();

    void setUserId( int userId );

    String getFcmToken();

    void setFcmToken( String fcmToken );

}
