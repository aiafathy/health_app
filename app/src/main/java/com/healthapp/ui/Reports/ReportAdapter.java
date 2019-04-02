package com.healthapp.ui.Reports;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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


public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {
    List<Questions> questionsList;
    List<NoDetails> noAnswersList;
    List<String> noAnswersListStrings;
    Context context;
    HashMap<Integer, String> answerHashMap;
    HashMap<Integer, Integer> answerNoIdHashMap;
    int idNoAnswer;

    public ReportAdapter( List<NoDetails> noAnswersListStrings, Context context ) {
        this.noAnswersList = noAnswersListStrings;
        this.context = context;
        answerHashMap = new HashMap<>();
        answerNoIdHashMap = new HashMap<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder( @NonNull ViewGroup viewGroup, int i ) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reports_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NonNull final MyViewHolder myViewHolder, final int position ) {
        final String question = questionsList.get(position).getName();
        myViewHolder.question.setText(question);

        // handle edit spinner of no answers list
        noAnswersListStrings = new ArrayList<>();
        for (int i = 0; i < noAnswersList.size(); i++) {
            noAnswersListStrings.add(noAnswersList.get(i).getAnswer());
        }

        ArrayAdapter<String> reportsAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, noAnswersListStrings);
        myViewHolder.noAnswerDetailsSpinner.setAdapter(reportsAdapter);

        // handle check box yes
        myViewHolder.yesCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                myViewHolder.noCheckBox.setBackgroundResource(R.drawable.un_mark);
                myViewHolder.noCheckBox.setChecked(false);
                if (((CheckBox) view).isChecked()) {
                    answerHashMap.put(questionsList.get(position).getId(), "yes");
                    myViewHolder.yesCheckBox.setBackgroundResource(R.drawable.mark);
                    myViewHolder.noAnswerDetailsSpinner.setVisibility(View.GONE);
                    myViewHolder.noAnswerTitle.setVisibility(View.GONE);
                    myViewHolder.noAnswerDetailsSpinner.clearListSelection();
                } else {
                    answerHashMap.remove(questionsList.get(position).getId());
                    myViewHolder.yesCheckBox.setBackgroundResource(R.drawable.un_mark);
                }
            }
        });

        // handle check box No
        myViewHolder.noCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                myViewHolder.yesCheckBox.setBackgroundResource(R.drawable.un_mark);
                myViewHolder.yesCheckBox.setChecked(false);
                if (((CheckBox) view).isChecked()) {
                    answerHashMap.put(questionsList.get(position).getId(), "no");
                    myViewHolder.noCheckBox.setBackgroundResource(R.drawable.mark);
                    myViewHolder.noAnswerTitle.setVisibility(View.VISIBLE);
                    myViewHolder.noAnswerDetailsSpinner.setVisibility(View.VISIBLE);
                    myViewHolder.noAnswerDetailsSpinner.setSelection(0);
                } else {
                    answerHashMap.remove(questionsList.get(position).getId());
                    myViewHolder.noCheckBox.setBackgroundResource(R.drawable.un_mark);
                    myViewHolder.noAnswerDetailsSpinner.setVisibility(View.GONE);
                    myViewHolder.noAnswerDetailsSpinner.clearListSelection();
                    myViewHolder.noAnswerTitle.setVisibility(View.GONE);
                }
            }
        });

        myViewHolder.noAnswerDetailsSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View view, int i, long l ) {

                answerNoIdHashMap.put(questionsList.get(position).getId(), noAnswersList.get(i).getId());

            }
        });

    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    @Override
    public int getItemViewType( int position ) {
        return position;
    }

    public void setQuestionsList( List<Questions> questionsList ) {
        this.questionsList = questionsList;
    }

    public List<DataReports> getAnswers() {
        List<DataReports> dataReportsList = new ArrayList<>();
        if (answerHashMap.size() == questionsList.size()) {
            for (int i = 0; i < questionsList.size(); i++) {
                DataReports dataReports = new DataReports();
                dataReports.setQuestions_id(questionsList.get(i).getId());
                dataReports.setForms_id(questionsList.get(i).getFormsId());
                dataReports.setUsers_id(PreferencesHelperImp.getInstance().getUserId());
                dataReports.setUnit_id(PreferencesHelperImp.getInstance().getUnitId());
                dataReports.setAnswer(answerHashMap.get(questionsList.get(i).getId()));
                dataReports.setAnswer_no_id(answerNoIdHashMap.get(i));

                dataReportsList.add(dataReports);
            }
        } else
            Toast.makeText(context, "من فضلك أجب علي كل الاسئلة", Toast.LENGTH_SHORT).show();

        return dataReportsList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView question, noAnswerTitle;
        CheckBox yesCheckBox, noCheckBox;
        EditSpinner noAnswerDetailsSpinner;

        public MyViewHolder( @NonNull View itemView ) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            yesCheckBox = itemView.findViewById(R.id.checkbox_yes);
            noCheckBox = itemView.findViewById(R.id.checkbox_no);
            noAnswerTitle = itemView.findViewById(R.id.text_no_answer);
            noAnswerDetailsSpinner = itemView.findViewById(R.id.spinner_no_answer_details);

        }
    }

}

