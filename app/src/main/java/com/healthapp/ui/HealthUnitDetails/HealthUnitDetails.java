package com.healthapp.ui.HealthUnitDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.R;
import com.healthapp.Retrofit.HealthUnit;
import com.healthapp.ui.Reports.ReportsActivity;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.List;

public class HealthUnitDetails extends AppCompatActivity implements IHealthUnitDetailsContract.View {

    EditSpinner spinnerTown, spinnerManagement, spinnerUnit;
    List<String> townListString, managementListString, unitListString;
    List<HealthUnit> townList, managementList, unitList;
    Button next;
    HealthUnitDetailsImp healthUnitDetailsImp;
    int idTown, idManagement;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_unit_details);

        initiViews();
        getInstances();
        setListener();
    }

    private void getInstances() {
        townListString = new ArrayList<>();
        managementListString = new ArrayList<>();
        unitListString = new ArrayList<>();
        townList = new ArrayList<>();
        managementList = new ArrayList<>();
        unitList = new ArrayList<>();
        healthUnitDetailsImp = new HealthUnitDetailsImp(this, HealthUnitDetails.this);
        healthUnitDetailsImp.getTownList();
    }

    @SuppressLint("ClickableViewAccessibility")
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


        spinnerTown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch( View view, MotionEvent event ) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (townList.size() == 0)
                        healthUnitDetailsImp.getTownList();
                }

                return false;
            }
        });

        spinnerManagement.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch( View view, MotionEvent event ) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (managementList.size() == 0)
                        healthUnitDetailsImp.getManagementList(idTown);
                }
                return false;
            }
        });

        spinnerUnit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch( View view, MotionEvent event ) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (unitList.size() == 0)
                        healthUnitDetailsImp.getUnitsList(idManagement);
                }

                return false;
            }
        });


        spinnerTown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View view, int i, long l ) {
                idTown = townList.get(i).getId();
                healthUnitDetailsImp.getManagementList(idTown);

            }
        });
        spinnerManagement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View view, int i, long l ) {
                idManagement = managementList.get(i).getId();
                healthUnitDetailsImp.getUnitsList(idManagement);
            }
        });

        spinnerUnit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View view, int i, long l ) {
                int unitID = unitList.get(i).getId();
                PreferencesHelperImp.getInstance().setUnitId(unitID);
            }
        });
    }

    private void initiViews() {
        spinnerTown = findViewById(R.id.spinner_twon);
        spinnerManagement = findViewById(R.id.spinner_management);
        spinnerUnit = findViewById(R.id.spinner_unit);
        next = findViewById(R.id.button_next);
    }

    @Override
    public void showTownList( List<HealthUnit> townList ) {
        // to clear list when user try to get data again
        this.townList.clear();
        this.townListString.clear();

        this.townList = townList;
        for (int i = 0; i < townList.size(); i++) {
            townListString.add(townList.get(i).getName());

        }

        ArrayAdapter<String> townAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, townListString);
        spinnerTown.setAdapter(townAdapter);
    }

    @Override
    public void showManagementList( List<HealthUnit> managementList ) {
        // to clear list when user try to get data again
        this.managementList.clear();
        this.managementListString.clear();

        this.managementList = managementList;
        for (int i = 0; i < managementList.size(); i++) {
            managementListString.add(managementList.get(i).getName());
        }

        ArrayAdapter<String> managementAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, managementListString);
        spinnerManagement.setAdapter(managementAdapter);

    }

    @Override
    public void showUnitsList( List<HealthUnit> unitList ) {
        // to clear list when user try to get data again
        this.unitList.clear();
        this.unitListString.clear();

        this.unitList = unitList;
        for (int i = 0; i < unitList.size(); i++) {
            unitListString.add(unitList.get(i).getName());
        }

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, unitListString);
        spinnerUnit.setAdapter(unitAdapter);
    }
}
