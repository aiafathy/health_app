package com.healthapp.ui.Notification;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthapp.R;
import com.healthapp.Retrofit.Notifications;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyViewHolder> {

    List<Notifications> notificationsList;

    public void setNotificationsList( List<Notifications> notificationsList ) {
        this.notificationsList = notificationsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder( @NonNull ViewGroup viewGroup, int i ) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NonNull MyViewHolder myViewHolder, int i ) {
        myViewHolder.message.setText(notificationsList.get(i).getMessage());
    }

    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView message;

        public MyViewHolder( @NonNull View itemView ) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
        }
    }

}
