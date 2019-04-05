package com.healthapp.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SettingsModel {
    @SerializedName("locale")
    @Expose
    private String locale;

    public String getLocale() {
        return locale;
    }

    public void setLocale( String locale ) {
        this.locale = locale;
    }
}
