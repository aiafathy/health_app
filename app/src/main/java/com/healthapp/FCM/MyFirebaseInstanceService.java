package com.healthapp.FCM;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.healthapp.Prefs.PreferencesHelperImp;

public class MyFirebaseInstanceService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);

        Intent intent = new Intent("fcm_intent");
        intent.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

    }

    private void sendRegistrationToServer( String refreshedTokens ) {
        PreferencesHelperImp.getInstance().setFcmToken(refreshedTokens);
    }

}
