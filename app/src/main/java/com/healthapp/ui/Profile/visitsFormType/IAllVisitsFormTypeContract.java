package com.healthapp.ui.Profile.visitsFormType;

import com.healthapp.Retrofit.LastVisitsFormType;

import java.util.List;

public interface IAllVisitsFormTypeContract {

    interface View {
        void showUserVisitsFormTypeList( List<LastVisitsFormType> lastVisitsList );
    }

    interface Presenter {

        void getUserVisitsFormType( int unit_id );
    }
}
