package com.healthapp.ui.SignUp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.healthapp.R;
import com.reginald.editspinner.EditSpinner;

public class SignUp extends AppCompatActivity implements View.OnClickListener,
        SignUpContract.SignUpView {
    EditSpinner nameEdit, emailEdit, phoneEdit, passEdit;
    Button signBt;
    SignUpContract.SignUpPresenter upPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
    }

    void initView() {
        nameEdit = findViewById(R.id.sign_name);
        emailEdit = findViewById(R.id.sign_email);
        phoneEdit = findViewById(R.id.sign_mobile);
        passEdit = findViewById(R.id.sign_pass);
        signBt = findViewById(R.id.button_signup);
        upPresenter = new SignUpPresenterImpl(this, this);
        signBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button_signup:
                String name = nameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String phone = phoneEdit.getText().toString();
                String pass = passEdit.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !pass.isEmpty()) {
                    upPresenter.signUp(name, email, phone, pass);
                } else {
                    Toast.makeText(this, "من فضلك إملأ جميع البيانات", Toast.LENGTH_SHORT).show();
                }


        }
    }

    /**
     * When he finish sign up return back to login page to log in.
     * because he cannot login until the admin confirm his data and accept
     */
    @Override
    public void backToLogin() {
        finish();

    }

}
