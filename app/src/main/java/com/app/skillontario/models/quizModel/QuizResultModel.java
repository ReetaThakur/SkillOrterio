package com.app.skillontario.models.quizModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizResultModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("jobSectorId")
    @Expose
    private String jobSectorId;
    @SerializedName("jobProfileId")
    @Expose
    private String jobProfileId;
    @SerializedName("fee")
    @Expose
    private String fee;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("tagged")
    @Expose
    private String tagged;
    @SerializedName("jobSector")
    @Expose
    private String jobSector;
    @SerializedName("jobProfile")
    @Expose
    private String jobProfile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobSectorId() {
        return jobSectorId;
    }

    public void setJobSectorId(String jobSectorId) {
        this.jobSectorId = jobSectorId;
    }

    public String getJobProfileId() {
        return jobProfileId;
    }

    public void setJobProfileId(String jobProfileId) {
        this.jobProfileId = jobProfileId;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTagged() {
        return tagged;
    }

    public void setTagged(String tagged) {
        this.tagged = tagged;
    }

    public String getJobSector() {
        return jobSector;
    }

    public void setJobSector(String jobSector) {
        this.jobSector = jobSector;
    }

    public String getJobProfile() {
        return jobProfile;
    }

    public void setJobProfile(String jobProfile) {
        this.jobProfile = jobProfile;
    }

}
