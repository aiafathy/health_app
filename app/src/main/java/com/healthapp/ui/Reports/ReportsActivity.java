package com.healthapp.ui.Reports;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.healthapp.R;
import com.healthapp.Retrofit.FeedBack;
import com.healthapp.Retrofit.FormTypes;
import com.healthapp.Retrofit.LocationModel;
import com.healthapp.Retrofit.NoDetails;
import com.healthapp.Retrofit.Questions;
import com.healthapp.ui.FeedbackDialog;
import com.healthapp.ui.Profile.ProfileActivity;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.List;

import static com.healthapp.Prefs.Constant.MY_PERMISSIONS_REQUEST_LOCATION;


public class ReportsActivity extends AppCompatActivity implements View.OnClickListener, IReportContract.View {
    Toolbar toolbar;
    ImageButton notification, profile, logout;
    Button send;
    CurrentLocation currentLocation;
    ReportAdapter reportAdapter;
    RecyclerView questionRecyclerView;
    EditSpinner formsTypeSpinner;
    List<String> formsTypeListStrings;
    List<FormTypes> formsTypeList;
    List<String> noDetailsListStrings;
    List<NoDetails> noDetailsList;
    List<String> questionsListStrings;
    List<Questions> questionsList;
    ReportPresenterImp reportPresenterImp;
    List<FeedBack> feedBackList;
    FeedbackDialog feedbackDialog;
    List<LocationModel> locationModelList;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        initiViews();
        createInstance();
        setListeners();
        currentLocation.buildGoogleApi();
    }

    private void setListeners() {
        notification.setOnClickListener(this);
        profile.setOnClickListener(this);
        logout.setOnClickListener(this);
        send.setOnClickListener(this);

        formsTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View view, int i, long l ) {
                int idFormType = formsTypeList.get(i).getId();
                reportPresenterImp.getAllQuestions(idFormType);
            }
        });

        formsTypeSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch( View view, MotionEvent event ) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (formsTypeList.size() == 0)
                        reportPresenterImp.getFormsType();
                }
                return false;
            }
        });
    }

    private void createInstance() {
        formsTypeList = new ArrayList<>();
        formsTypeListStrings = new ArrayList<>();
        questionsList = new ArrayList<>();
        noDetailsList = new ArrayList<>();
        feedBackList = new ArrayList<>();
        noDetailsListStrings = new ArrayList<>();
        questionsListStrings = new ArrayList<>();
        locationModelList = new ArrayList<>();
        currentLocation = new CurrentLocation(ReportsActivity.this);
        reportPresenterImp = new ReportPresenterImp(this, ReportsActivity.this);
        reportPresenterImp.getNoDetailsList();
    }

    private void initiViews() {
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        notification = findViewById(R.id.notification);
        profile = findViewById(R.id.profile);
        logout = findViewById(R.id.logout);
        send = findViewById(R.id.button_send);
        formsTypeSpinner = findViewById(R.id.spinner_report_type);
        questionRecyclerView = findViewById(R.id.recycler_reports);

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

                if (currentLocation.getLatitude() != 0 && currentLocation.getLongitude() != 0) {
                    showFeedBack();
                }
                feedbackDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss( DialogInterface dialogInterface ) {
                        FeedBack feedBack = new FeedBack();
                        if (feedbackDialog.getFeedback() != null) {
                            Log.i("feedback",feedbackDialog.getFeedback());
                            feedBack.setFeedBack(feedbackDialog.getFeedback());
                        } else {
                            showFeedBack();
                        }

                        feedBackList.add(feedBack);

                        LocationModel locationModel = new LocationModel();
                        locationModel.setLang(String.valueOf(currentLocation.getLongitude()));
                        locationModel.setLat(String.valueOf(currentLocation.getLatitude()));
                        locationModelList.add(locationModel);
                        Log.i("reportData", locationModelList.get(0).toString() + " , " + feedBackList.get(0).toString() + " , " +
                                reportAdapter.getAnswers().get(0).getAnswer());
                        reportPresenterImp.sendReport(reportAdapter.getAnswers(), locationModelList,feedBackList);

                    }
                });
                Log.i("location", "lat: " + currentLocation.getLatitude() + " lang: " + currentLocation.getLongitude());
                break;
        }

    }

    private void showFeedBack() {
        feedbackDialog = new FeedbackDialog(ReportsActivity.this);
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

    @Override
    public void showFormsType( List<FormTypes> formsTypesList ) {
        // to clear list when user try to get data again
        this.formsTypeList.clear();
        this.formsTypeListStrings.clear();

        this.formsTypeList = formsTypesList;
        for (int i = 0; i < formsTypesList.size(); i++) {
            formsTypeListStrings.add(formsTypesList.get(i).getName());
        }

        ArrayAdapter<String> reportsAdapter = new ArrayAdapter<String>(ReportsActivity.this, android.R.layout.simple_spinner_dropdown_item, formsTypeListStrings);
        formsTypeSpinner.setAdapter(reportsAdapter);

    }

    @Override
    public void showNoDetailsList( List<NoDetails> noDetailsList ) {
        this.noDetailsList = noDetailsList;
        reportAdapter = new ReportAdapter(noDetailsList, ReportsActivity.this);
    }

    @Override
    public void showAllQuestions( List<Questions> questionsList ) {
        // to clear list when user try to get data again
        this.questionsList.clear();
        this.questionsListStrings.clear();

        this.questionsList = questionsList;
        for (int i = 0; i < questionsList.size(); i++) {
            questionsListStrings.add(questionsList.get(i).getName());
        }

        LinearLayoutManager manager = new LinearLayoutManager(ReportsActivity.this, LinearLayoutManager.VERTICAL, false);
        questionRecyclerView.setLayoutManager(manager);
        reportAdapter.setQuestionsList(questionsList);
        questionRecyclerView.setAdapter(reportAdapter);
    }

    @Override
    public void showSuccessfullySendReport() {
        Toast.makeText(this, "تم ارسال التقرير بنجاح", Toast.LENGTH_SHORT).show();
    }
}