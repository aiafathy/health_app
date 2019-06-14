package com.healthapp.ui.ReportType;

import com.healthapp.Retrofit.FormTypes;

import java.util.List;

public interface IReportTypeContract {

    interface View {
        void showReportsType( List<FormTypes> formTypesList );
    }

    interface Presenter {
        void getReportsType();
    }
}
