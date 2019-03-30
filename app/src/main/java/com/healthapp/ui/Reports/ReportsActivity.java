package com.healthapp.ui.Reports;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.healthapp.R;
import com.healthapp.ui.FeedbackDialog;
import com.healthapp.ui.Profile.ProfileActivity;
import com.healthapp.ui.Reports.Reports1.Reports1Fragment;


public class ReportsActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageButton notification, profile, logout;
    Button send;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        initiViews();
        addFragment(new Reports1Fragment());
        setListeners();
    }

    private void setListeners() {
        notification.setOnClickListener(this);
        profile.setOnClickListener(this);
        logout.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    private void initiViews() {
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        notification = findViewById(R.id.notification);
        profile = findViewById(R.id.profile);
        logout = findViewById(R.id.logout);
        send = findViewById(R.id.button_send);
    }

    public void addFragment( Fragment fragment ) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    @Override
    public void onClick( View view ) {
        switch (view.getId()) {
            case R.id.notification:
                break;

            case R.id.logout:

                break;

            case R.id.profile:
                startActivity(new Intent(ReportsActivity.this, ProfileActivity.class));
                break;

            case R.id.button_send:
                showFeedBack();
                break;
        }
    }

    private void showFeedBack() {
        FeedbackDialog feedbackDialog = new FeedbackDialog(ReportsActivity.this);
        feedbackDialog.setCancelable(false);
        feedbackDialog.setCanceledOnTouchOutside(false);
        feedbackDialog.show();
    }
}