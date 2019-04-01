package com.healthapp.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedBack {
    @SerializedName("feed_back")
    @Expose
    private String feedBack;

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack( String feedBack ) {
        this.feedBack = feedBack;
    }
}
