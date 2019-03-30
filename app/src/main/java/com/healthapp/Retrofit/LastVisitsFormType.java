package com.healthapp.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastVisitsFormType {

    @SerializedName("forms_id")
    @Expose
    private Integer formsId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("total")
    @Expose
    private Integer total;

    public Integer getFormsId() {
        return formsId;
    }

    public void setFormsId( Integer formsId ) {
        this.formsId = formsId;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal( Integer total ) {
        this.total = total;
    }

}
