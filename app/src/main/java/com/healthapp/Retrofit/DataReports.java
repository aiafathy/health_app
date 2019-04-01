package com.healthapp.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataReports {
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("questions_id")
    @Expose
    private Integer questionsId;
    @SerializedName("forms_id")
    @Expose
    private Integer formsId;
    @SerializedName("answer_no_id")
    @Expose
    private Integer answerNoId;
    @SerializedName("users_id")
    @Expose
    private Integer usersId;
    @SerializedName("unit_id")
    @Expose
    private Integer unitId;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getQuestionsId() {
        return questionsId;
    }

    public void setQuestionsId(Integer questionsId) {
        this.questionsId = questionsId;
    }

    public Integer getFormsId() {
        return formsId;
    }

    public void setFormsId(Integer formsId) {
        this.formsId = formsId;
    }

    public Integer getAnswerNoId() {
        return answerNoId;
    }

    public void setAnswerNoId(Integer answerNoId) {
        this.answerNoId = answerNoId;
    }

    public Integer getUsersId() {
        return usersId;
    }

    public void setUsersId(Integer usersId) {
        this.usersId = usersId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

}
