package com.healthapp.FCM;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.healthapp.R;
import com.healthapp.ui.Reports.ReportsActivity;


public class MyFirebaseMessangingService extends FirebaseMessagingService {

    public MyFirebaseMessangingService() {
    }

    @Override
    public void onMessageReceived( RemoteMessage remoteMessage ) {
        super.onMessageReceived(remoteMessage);

        createNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    @SuppressLint("NewApi")
    public void createNotification( String title, String message ) {

        //TODO OPEN NOTIFICATION ACTIVITY
        Intent intent = new Intent(this, ReportsActivity.class);
        intent.putExtra("notification", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel;

            String id1 = "BackgroundChannel";
            mChannel = new NotificationChannel(id1, "Health App",  //name of the channel
                    NotificationManager.IMPORTANCE_LOW);
            mChannel.setDescription("Notification Health App");
            mChannel.enableLights(true);
            mChannel.setShowBadge(true);

            notificationManager.createNotificationChannel(mChannel);

            NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
            notification.setSmallIcon(R.drawable.logo);
            notification.setContentTitle(title);
            notification.setContentText(message);
            notification.setAutoCancel(true);
            notification.setSound(defaultUri);
            notification.setContentIntent(pendingIntent);

            notificationManager.notify(0, notification.build());
        } else {

            NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
            notification.setSmallIcon(R.drawable.notification);
            notification.setContentTitle(title);
            notification.setContentText(message);
            notification.setAutoCancel(true);
            notification.setSound(defaultUri);
            notification.setContentIntent(pendingIntent);

            notificationManager.notify(0, notification.build());
        }

    }
}
