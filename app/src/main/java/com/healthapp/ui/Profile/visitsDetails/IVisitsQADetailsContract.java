package com.healthapp.ui.Profile.visitsDetails;

import com.healthapp.Retrofit.LastVisitsDetails;

import java.util.List;

public interface IVisitsQADetailsContract {

    interface View {
        void showUserVisitsDetailsList( List<LastVisitsDetails> lastVisitsList );
    }

    interface Presenter {
        void getUserVisitsDetails( int form_id );
    }
}
