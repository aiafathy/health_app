package com.healthapp.ui.Profile.visitsDetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthapp.R;
import com.healthapp.Retrofit.LastVisitsDetails;
import com.healthapp.ui.Profile.visitsFormType.AllVisitsFormTypePresenterImp;
import com.healthapp.ui.Profile.visitsFormType.VisitsTypeFormAdapter;

import java.util.ArrayList;
import java.util.List;

public class VisitsQADetailsFragment extends Fragment implements IVisitsQADetailsContract.View {

    VisitsQADetailsAdapter visitsQADetailsAdapter;
    TextView title;
    RecyclerView allVisitsDetailsRecyclerView;
    VisitsQADetailsPresenterImp visitsQADetailsPresenterImp;
    List<String> lastVisitsDetailsString;
    int formId;
    String unitName;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.all_visits_forms_type, container, false);
        Bundle bundle = getArguments();
        formId = bundle.getInt("forms_id");
        unitName = bundle.getString("unit_name");
        return view;
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);

        initiViews(view);
        createInstance();
    }

    private void createInstance() {
        visitsQADetailsAdapter = new VisitsQADetailsAdapter();
        lastVisitsDetailsString = new ArrayList<>();
        visitsQADetailsPresenterImp = new VisitsQADetailsPresenterImp(this, getActivity());
        visitsQADetailsPresenterImp.getUserVisitsDetails(formId);
    }

    private void initiViews( View view ) {
        title = view.findViewById(R.id.title);
        allVisitsDetailsRecyclerView = view.findViewById(R.id.recycler_visits_details);
        title.setText("تفاصيل التقارير السابقة لوحدة: " + unitName);
    }


    @Override
    public void showUserVisitsDetailsList( List<LastVisitsDetails> lastVisitsList ) {
        visitsQADetailsAdapter.setLastVisitsModelList(lastVisitsList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        allVisitsDetailsRecyclerView.setLayoutManager(linearLayoutManager);
        allVisitsDetailsRecyclerView.setAdapter(visitsQADetailsAdapter);

    }
}
