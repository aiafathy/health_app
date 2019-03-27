package com.healthapp.ui.Profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.R;
import com.healthapp.Retrofit.LastVisits;

import java.util.ArrayList;
import java.util.List;


public class ProfileActivity extends AppCompatActivity implements IProfileContract.View {
    TextView nameText;
    ProfilePresenterImp profilePresenterImp;
    List<LastVisits> lastVisitsList;
    RecyclerView allVisitsRecyclerView;
    AllVisitsAdapter allVisitsAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initiViews();
        createInstance();
    }

    private void createInstance() {
        lastVisitsList = new ArrayList<>();
        allVisitsAdapter = new AllVisitsAdapter();

        profilePresenterImp = new ProfilePresenterImp(this, ProfileActivity.this);
        profilePresenterImp.getUserData();
        profilePresenterImp.getUserVisits(PreferencesHelperImp.getInstance().getUserId());
    }

    private void initiViews() {
        nameText = findViewById(R.id.name);
        allVisitsRecyclerView = findViewById(R.id.recycler_previous_visit);
    }

    @Override
    public void showName( String name ) {
        nameText.setText(name);
    }

    @Override
    public void showUserVisitsList( List<LastVisits> lastVisitsList ) {
        this.lastVisitsList = lastVisitsList;
        allVisitsAdapter.setLastVisitsModelList(lastVisitsList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.VERTICAL, false);
        allVisitsRecyclerView.setLayoutManager(linearLayoutManager);
        allVisitsRecyclerView.setAdapter(allVisitsAdapter);
    }
}