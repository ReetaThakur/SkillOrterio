package com.app.skillsontario.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EducationModal implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("jobEducat")
    @Expose
    private String jobSector;
   boolean  IsSelector;

    public boolean isSelector() {
        return IsSelector;
    }

    public void setSelector(boolean selector) {
        IsSelector = selector;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobSector() {
        return jobSector;
    }

    public void setJobSector(String jobSector) {
        this.jobSector = jobSector;
    }

}
