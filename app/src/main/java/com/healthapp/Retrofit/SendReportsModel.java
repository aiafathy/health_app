package com.healthapp.Retrofit;

import java.util.List;

public class SendReportsModel {

    private List<DataReports> data;
    private List<FeedBack> feed_back;

    private List<LocationModel> location;

    public SendReportsModel( List<DataReports> data, List<FeedBack> feedBack, List<LocationModel> location ) {
        this.data = data;
        this.feed_back = feedBack;
        this.location = location;
    }

    public List<DataReports> getData() {
        return data;
    }

    public void setData( List<DataReports> data ) {
        this.data = data;
    }

    public List<FeedBack> getFeedBack() {
        return feed_back;
    }

    public void setFeedBack( List<FeedBack> feedBack ) {
        this.feed_back = feedBack;
    }

    public List<LocationModel> getLocation() {
        return location;
    }

    public void setLocation( List<LocationModel> location ) {
        this.location = location;
    }
}
