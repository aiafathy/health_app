package com.healthapp.ui.Profile.visitsFormType;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthapp.R;
import com.healthapp.Retrofit.LastVisitsFormType;
import com.healthapp.ui.Profile.ProfileFragment.AllVisitsAdapter;
import com.healthapp.ui.Profile.visitsDetails.VisitsQADetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class AllVisitsFormTypeFragment extends Fragment implements IAllVisitsFormTypeContract.View, VisitsTypeFormAdapter.LastVisitsClick {
    VisitsTypeFormAdapter visitsTypeFormAdapter;
    RecyclerView allVisitsFormRecyclerView;
    TextView title;
    int unitId;
    String unitName;
    List<LastVisitsFormType> lastVisitsFormTypeList;
    List<String> lastVisitsFormTypeListString;
    AllVisitsFormTypePresenterImp allVisitsFormTypePresenterImp;
    TextView feedback;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.all_visits_forms_type, container, false);
        Bundle bundle = getArguments();
        unitId = bundle.getInt("unit_id");
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
        visitsTypeFormAdapter = new VisitsTypeFormAdapter(this);
        lastVisitsFormTypeList = new ArrayList<>();
        lastVisitsFormTypeListString = new ArrayList<>();
        allVisitsFormTypePresenterImp = new AllVisitsFormTypePresenterImp(getActivity(), this);
        allVisitsFormTypePresenterImp.getUserVisitsFormType(unitId);
    }

    private void initiViews( View view ) {
        title = view.findViewById(R.id.title);
        feedback = view.findViewById(R.id.feedback);
        feedback.setVisibility(View.GONE);
        allVisitsFormRecyclerView = view.findViewById(R.id.recycler_visits_details);
        title.setText("الزيارات السابقة لوحدة: " + unitName);
    }

    @Override
    public void showUserVisitsFormTypeList( List<LastVisitsFormType> lastVisitsFormTypeList ) {
        this.lastVisitsFormTypeList = lastVisitsFormTypeList;
        visitsTypeFormAdapter.setLastVisitsModelList(lastVisitsFormTypeList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        allVisitsFormRecyclerView.setLayoutManager(linearLayoutManager);
        allVisitsFormRecyclerView.setAdapter(visitsTypeFormAdapter);

    }

    @Override
    public void onLastVisitsClick( int pos ) {
        Bundle bundle = new Bundle();
        bundle.putInt("forms_id", lastVisitsFormTypeList.get(pos).getFormsId());
        bundle.putString("unit_name", unitName);
        VisitsQADetailsFragment visitsQADetailsFragment = new VisitsQADetailsFragment();
        visitsQADetailsFragment.setArguments(bundle);

        addFragment(visitsQADetailsFragment);
    }


    public void addFragment( Fragment fragment ) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
