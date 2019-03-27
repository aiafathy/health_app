package com.healthapp.ui.Profile;

public interface IProfileContract {

    interface View {
        void showName( String name );
    }

    interface Presenter {
        void getUserData();
    }
}
