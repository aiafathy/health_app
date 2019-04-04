package com.healthapp.ui.Notification;

import com.healthapp.Retrofit.Notifications;

import java.util.List;

public interface INotificationContract {

    interface View {
        void showListNotifications( List<Notifications> notificationsList );
    }

    interface Presenter {
        void getListNotification( int userId );
    }
}
