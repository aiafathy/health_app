package com.healthapp.ui.Reports;

import com.healthapp.Retrofit.DataReports;
import com.healthapp.Retrofit.FeedBack;
import com.healthapp.Retrofit.FormTypes;
import com.healthapp.Retrofit.LocationModel;
import com.healthapp.Retrofit.NoDetails;
import com.healthapp.Retrofit.Questions;

import java.util.List;

public interface IReportContract {

    interface View {

        void showFormsType( List<FormTypes> formTypesList );

        void showNoDetailsList( List<NoDetails> noDetailsList );

        void showAllQuestions( List<Questions> questionsList );

        void showSuccessfullySendReport( int code );
    }

    interface Presenter {
        void getFormsType();

        void getNoDetailsList();

        void getAllQuestions( int formId );

        void sendReport( List<DataReports> questionsList, List<LocationModel> locationModelList, List<FeedBack> feedBackList );
    }
}
