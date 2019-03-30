package com.healthapp.ui.HealthUnitDetails;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.R;
import com.healthapp.Retrofit.HealthUnit;
import com.healthapp.ui.Login.LoginActivity;
import com.healthapp.ui.Reports.ReportsActivity;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.List;

public class HealthUnitDetails extends AppCompatActivity implements IHealthUnitDetailsContract.View, AdapterView.OnItemClickListener, View.OnTouchListener {

    EditSpinner spinnerTown, spinnerManagement, spinnerUnit;
    List<String> townListString, managementListString, unitListString;
    List<HealthUnit> townList, managementList, unitList;
    Button next;
    HealthUnitDetailsImp healthUnitDetailsImp;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_unit_details);

        initiViews();
        getInstances();
        setListener();
        setUpFCM();
    }

    private void getInstances() {
        townListString = new ArrayList<>();
        managementListString = new ArrayList<>();
        unitListString = new ArrayList<>();
        townList = new ArrayList<>();
        managementList = new ArrayList<>();
        unitList = new ArrayList<>();
        healthUnitDetailsImp = new HealthUnitDetailsImp(this, HealthUnitDetails.this);
    }

    private void setListener() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if (!spinnerTown.getText().toString().isEmpty() && !spinnerManagement.getText().toString().isEmpty() && !spinnerUnit.getText().toString().isEmpty())
                    startActivity(new Intent(HealthUnitDetails.this, ReportsActivity.class));
                else {
                    Toast.makeText(HealthUnitDetails.this, "من فضلك حدد البيانات المطلوبة", Toast.LENGTH_SHORT).show();
                }
            }
        });
        spinnerTown.setOnTouchListener(this);
        spinnerTown.setOnItemClickListener(this);
        spinnerManagement.setOnItemClickListener(this);
    }

    private void initiViews() {
        spinnerTown = findViewById(R.id.spinner_twon);
        spinnerManagement = findViewById(R.id.spinner_management);
        spinnerUnit = findViewById(R.id.spinner_unit);
        next = findViewById(R.id.button_next);
    }

    @Override
    public void showTownList( List<HealthUnit> townList ) {
        this.townList = townList;
        for (int i = 0; i < townList.size(); i++) {
            townListString.add(townList.get(i).getName());
        }

        ArrayAdapter<String> townAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, townListString);
        spinnerTown.setAdapter(townAdapter);
    }

    @Override
    public void showManagementList( List<HealthUnit> managementList ) {
        this.managementList = managementList;
        for (int i = 0; i < managementList.size(); i++) {
            managementListString.add(managementList.get(i).getName());
        }

        ArrayAdapter<String> managementAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, managementListString);
        spinnerManagement.setAdapter(managementAdapter);

    }

    @Override
    public void showUnitsList( List<HealthUnit> unitList ) {
        this.unitList = unitList;
        for (int i = 0; i < unitList.size(); i++) {
            unitListString.add(unitList.get(i).getName());
        }

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, unitListString);
        spinnerUnit.setAdapter(unitAdapter);
    }


    // for FCM

    private void setUpFCM() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive( Context context, Intent intent ) {
                if (intent.getAction().equals("fcm_intent")) {
                    Log.i("fcm_token", "token: " + intent.getExtras().getString("token"));
                    FirebaseMessaging.getInstance().subscribeToTopic("Global");
                }
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter("reg_id"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onItemClick( AdapterView<?> adapterView, View view, int i, long l ) {
        switch (view.getId()) {
            case R.id.spinner_twon:
                int idTown = townList.get(i).getId();
                healthUnitDetailsImp.getManagementList(idTown);
                break;

            case R.id.spinner_management:
                int idManagement = managementList.get(i).getId();
                healthUnitDetailsImp.getUnitsList(idManagement);
                break;
        }
    }

    @Override
    public boolean onTouch( View view, MotionEvent event ) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            healthUnitDetailsImp.getTownList();
        }
        return true;
    }
}
