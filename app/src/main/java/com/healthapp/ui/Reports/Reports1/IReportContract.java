package com.healthapp.ui.Reports.Reports1;

import com.healthapp.Retrofit.FormTypes;
import com.healthapp.Retrofit.NoDetails;
import com.healthapp.Retrofit.Questions;

import java.util.List;

public interface IReportContract {

    interface View {

        void showFormsType( List<FormTypes> formTypesList );

        void showNoDetailsList( List<NoDetails> noDetailsList );

        void showAllQuestions( List<Questions> questionsList );
    }

    interface Presenter {
        void getFormsType();

        void getNoDetailsList();

        void getAllQuestions( int formId );
    }
}
