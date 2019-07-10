package com.healthapp.ui.SignUp;

public interface SignUpContract {

    interface SignUpView {

        void backToLogin();


    }

    interface SignUpPresenter {
        void signUp(String name, String email, String phone, String password);

        void backToLog();

    }
}
