package com.healthapp.ui.Reports.Reports1;

import com.healthapp.Retrofit.FormTypes;
import com.healthapp.Retrofit.NoDetails;

import java.util.List;

public interface IReportContract {

    interface View {

        void showFormsType( List<FormTypes> formTypesList );

        void showNoDetailsList( List<NoDetails> noDetailsList );
    }

    interface Presenter {
        void getFormsType();

        void getNoDetailsList();
    }
}
