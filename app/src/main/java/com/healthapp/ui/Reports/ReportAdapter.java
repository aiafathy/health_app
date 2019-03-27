package com.healthapp.ui.Reports;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.healthapp.R;

import java.util.List;


public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {
    List<String> questionsList;

    public void setQuestionsList( List<String> questionsList ) {
        this.questionsList = questionsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder( @NonNull ViewGroup viewGroup, int i ) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reports_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( @NonNull final MyViewHolder myViewHolder, int position ) {
        String question = questionsList.get(position);
        myViewHolder.question.setText(question);

        myViewHolder.noCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                myViewHolder.yesCheckBox.setBackgroundResource(R.drawable.un_mark);
                myViewHolder.yesCheckBox.setChecked(false);
                if (((CheckBox) view).isChecked()) {
                    myViewHolder.noCheckBox.setBackgroundResource(R.drawable.mark);
                    myViewHolder.notes.setVisibility(View.VISIBLE);
                } else {
                    myViewHolder.noCheckBox.setBackgroundResource(R.drawable.un_mark);
                    myViewHolder.notes.setVisibility(View.GONE);
                }
            }
        });

        myViewHolder.yesCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                myViewHolder.noCheckBox.setBackgroundResource(R.drawable.un_mark);
                myViewHolder.noCheckBox.setChecked(false);
                if (((CheckBox) view).isChecked()) {
                    myViewHolder.yesCheckBox.setBackgroundResource(R.drawable.mark);
                    myViewHolder.notes.setVisibility(View.GONE);
                } else
                    myViewHolder.yesCheckBox.setBackgroundResource(R.drawable.un_mark);

            }
        });


    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        CheckBox yesCheckBox, noCheckBox;
        EditText notes;

        public MyViewHolder( @NonNull View itemView ) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            yesCheckBox = itemView.findViewById(R.id.checkbox_yes);
            noCheckBox = itemView.findViewById(R.id.checkbox_no);
            notes = itemView.findViewById(R.id.notes);
        }
    }

    @Override
    public int getItemViewType( int position ) {
        return position;
    }
}

