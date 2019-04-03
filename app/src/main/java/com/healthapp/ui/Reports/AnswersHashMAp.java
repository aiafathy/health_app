package com.healthapp.ui.Reports;

public class AnswersHashMAp {
    String answer;
    Integer noAnswerId;

    public AnswersHashMAp( String answer, Integer noAnswerId ) {
        this.answer = answer;
        this.noAnswerId = noAnswerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer( String answer ) {
        this.answer = answer;
    }

    public Integer getNoAnswerId() {
        return noAnswerId;
    }

    public void setNoAnswerId( Integer noAnswerId ) {
        this.noAnswerId = noAnswerId;
    }
}
