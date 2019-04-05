package com.healthapp.ui.Reports;

public class AnswersHashMAp {
    String answer;
    String noAnswerId;

    public AnswersHashMAp( String answer, String noAnswerId ) {
        this.answer = answer;
        this.noAnswerId = noAnswerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer( String answer ) {
        this.answer = answer;
    }


    public String getNoAnswerId() {
        return noAnswerId;
    }

    public void setNoAnswerId( String noAnswerId ) {
        this.noAnswerId = noAnswerId;
    }
}
