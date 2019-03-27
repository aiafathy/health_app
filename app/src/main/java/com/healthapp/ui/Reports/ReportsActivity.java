package com.healthapp.ui.Reports;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.healthapp.R;
import com.healthapp.ui.Profile.ProfileActivity;
import com.healthapp.ui.Reports.Reports1.Reports1Fragment;


public class ReportsActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageButton notification, profile, logout;

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
    }

    private void initiViews() {
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        notification = findViewById(R.id.notification);
        profile = findViewById(R.id.profile);
        logout = findViewById(R.id.logout);
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
        }
    }
}