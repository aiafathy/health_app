package com.healthapp.ui.Login;

public interface ILoginContract {

    interface View {
        void goToHealthUnitDetails(String token, int userId);

        void goToSignUp();
    }

    interface Presenter {
        void login(String email, String pass, String device_token);

        void goToSignUp();
    }
}
