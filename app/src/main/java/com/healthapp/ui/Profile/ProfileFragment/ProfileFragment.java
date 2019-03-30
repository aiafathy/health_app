package com.healthapp.ui.Profile.ProfileFragment;

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
import android.widget.Button;
import android.widget.TextView;

import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.R;
import com.healthapp.Retrofit.LastVisits;
import com.healthapp.Retrofit.LastVisitsDetails;
import com.healthapp.ui.Profile.visitsFormType.AllVisitsFormTypeFragment;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements IProfileContract.View, AllVisitsAdapter.LastVisitsClick {
    TextView nameText;
    ProfilePresenterImp profilePresenterImp;
    List<LastVisits> lastVisitsList;
    RecyclerView allVisitsRecyclerView;
    AllVisitsAdapter allVisitsAdapter;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated(view, savedInstanceState);

        initiViews(view);
        createInstance();
    }

    private void createInstance() {
        lastVisitsList = new ArrayList<>();
        allVisitsAdapter = new AllVisitsAdapter(this);

        profilePresenterImp = new ProfilePresenterImp(this, getActivity());
        profilePresenterImp.getUserData();
        profilePresenterImp.getUserVisits(PreferencesHelperImp.getInstance().getUserId());

    }

    private void initiViews( View view ) {
        nameText = view.findViewById(R.id.name);
        allVisitsRecyclerView = view.findViewById(R.id.recycler_previous_visit);
    }

    @Override
    public void showName( String name ) {
        nameText.setText(name);
    }

    @Override
    public void showUserVisitsList( List<LastVisits> lastVisitsList ) {
        this.lastVisitsList = lastVisitsList;
        allVisitsAdapter.setLastVisitsModelList(lastVisitsList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        allVisitsRecyclerView.setLayoutManager(linearLayoutManager);
        allVisitsRecyclerView.setAdapter(allVisitsAdapter);
    }

    @Override
    public void onLastVisitsClick( int pos ) {
        Bundle bundle = new Bundle();
        bundle.putInt("unit_id", lastVisitsList.get(pos).getUnitId());
        bundle.putString("unit_name", lastVisitsList.get(pos).getName());
        AllVisitsFormTypeFragment allVisitsFormTypeFragment = new AllVisitsFormTypeFragment();
        allVisitsFormTypeFragment.setArguments(bundle);

        addFragment(allVisitsFormTypeFragment);
    }

    public void addFragment( Fragment fragment ) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
