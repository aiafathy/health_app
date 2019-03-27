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
import android.widget.ArrayAdapter;

import com.healthapp.R;
import com.healthapp.ui.Reports.ReportAdapter;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.List;

public class Reports1Fragment extends Fragment {
    ReportAdapter reportAdapter;
    RecyclerView questionRecyclerView;
    List<String> questionsList;
    EditSpinner reportsSpinner;
    List<String> reportsTypeList;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.report_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);

        setUpSpinner(view);
        initiRecyclerView(view);
    }

    private void setUpSpinner( View view ) {
        reportsSpinner = view.findViewById(R.id.spinner_report_type);
        reportsTypeList = new ArrayList<>();
        reportsTypeList.add("تقرير 1");
        reportsTypeList.add("تقرير 2");
        reportsTypeList.add("تقرير 3");

        ArrayAdapter<String> reportsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, reportsTypeList);
        reportsSpinner.setAdapter(reportsAdapter);
    }

    private void initiRecyclerView( View view ) {

        questionRecyclerView = view.findViewById(R.id.recycler_reports);
        reportAdapter = new ReportAdapter();
        questionsList = new ArrayList<>();

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

}
