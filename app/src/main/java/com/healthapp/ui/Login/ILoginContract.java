package com.healthapp.ui.Login;

public interface ILoginContract {

    interface View {
        void goToHealthUnitDetails( String token ,int userId);
    }

    interface Presenter {
        void login( String email, String pass );
    }
}
