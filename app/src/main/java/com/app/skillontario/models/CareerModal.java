package com.app.skillontario.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CareerModal implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private Integer status;
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
    @SerializedName("jobDesc")
    @Expose
    private String jobDesc;
    @SerializedName("jobResp")
    @Expose
    private String jobResp;
    @SerializedName("jobArea")
    @Expose
    private String jobArea;
    @SerializedName("advice")
    @Expose
    private String advice;
    @SerializedName("eduReq")
    @Expose
    private String eduReq;
    @SerializedName("traReq")
    @Expose
    private String traReq;
    @SerializedName("expeReq")
    @Expose
    private String expeReq;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("jobSector")
    @Expose
    private String jobSector;
    @SerializedName("jobProfile")
    @Expose
    private String jobProfile;
    @SerializedName("jobEducatId")
    @Expose
    private String jobEducatId;
    @SerializedName("jobEducat")
    @Expose
    private String jobEducat;
    @SerializedName("bId")
    @Expose
    private String bId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getJobResp() {
        return jobResp;
    }

    public void setJobResp(String jobResp) {
        this.jobResp = jobResp;
    }

    public String getJobArea() {
        return jobArea;
    }

    public void setJobArea(String jobArea) {
        this.jobArea = jobArea;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getEduReq() {
        return eduReq;
    }

    public void setEduReq(String eduReq) {
        this.eduReq = eduReq;
    }

    public String getTraReq() {
        return traReq;
    }

    public void setTraReq(String traReq) {
        this.traReq = traReq;
    }

    public String getExpeReq() {
        return expeReq;
    }

    public void setExpeReq(String expeReq) {
        this.expeReq = expeReq;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    public String getJobEducatId() {
        return jobEducatId;
    }

    public void setJobEducatId(String jobEducatId) {
        this.jobEducatId = jobEducatId;
    }

    public String getJobEducat() {
        return jobEducat;
    }

    public void setJobEducat(String jobEducat) {
        this.jobEducat = jobEducat;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }
}
