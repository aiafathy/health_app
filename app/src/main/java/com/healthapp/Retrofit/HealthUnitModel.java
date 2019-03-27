package com.healthapp.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HealthUnitModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("response")
    @Expose
    private List<HealthUnit> response = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus( Boolean status ) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode( Integer code ) {
        this.code = code;
    }

    public List<HealthUnit> getResponse() {
        return response;
    }

    public void setResponse( List<HealthUnit> response ) {
        this.response = response;
    }
}

