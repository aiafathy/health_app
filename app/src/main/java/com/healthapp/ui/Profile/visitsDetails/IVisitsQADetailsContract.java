package com.healthapp.ui.Profile.visitsDetails;

import com.healthapp.Retrofit.LastVisitsDetails;
import com.healthapp.Retrofit.LastVisitsDetailsList;

import java.util.List;

public interface IVisitsQADetailsContract {

    interface View {
        void showUserVisitsDetailsList( List<LastVisitsDetailsList> lastVisitsList, String feedback );
    }

    interface Presenter {
        void getUserVisitsDetails( int form_id );
    }
}
