package com.healthapp.ui.Profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.healthapp.R;


public class ProfileActivity extends AppCompatActivity implements IProfileContract.View {
    TextView nameText;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initiViews();
    }

    private void initiViews() {
        nameText = findViewById(R.id.name);
    }

    @Override
    public void showName( String name ) {
        nameText.setText(name);
    }
}
