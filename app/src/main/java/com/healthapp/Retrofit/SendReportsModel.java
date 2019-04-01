package com.healthapp.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendReportsModel {
    @SerializedName("data")
    @Expose
    private List<DataReports> data = null;
    @SerializedName("feed_back")
    @Expose
    private List<FeedBack> feedBack = null;

    public List<DataReports> getData() {
        return data;
    }

    public void setData(List<DataReports> data) {
        this.data = data;
    }

    public List<FeedBack> getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(List<FeedBack> feedBack) {
        this.feedBack = feedBack;
    }
}
