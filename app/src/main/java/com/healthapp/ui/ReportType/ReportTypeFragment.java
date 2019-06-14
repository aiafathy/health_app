package com.healthapp.ui.ReportType;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.healthapp.R;
import com.healthapp.Retrofit.FormTypes;
import com.healthapp.ui.Reports.ReportFragment;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.List;

public class ReportTypeFragment extends Fragment implements IReportTypeContract.View {
    ReportTypePresenterImp reportTypePresenterImp;
    List<String> formsTypeListStrings;
    List<FormTypes> formsTypeList;
    EditSpinner formsTypeSpinner;
    TextView next;
    int idFormType;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_report_type, container, false);
        return view;
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);

        initiViews(view);
        createInstance();
        setListeners();
    }

    private void createInstance() {
        formsTypeList = new ArrayList<>();
        formsTypeListStrings = new ArrayList<>();
        reportTypePresenterImp = new ReportTypePresenterImp(this, getActivity());
        reportTypePresenterImp.getReportsType();
    }

    private void initiViews( View view ) {
        formsTypeSpinner = view.findViewById(R.id.spinner_report_type);
        next = view.findViewById(R.id.next_type);
    }

    private void setListeners() {
        formsTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View view, int i, long l ) {
                idFormType = formsTypeList.get(i).getId();
                next.setVisibility(View.VISIBLE);
            }
        });

        formsTypeSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch( View view, MotionEvent event ) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (formsTypeList.size() == 0)
                        reportTypePresenterImp.getReportsType();
                }
                return false;
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Bundle bundle = new Bundle();
                bundle.putInt("id_form_type", idFormType);
                ReportFragment reportFragment = new ReportFragment();
                reportFragment.setArguments(bundle);
                addFragment(reportFragment);
            }
        });
    }

    @Override
    public void showReportsType( List<FormTypes> formTypesList ) {
        this.formsTypeList.clear();
        this.formsTypeListStrings.clear();

        this.formsTypeList = formTypesList;
        for (int i = 0; i < formTypesList.size(); i++) {
            formsTypeListStrings.add(formTypesList.get(i).getName());
        }

        ArrayAdapter<String> reportsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, formsTypeListStrings);
        formsTypeSpinner.setAdapter(reportsAdapter);
    }

    private void addFragment( Fragment fragment ) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

