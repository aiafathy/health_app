package com.healthapp.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastVisitsDetailsList {
    @SerializedName("questions_id")
    @Expose
    private Integer questionsId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("total")
    @Expose
    private Integer total;

    public Integer getQuestionsId() {
        return questionsId;
    }

    public void setQuestionsId( Integer questionsId ) {
        this.questionsId = questionsId;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer( String answer ) {
        this.answer = answer;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal( Integer total ) {
        this.total = total;
    }
}
