package com.healthapp.ui.HealthUnitDetails;

import com.healthapp.Retrofit.HealthUnit;

import java.util.List;

public interface IHealthUnitDetailsContract {

    interface View {

        void showTownList( List<HealthUnit> townList );

        void showManagementList( List<HealthUnit> managementList );

        void showUnitsList( List<HealthUnit> unitList );
    }

    interface Presenter {

        void getTownList();

        void getManagementList( int id );

        void getUnitsList( int id );
    }
}
