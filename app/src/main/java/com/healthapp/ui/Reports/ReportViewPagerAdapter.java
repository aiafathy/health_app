package com.healthapp.ui.Reports;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.healthapp.Prefs.PreferencesHelperImp;
import com.healthapp.R;
import com.healthapp.Retrofit.DataReports;
import com.healthapp.Retrofit.NoDetails;
import com.healthapp.Retrofit.Questions;
import com.reginald.editspinner.EditSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ReportViewPagerAdapter extends PagerAdapter {
    List<Questions> questionsList;
    List<NoDetails> noAnswersList;
    List<String> noAnswersListStrings;
    Context context;
    HashMap<Integer, AnswersHashMAp> answerHashMap = new HashMap<>();


    public ReportViewPagerAdapter( Context context ) {
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem( @NonNull ViewGroup container, int position ) {


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.reports_item, container, false);

        TextView question = itemView.findViewById(R.id.question);
        CheckBox yesCheckBox = itemView.findViewById(R.id.checkbox_yes);
        CheckBox noCheckBox = itemView.findViewById(R.id.checkbox_no);
        TextView noAnswerTitle = itemView.findViewById(R.id.text_no_answer);
        Spinner noAnswerDetailsSpinner = itemView.findViewById(R.id.spinner_no_answer_details);
        RelativeLayout spinnerViewGroup = itemView.findViewById(R.id.spinner_relativeType);

        Questions questions = questionsList.get(position);
        question.setText(questions.getName());

        handleQuestions(position, yesCheckBox, noCheckBox, spinnerViewGroup, noAnswerDetailsSpinner, noAnswerTitle);

        // Add viewpager_item.xml to ViewPager
        container.addView(itemView);

        return itemView;
    }

    private void handleQuestions( final int position, final CheckBox yesCheckBox, final CheckBox noCheckBox, final RelativeLayout spinnerViewGroup, Spinner noAnswerDetailsSpinner, final TextView noAnswerTitle ) {

        if (noAnswersList != null) {
            // handle edit spinner of no answers list
            noAnswersListStrings = new ArrayList<>();
            for (int i = 0; i < noAnswersList.size(); i++) {
                noAnswersListStrings.add(noAnswersList.get(i).getAnswer());
            }
        }

        ArrayAdapter<String> reportsAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, noAnswersListStrings);
        reportsAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);

        noAnswerDetailsSpinner.setAdapter(reportsAdapter);

        // handle check box yes
        yesCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                noCheckBox.setBackgroundResource(R.drawable.un_mark);
                noCheckBox.setChecked(false);
                if (((CheckBox) view).isChecked()) {
                    answerHashMap.put(questionsList.get(position).getId(), new AnswersHashMAp("yes", "null"));
                    yesCheckBox.setBackgroundResource(R.drawable.mark);
                    spinnerViewGroup.setVisibility(View.GONE);
                    noAnswerTitle.setVisibility(View.GONE);
                } else {
                    answerHashMap.remove(questionsList.get(position).getId());
                    yesCheckBox.setBackgroundResource(R.drawable.un_mark);
                }

            }
        });

        // handle check box No
        noCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                yesCheckBox.setBackgroundResource(R.drawable.un_mark);
                yesCheckBox.setChecked(false);
                if (((CheckBox) view).isChecked()) {
                    noCheckBox.setBackgroundResource(R.drawable.mark);
                    noAnswerTitle.setVisibility(View.VISIBLE);
                    spinnerViewGroup.setVisibility(View.VISIBLE);
                } else {
                    noCheckBox.setBackgroundResource(R.drawable.un_mark);
                    spinnerViewGroup.setVisibility(View.GONE);
                    noAnswerTitle.setVisibility(View.GONE);
                }
            }
        });


        noAnswerDetailsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int i, long l ) {
                answerHashMap.put(questionsList.get(position).getId(), new AnswersHashMAp("no", String.valueOf(noAnswersList.get(i).getId())));
            }

            @Override
            public void onNothingSelected( AdapterView<?> adapterView ) {
                if (noCheckBox.isChecked()) {
                    Toast.makeText(context, "من فضلك وضح السبب", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void destroyItem( ViewGroup container, int position, Object object ) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((View) object);
    }

    public void setQuestionsList( List<Questions> questionsList ) {
        this.questionsList = questionsList;
    }

    public void setNoAnswersList( List<NoDetails> noAnswersList ) {
        this.noAnswersList = noAnswersList;
    }

    public boolean isAllQuestionsIsAnswered() {/*
        for (int i = 0; i < answerHashMap.size(); i++) {
            String answer = answerHashMap.get(i).answer;*/
        Log.i("answersize", "=" + answerHashMap.size() + ", " + questionsList.size());

        if (answerHashMap.size() == questionsList.size())
            return true;
        else
            return false;
    }

    public List<DataReports> getAnswers() {
        List<DataReports> dataReportsList = new ArrayList<>();
        for (int i = 0; i < questionsList.size(); i++) {
            DataReports dataReports = new DataReports();
            dataReports.setQuestions_id(questionsList.get(i).getId());
            dataReports.setForms_id(questionsList.get(i).getFormsId());
            dataReports.setUsers_id(PreferencesHelperImp.getInstance().getUserId());
            dataReports.setUnit_id(PreferencesHelperImp.getInstance().getUnitId());
            dataReports.setAnswer(answerHashMap.get(questionsList.get(i).getId()).getAnswer());
            dataReports.setAnswer_no_id(answerHashMap.get(questionsList.get(i).getId()).getNoAnswerId());

            dataReportsList.add(dataReports);
        }
        return dataReportsList;
    }


    @Override
    public int getCount() {
        return questionsList.size();
    }

    @Override
    public boolean isViewFromObject( @NonNull View view, @NonNull Object object ) {
        return view == object;
    }

}

