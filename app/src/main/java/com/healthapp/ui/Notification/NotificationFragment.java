package com.healthapp.ui.Notification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthapp.Prefs.PreferencesHelper;
import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.R;
import com.healthapp.Retrofit.Notifications;

import java.util.List;

public class NotificationFragment extends Fragment implements INotificationContract.View {
    NotificationsPresenterImp notificationsPresenterImp;
    NotificationsAdapter notificationsAdapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        return inflater.inflate(R.layout.notification_fragment, container, false);
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);

        setUpRecyclerView(view);
        createInstance();
    }

    private void createInstance() {
        notificationsAdapter = new NotificationsAdapter();
        notificationsPresenterImp = new NotificationsPresenterImp(this, getActivity());
        notificationsPresenterImp.getListNotification(PreferencesHelperImp.getInstance().getUserId());
    }

    private void setUpRecyclerView( View view ) {
        recyclerView = view.findViewById(R.id.recycler_notifications);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void showListNotifications( List<Notifications> notificationsList ) {
        notificationsAdapter.setNotificationsList(notificationsList);
        recyclerView.setAdapter(notificationsAdapter);
    }
}
