package com.healthapp.ui.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.healthapp.Prefs.PreferencesHelper;
import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.R;
import com.healthapp.ui.HealthUnitDetails.HealthUnitDetails;
import com.reginald.editspinner.EditSpinner;

public class LoginActivity extends AppCompatActivity implements ILoginContract.View {

    Button login;
    EditSpinner emailEdit, passwordEdit;
    LoginPresenterImp loginPresenterImp;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initiViews();
        createInstance();
        setListener();
    }

    private void createInstance() {
        loginPresenterImp = new LoginPresenterImp(this, LoginActivity.this);
    }

    private void setListener() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {

                String emailUser = emailEdit.getText().toString();
                String passwordUser = passwordEdit.getText().toString();
                if (!emailUser.isEmpty() && !passwordUser.isEmpty()) {
                    //TODO fcm token from FCM
                    loginPresenterImp.login(emailUser, passwordUser);
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

    @Override
    public void goToHealthUnitDetails( String token, int userId ) {
        PreferencesHelperImp.getInstance().setUserIsLogged(true);
        PreferencesHelperImp.getInstance().setUserToken(token);
        PreferencesHelperImp.getInstance().setUserId(userId);
        startActivity(new Intent(LoginActivity.this, HealthUnitDetails.class));
        finish();
    }


}
