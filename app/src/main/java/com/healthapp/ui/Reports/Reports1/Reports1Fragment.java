package com.healthapp.ui.Reports.Reports1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.healthapp.R;
import com.healthapp.Retrofit.FormTypes;
import com.healthapp.Retrofit.NoDetails;
import com.healthapp.ui.Reports.ReportAdapter;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.List;

public class Reports1Fragment extends Fragment implements IReportContract.View, ReportAdapter.SpinnerClick {
    ReportAdapter reportAdapter;
    RecyclerView questionRecyclerView;
    EditSpinner formsTypeSpinner;
    List<String> formsTypeListStrings;
    List<FormTypes> formsTypeList;
    List<String> noDetailsListStrings;
    List<NoDetails> noDetailsList;
    List<String> questionsList;
    String idNoAnswer = null;
    ReportPresenterImp reportPresenterImp;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.report_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);

        initiViews(view);
        createInstance();
        setUpListeners();
    }

    private void initiViews( View view ) {
        formsTypeSpinner = view.findViewById(R.id.spinner_report_type);
        questionRecyclerView = view.findViewById(R.id.recycler_reports);
    }

    private void createInstance() {
        formsTypeList = new ArrayList<>();
        formsTypeListStrings = new ArrayList<>();
        questionsList = new ArrayList<>();
        noDetailsList = new ArrayList<>();
        noDetailsListStrings = new ArrayList<>();
        reportPresenterImp = new ReportPresenterImp(this, getActivity());
        reportPresenterImp.getNoDetailsList();
        reportPresenterImp.getFormsType();
    }


    private void setUpListeners() {

        formsTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View view, int i, long l ) {
                int idFormType = formsTypeList.get(i).getId();
            }
        });
    }


    @Override
    public void showFormsType( List<FormTypes> formsTypesList ) {
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
        for (int i = 0; i < noDetailsList.size(); i++) {
            noDetailsListStrings.add(noDetailsList.get(i).getAnswer());
        }

        reportAdapter = new ReportAdapter(noDetailsListStrings, this, getActivity());


        // for test
        questionsList.add("هل يوجد مطفأة حرائق ؟ ");
        questionsList.add("هل يوجد مطفأة حرائق ؟ ");
        questionsList.add("هل يوجد مطفأة حرائق ؟ ");
        questionsList.add("هل يوجد مطفأة حرائق ؟ ");
        questionsList.add("هل يوجد مطفأة حرائق ؟ ");
        questionsList.add("هل يوجد مطفأة حرائق ؟ ");
        questionsList.add("هل يوجد مطفأة حرائق ؟ ");
        questionsList.add("هل يوجد مطفأة حرائق ؟ ");
        questionsList.add("هل يوجد مطفأة حرائق ؟ ");
        questionsList.add("هل يوجد مطفأة حرائق ؟ ");
        questionsList.add("هل يوجد مطفأة حرائق ؟ ");
        reportAdapter.setQuestionsList(questionsList);



        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        questionRecyclerView.setLayoutManager(manager);
        questionRecyclerView.setAdapter(reportAdapter);
    }

    @Override
    public void onSpinnerClick( int pos ) {
        idNoAnswer = String.valueOf(noDetailsList.get(pos).getId());
    }
}
