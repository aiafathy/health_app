package com.healthapp.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataReports {
    private String answer;
    private Integer questions_id;
    private Integer forms_id;
    private String answer_no_id;
    private Integer users_id;
    private Integer unit_id;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer( String answer ) {
        this.answer = answer;
    }

    public Integer getQuestions_id() {
        return questions_id;
    }

    public void setQuestions_id( Integer questions_id ) {
        this.questions_id = questions_id;
    }

    public Integer getForms_id() {
        return forms_id;
    }

    public void setForms_id( Integer forms_id ) {
        this.forms_id = forms_id;
    }

    public String getAnswer_no_id() {
        return answer_no_id;
    }

    public void setAnswer_no_id( String answer_no_id ) {
        this.answer_no_id = answer_no_id;
    }

    public Integer getUsers_id() {
        return users_id;
    }

    public void setUsers_id( Integer users_id ) {
        this.users_id = users_id;
    }

    public Integer getUnit_id() {
        return unit_id;
    }

    public void setUnit_id( Integer unit_id ) {
        this.unit_id = unit_id;
    }
}
