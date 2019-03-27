package com.healthapp.ui.Profile;

import com.healthapp.Retrofit.LastVisits;

import java.util.List;

public interface IProfileContract {

    interface View {
        void showName( String name );

        void showUserVisitsList( List<LastVisits> lastVisitsList );
    }

    interface Presenter {
        void getUserData();

        void getUserVisits( int userId );
    }
}
