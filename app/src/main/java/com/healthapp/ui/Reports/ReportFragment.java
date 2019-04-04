package com.healthapp.ui.Reports;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.R;
import com.healthapp.Retrofit.FeedBack;
import com.healthapp.Retrofit.FormTypes;
import com.healthapp.Retrofit.LocationModel;
import com.healthapp.Retrofit.NoDetails;
import com.healthapp.Retrofit.Questions;
import com.healthapp.ui.FeedbackDialog;
import com.healthapp.ui.Login.LoginActivity;
import com.healthapp.ui.Notification.NotificationFragment;
import com.healthapp.ui.Profile.ProfileFragment.ProfileFragment;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.List;

import static com.healthapp.Prefs.Constant.MY_PERMISSIONS_REQUEST_LOCATION;

public class ReportFragment extends Fragment implements View.OnClickListener, IReportContract.View {
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

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);


        initiViews(view);
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
                if (noDetailsList.size() > 0)
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
        currentLocation = new CurrentLocation(getActivity());
        reportPresenterImp = new ReportPresenterImp(this, getActivity());
        reportPresenterImp.getNoDetailsList();
    }

    private void initiViews( View view ) {
        toolbar = view.findViewById(R.id.tool_bar);
        notification = view.findViewById(R.id.notification);
        profile = view.findViewById(R.id.profile);
        logout = view.findViewById(R.id.logout);
        send = view.findViewById(R.id.button_send);
        formsTypeSpinner = view.findViewById(R.id.spinner_report_type);
        questionRecyclerView = view.findViewById(R.id.recycler_reports);

    }

    @Override
    public void onClick( View view ) {
        switch (view.getId()) {
            case R.id.notification:
                addFragment(new NotificationFragment());
                break;

            case R.id.logout:
                PreferencesHelperImp.getInstance().removeAllValues();
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;

            case R.id.profile:
                addFragment(new ProfileFragment());
                break;

            case R.id.button_send:
                if (reportAdapter.isAllQuestionsIsAnswered()) {
                    if (currentLocation.getLatitude() != 0 && currentLocation.getLongitude() != 0) {
                        showFeedBack();
                        feedbackDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss( DialogInterface dialogInterface ) {
                                //to clear data
                                feedBackList.clear();
                                locationModelList.clear();

                                FeedBack feedBack = new FeedBack();
                                if (feedbackDialog.getFeedback() != null) {
                                    Log.i("feedback", feedbackDialog.getFeedback());
                                    feedBack.setFeedBack(feedbackDialog.getFeedback());
                                }

                                feedBackList.add(feedBack);

                                LocationModel locationModel = new LocationModel();
                                locationModel.setLang(String.valueOf(currentLocation.getLongitude()));
                                locationModel.setLat(String.valueOf(currentLocation.getLatitude()));
                                locationModelList.add(locationModel);
                                reportPresenterImp.sendReport(reportAdapter.getAnswers(), locationModelList, feedBackList);

                            }
                        });
                    }
                    Log.i("location", "lat: " + currentLocation.getLatitude() + " lang: " + currentLocation.getLongitude());

                } else
                    Toast.makeText(getActivity(), "من فضلك أجب علي كل الاسئلة", Toast.LENGTH_SHORT).show();

                break;
        }

    }

    private void showFeedBack() {
        feedbackDialog = new FeedbackDialog(getActivity());
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
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (currentLocation.isLocationEnabled()) {
                            currentLocation.getLocation();
                        } else {
                            Toast.makeText(getActivity(), "من فضلك قم بتشغيل GPS أولا", Toast.LENGTH_SHORT).show();
                        }

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
    public void onStop() {
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

        ArrayAdapter<String> reportsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, formsTypeListStrings);
        formsTypeSpinner.setAdapter(reportsAdapter);

    }

    @Override
    public void showNoDetailsList( List<NoDetails> noDetailsList ) {
        this.noDetailsList = noDetailsList;
        reportAdapter = new ReportAdapter(noDetailsList, getActivity());
    }

    @Override
    public void showAllQuestions( List<Questions> questionsList ) {
        // to clear list when user try to get data again
        this.questionsList.clear();

        this.questionsList = questionsList;
        for (int i = 0; i < questionsList.size(); i++) {
            questionsListStrings.add(questionsList.get(i).getName());
        }

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        questionRecyclerView.setLayoutManager(manager);
        reportAdapter.setQuestionsList(questionsList);
        questionRecyclerView.setAdapter(reportAdapter);
    }

    @Override
    public void showSuccessfullySendReport() {
        Toast.makeText(getActivity(), "تم ارسال التقرير بنجاح", Toast.LENGTH_SHORT).show();
    }

    public void addFragment( Fragment fragment ) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
