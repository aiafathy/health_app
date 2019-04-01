package com.healthapp.ui.Login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.healthapp.Prefs.PreferencesHelper;
import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.R;
import com.healthapp.ui.HealthUnitDetails.HealthUnitDetails;
import com.reginald.editspinner.EditSpinner;

public class LoginActivity extends AppCompatActivity implements ILoginContract.View {

    Button login;
    EditSpinner emailEdit, passwordEdit;
    LoginPresenterImp loginPresenterImp;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initiViews();
        createInstance();
        setUpFCM();
        setListener();
    }

    private void createInstance() {
        loginPresenterImp = new LoginPresenterImp(this, LoginActivity.this);
    }

    private void setListener() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                String fcm_token = null;
                String emailUser = emailEdit.getText().toString();
                String passwordUser = passwordEdit.getText().toString();
                Log.i("fcm_token", PreferencesHelperImp.getInstance().getFcmToken());
                fcm_token = PreferencesHelperImp.getInstance().getFcmToken();
                if (!emailUser.isEmpty() && !passwordUser.isEmpty() && fcm_token != null) {

                    loginPresenterImp.login(emailUser, passwordUser, PreferencesHelperImp.getInstance().getFcmToken());
                } else {
                    Toast.makeText(LoginActivity.this, "من فضلك ادخل بريدك الالكتروني وكلمة السر", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void initiViews() {
        login = findViewById(R.id.button_login);
        emailEdit = findViewById(R.id.edit_email);
        passwordEdit = findViewById(R.id.edit_pass);
    }

    // for FCM

    private void setUpFCM() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive( Context context, Intent intent ) {
                if (intent.getAction().equals("fcm_intent")) {
                    Log.i("fcm_token", "token: " + intent.getExtras().getString("token"));
                    FirebaseMessaging.getInstance().subscribeToTopic("Global");
                }
            }
        };
    }

    @Override
    public void goToHealthUnitDetails( String token, int userId ) {
        PreferencesHelperImp.getInstance().setUserIsLogged(true);
        PreferencesHelperImp.getInstance().setUserToken(token);
        PreferencesHelperImp.getInstance().setUserId(userId);
        startActivity(new Intent(LoginActivity.this, HealthUnitDetails.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter("reg_id"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
