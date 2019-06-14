package com.healthapp.ui.Reports;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.R;
import com.healthapp.ui.Login.LoginActivity;
import com.healthapp.ui.Notification.NotificationFragment;
import com.healthapp.ui.Profile.ProfileFragment.ProfileFragment;
import com.healthapp.ui.ReportType.ReportTypeFragment;


public class ReportsActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageButton notification, profile, logout;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        intiViews();
        setListners();

        Intent intent = getIntent();
        if (intent.getBooleanExtra("notification", false))
            addFragment(new NotificationFragment());
        else
            addFragment(new ReportTypeFragment());
    }

    private void setListners() {
        notification.setOnClickListener(this);
        profile.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    private void intiViews() {
        toolbar = findViewById(R.id.tool_bar);
        notification = findViewById(R.id.notification);
        profile = findViewById(R.id.profile);
        logout = findViewById(R.id.logout);
    }

    public void addFragment( Fragment fragment ) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick( View view ) {
        switch (view.getId()) {
            case R.id.notification:
                addFragment(new NotificationFragment());
                break;

            case R.id.logout:
                PreferencesHelperImp.getInstance().removeAllValues();
                startActivity(new Intent(ReportsActivity.this, LoginActivity.class));
                finish();
                break;

            case R.id.profile:
                addFragment(new ProfileFragment());
                break;
        }
    }
}