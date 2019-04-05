package com.healthapp.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.healthapp.R;
import com.reginald.editspinner.EditSpinner;

public class FeedbackDialog extends Dialog {

    public Activity activity;
    public Dialog dialog;
    TextView question;
    Button ok;
    EditSpinner feedbackEditSpinner;

    private String feedback;

    public FeedbackDialog( Activity activity ) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_feedback);

        initiViews();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if (!feedbackEditSpinner.getText().toString().isEmpty()) {
                    feedback = feedbackEditSpinner.getText().toString();
                    dismiss();
                } else {
                    Toast.makeText(activity, "من فضلك اكتب رايك عن الزيارة", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initiViews() {

        feedbackEditSpinner = findViewById(R.id.edit_feedback);
        question = findViewById(R.id.question_dialog);
        ok = findViewById(R.id.okay);

        question.setText("من فضلك قيم زياراتك للوحدة الصحية");
    }


    public String getFeedback() {
        return feedback;
    }
}