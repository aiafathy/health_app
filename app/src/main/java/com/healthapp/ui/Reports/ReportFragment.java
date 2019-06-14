package com.healthapp.ui.Reports;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.healthapp.R;
import com.healthapp.Retrofit.FeedBack;
import com.healthapp.Retrofit.FormTypes;
import com.healthapp.Retrofit.LocationModel;
import com.healthapp.Retrofit.NoDetails;
import com.healthapp.Retrofit.Questions;
import com.healthapp.ui.DepthPageTransformer;
import com.healthapp.ui.FeedbackDialog;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.List;

import static com.healthapp.Prefs.Constant.MY_PERMISSIONS_REQUEST_LOCATION;

public class ReportFragment extends Fragment implements IReportContract.View {
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView next;
    ReportViewPagerAdapter reportViewPagerAdapter;
    CurrentLocation currentLocation;
    List<String> noDetailsListStrings;
    List<NoDetails> noDetailsList;
    List<String> questionsListStrings;
    List<Questions> questionsList;
    ReportPresenterImp reportPresenterImp;
    List<FeedBack> feedBackList;
    FeedbackDialog feedbackDialog;
    List<LocationModel> locationModelList;
    int idFormType;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        Bundle bundle = getArguments();
        idFormType = bundle.getInt("id_form_type", 0);
        Log.i("id_form_type", ": " + idFormType);
        return view;
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
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if (reportViewPagerAdapter.isAllQuestionsIsAnswered()) {
                    currentLocation.connectGPS();
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
                                reportPresenterImp.sendReport(reportViewPagerAdapter.getAnswers(), locationModelList, feedBackList);

                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "من فضلك قم بتشغيل GPS", Toast.LENGTH_SHORT).show();
                    }
                    Log.i("location", "lat: " + currentLocation.getLatitude() + " lang: " + currentLocation.getLongitude());

                } else
                    Toast.makeText(getActivity(), "من فضلك أجب علي كل الاسئلة", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void createInstance() {
        questionsList = new ArrayList<>();
        noDetailsList = new ArrayList<>();
        feedBackList = new ArrayList<>();
        noDetailsListStrings = new ArrayList<>();
        questionsListStrings = new ArrayList<>();
        locationModelList = new ArrayList<>();
        currentLocation = new CurrentLocation(getActivity());
        reportPresenterImp = new ReportPresenterImp(this, getActivity());
        reportViewPagerAdapter = new ReportViewPagerAdapter(getActivity());
        reportPresenterImp.getNoDetailsList();
        reportPresenterImp.getAllQuestions(idFormType);
    }

    private void initiViews( View view ) {
        next = view.findViewById(R.id.next);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
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
    public void showNoDetailsList( List<NoDetails> noDetailsList ) {
        this.noDetailsList = noDetailsList;
        reportViewPagerAdapter.setNoAnswersList(noDetailsList);
    }

    @Override
    public void showAllQuestions( List<Questions> questionsList ) {

        // to clear list when user try to get data again
        this.questionsList.clear();

        this.questionsList = questionsList;
        for (int i = 0; i < questionsList.size(); i++) {
            questionsListStrings.add(questionsList.get(i).getName());
        }

        reportViewPagerAdapter.setQuestionsList(questionsList);
        viewPager.setAdapter(reportViewPagerAdapter);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.setOffscreenPageLimit(questionsList.size());

    }

    @Override
    public void showSuccessfullySendReport( int code ) {
        if (code == 200)
            Toast.makeText(getActivity(), "تم ارسال التقرير بنجاح", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), "حدث خطا يرجي المحاولة مرة اخري", Toast.LENGTH_SHORT).show();

    }

    public void addFragment( Fragment fragment ) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
