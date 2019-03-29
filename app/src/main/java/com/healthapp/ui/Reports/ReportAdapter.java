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
import android.widget.EditText;
import android.widget.TextView;

import com.healthapp.R;
import com.reginald.editspinner.EditSpinner;

import java.util.List;


public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {
    List<String> questionsList;
    List<String> noAnswersListStrings;
    SpinnerClick spinnerClick;
    Context context;

    public ReportAdapter( List<String> noAnswersListStrings, SpinnerClick spinnerClick, Context context ) {
        this.noAnswersListStrings = noAnswersListStrings;
        this.spinnerClick = spinnerClick;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder( @NonNull ViewGroup viewGroup, int i ) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reports_item, viewGroup, false);
        return new MyViewHolder(view, spinnerClick);
    }

    @Override
    public void onBindViewHolder( @NonNull final MyViewHolder myViewHolder, int position ) {
        String question = questionsList.get(position);
        myViewHolder.question.setText(question);


        // handle edit spinner of no answers list
        ArrayAdapter<String> reportsAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, noAnswersListStrings);
        myViewHolder.noAnswerDetailsSpinner.setAdapter(reportsAdapter);

        // handle check box yes
        myViewHolder.yesCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                myViewHolder.noCheckBox.setBackgroundResource(R.drawable.un_mark);
                myViewHolder.noCheckBox.setChecked(false);
                if (((CheckBox) view).isChecked()) {
                    myViewHolder.yesCheckBox.setBackgroundResource(R.drawable.mark);
                    myViewHolder.noAnswerDetailsSpinner.setVisibility(View.GONE);
                    myViewHolder.noAnswerTitle.setVisibility(View.GONE);
                } else
                    myViewHolder.yesCheckBox.setBackgroundResource(R.drawable.un_mark);

            }
        });

        // handle check box No
        myViewHolder.noCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                myViewHolder.yesCheckBox.setBackgroundResource(R.drawable.un_mark);
                myViewHolder.yesCheckBox.setChecked(false);
                if (((CheckBox) view).isChecked()) {
                    myViewHolder.noCheckBox.setBackgroundResource(R.drawable.mark);
                    myViewHolder.noAnswerTitle.setVisibility(View.VISIBLE);
                    myViewHolder.noAnswerDetailsSpinner.setVisibility(View.VISIBLE);
                } else {
                    myViewHolder.noCheckBox.setBackgroundResource(R.drawable.un_mark);
                    myViewHolder.noAnswerDetailsSpinner.setVisibility(View.GONE);
                    myViewHolder.noAnswerTitle.setVisibility(View.GONE);
                }
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

    public void setQuestionsList( List<String> questionsList ) {
        this.questionsList = questionsList;
    }

    public interface SpinnerClick {
        void onSpinnerClick( int pos );
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemClickListener {
        TextView question, noAnswerTitle;
        CheckBox yesCheckBox, noCheckBox;
        EditSpinner noAnswerDetailsSpinner;
        SpinnerClick spinnerClick;

        public MyViewHolder( @NonNull View itemView, SpinnerClick spinnerClick ) {
            super(itemView);
            this.spinnerClick = spinnerClick;
            question = itemView.findViewById(R.id.question);
            yesCheckBox = itemView.findViewById(R.id.checkbox_yes);
            noCheckBox = itemView.findViewById(R.id.checkbox_no);
            noAnswerTitle = itemView.findViewById(R.id.text_no_answer);
            noAnswerDetailsSpinner = itemView.findViewById(R.id.spinner_no_answer_details);

            noAnswerDetailsSpinner.setOnItemClickListener(this);

        }

        @Override
        public void onItemClick( AdapterView<?> adapterView, View view, int i, long l ) {
            spinnerClick.onSpinnerClick(i);
        }
    }

}

