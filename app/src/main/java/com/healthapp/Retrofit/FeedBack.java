package com.healthapp.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedBack {

    private String feed_back;

    public String getFeedBack() {
        return feed_back;
    }

    public void setFeedBack( String feedBack ) {
        this.feed_back = feedBack;
    }
}
