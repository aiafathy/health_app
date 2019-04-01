package com.healthapp.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.healthapp.R;

public class FeedbackDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity activity;
    public Dialog dialog;
    LinearLayout verygood_feedback_layout, good_feedback_layout, bad_feedback_lLayout;
    TextView  question, veryGood, good, bad;

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

        verygood_feedback_layout.setOnClickListener(this);
        good_feedback_layout.setOnClickListener(this);
        bad_feedback_lLayout.setOnClickListener(this);

    }

    private void initiViews() {
        verygood_feedback_layout = findViewById(R.id.postive_feedback_layout);
        good_feedback_layout = findViewById(R.id.negative_feedback_layout);
        bad_feedback_lLayout = findViewById(R.id.ambiguity_feedback_layout);


        question = findViewById(R.id.question_dialog);

        veryGood = findViewById(R.id.very_good_dialog);
        good = findViewById(R.id.good_dialog);
        bad = findViewById(R.id.bad_dialog);

        question.setText("من فضلك قيم زياراتك للوحدة الصحية");
        veryGood.setText("ممتاز");
        good.setText("جيد");
        bad.setText("سيء");
    }

    @Override
    public void onClick( View v ) {
        switch (v.getId()) {
            case R.id.postive_feedback_layout:
                Toast.makeText(activity, "very good", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.negative_feedback_layout:
                Toast.makeText(activity, "good", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.ambiguity_feedback_layout:
                Toast.makeText(activity, "bad", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
        dismiss();
    }
}