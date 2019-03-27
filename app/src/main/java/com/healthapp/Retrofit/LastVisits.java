package com.healthapp.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastVisits {

    @SerializedName("unit_id")
    @Expose
    private Integer unitId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("total")
    @Expose
    private Integer total;

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId( Integer unitId ) {
        this.unitId = unitId;
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
