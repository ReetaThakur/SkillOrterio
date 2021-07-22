package com.app.skillontario.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InterestModal implements Serializable {

    @SerializedName("sector")
    @Expose
    private List<SectorModal> sector;
    @SerializedName("education")
    @Expose
    private List<EducationModal> education;
    @SerializedName("redSeal")
    @Expose
    private List<RedSealModal> redSeal;

    public List<SectorModal> getSector() {
        return sector;
    }

    public void setSector(List<SectorModal> sector) {
        this.sector = sector;
    }

    public List<EducationModal> getEducation() {
        return education;
    }

    public void setEducation(List<EducationModal> education) {
        this.education = education;
    }

    public List<RedSealModal> getRedSeal() {
        return redSeal;
    }

    public void setRedSeal(List<RedSealModal> redSeal) {
        this.redSeal = redSeal;
    }
}
