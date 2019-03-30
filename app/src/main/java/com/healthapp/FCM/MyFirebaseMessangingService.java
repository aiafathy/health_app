package com.healthapp.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.healthapp.R;
import com.healthapp.ui.Profile.ProfileActivity;


public class MyFirebaseMessangingService extends FirebaseMessagingService {

    public MyFirebaseMessangingService() {
    }

    @Override
    public void onMessageReceived( RemoteMessage remoteMessage ) {
        super.onMessageReceived(remoteMessage);

        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    public void sendNotification( String title, String message ) {

        //TODO OPEN NOTIFICATION ACTIVITY
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setSmallIcon(R.drawable.notification);
        notification.setContentTitle(title);
        notification.setContentText(message);
        notification.setAutoCancel(true);
        notification.setSound(defaultUri);
        notification.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification.build());
    }
}
