package com.healthapp.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LastVisitsDetails {
    @SerializedName("data")
    @Expose
    private List<LastVisitsDetailsList> data = null;
    @SerializedName("feedBack")
    @Expose
    private String feedBack;

    public List<LastVisitsDetailsList> getData() {
        return data;
    }

    public void setData( List<LastVisitsDetailsList> data ) {
        this.data = data;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack( String feedBack ) {
        this.feedBack = feedBack;
    }

}
