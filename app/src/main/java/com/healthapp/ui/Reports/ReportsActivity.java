package com.healthapp.ui.Reports;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.healthapp.R;
import com.healthapp.ui.FeedbackDialog;
import com.healthapp.ui.Profile.ProfileActivity;
import com.healthapp.ui.Reports.Reports1.Reports1Fragment;

import static com.healthapp.Prefs.Constant.MY_PERMISSIONS_REQUEST_LOCATION;


public class ReportsActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageButton notification, profile, logout;
    Button send;
    CurrentLocation currentLocation;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        initiViews();
        addFragment(new Reports1Fragment());
        setListeners();
        currentLocation = new CurrentLocation(ReportsActivity.this);
        currentLocation.buildGoogleApi();
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
                currentLocation.getLocation();

                if (currentLocation.getLatitude() != 0 && currentLocation.getLongitude() != 0)
                    showFeedBack();

                Log.i("location", "lat: " + currentLocation.getLatitude() + " lang: " + currentLocation.getLongitude());
                break;
        }
    }

    private void showFeedBack() {
        FeedbackDialog feedbackDialog = new FeedbackDialog(ReportsActivity.this);
        feedbackDialog.setCancelable(false);
        feedbackDialog.setCanceledOnTouchOutside(false);
        feedbackDialog.show();
    }

    @Override
    public void onRequestPermissionsResult( int requestCode,
                                            String permissions[], int[] grantResults ) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        currentLocation.getLocation();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        currentLocation.close();
    }
}