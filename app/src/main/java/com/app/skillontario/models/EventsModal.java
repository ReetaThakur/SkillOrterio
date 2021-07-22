package com.app.skillontario.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventsModal implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("evtTitle")
    @Expose
    private String evtTitle;
    @SerializedName("evtDesc")
    @Expose
    private String evtDesc;
    @SerializedName("evtCategory")
    @Expose
    private String evtCategory;
    @SerializedName("evtVenue")
    @Expose
    private String evtVenue;
    @SerializedName("evtImage")
    @Expose
    private String evtImage;
    @SerializedName("evtDate")
    @Expose
    private String evtDate;
    @SerializedName("evtURL")
    @Expose
    private String evtURL;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @SerializedName("evtEndDate")
    @Expose
    private String evtEndDate;

    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getEvtEndDate() {
        return evtEndDate;
    }

    public void setEvtEndDate(String evtEndDate) {
        this.evtEndDate = evtEndDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEvtTitle() {
        return evtTitle;
    }

    public void setEvtTitle(String evtTitle) {
        this.evtTitle = evtTitle;
    }

    public String getEvtDesc() {
        return evtDesc;
    }

    public void setEvtDesc(String evtDesc) {
        this.evtDesc = evtDesc;
    }

    public String getEvtCategory() {
        return evtCategory;
    }

    public void setEvtCategory(String evtCategory) {
        this.evtCategory = evtCategory;
    }

    public String getEvtVenue() {
        return evtVenue;
    }

    public void setEvtVenue(String evtVenue) {
        this.evtVenue = evtVenue;
    }

    public String getEvtImage() {
        return evtImage;
    }

    public void setEvtImage(String evtImage) {
        this.evtImage = evtImage;
    }

    public String getEvtDate() {
        return evtDate;
    }

    public void setEvtDate(String evtDate) {
        this.evtDate = evtDate;
    }

    public String getEvtURL() {
        return evtURL;
    }

    public void setEvtURL(String evtURL) {
        this.evtURL = evtURL;
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

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
