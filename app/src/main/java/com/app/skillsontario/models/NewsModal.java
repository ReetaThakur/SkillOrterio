package com.app.skillsontario.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsModal implements Serializable, Parcelable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("newsTitle")
    @Expose
    private String newsTitle;
    @SerializedName("newsDesc")
    @Expose
    private String newsDesc;
    @SerializedName("newsImage")
    @Expose
    private String newsImage;
    @SerializedName("newsDate")
    @Expose
    private String newsDate;
    @SerializedName("newsSouce")
    @Expose
    private String newsSouce;
    @SerializedName("newsUrl")
    @Expose
    private String newsUrl;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public NewsModal(Parcel in) {
        id = in.readString();
        lang = in.readString();
        if (in.readByte() == 0) {
            status = null;
        } else {
            status = in.readInt();
        }
        newsTitle = in.readString();
        newsDesc = in.readString();
        newsImage = in.readString();
        newsDate = in.readString();
        newsSouce = in.readString();
        newsUrl = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        if (in.readByte() == 0) {
            v = null;
        } else {
            v = in.readInt();
        }
    }

    public static final Creator<NewsModal> CREATOR = new Creator<NewsModal>() {
        @Override
        public NewsModal createFromParcel(Parcel in) {
            return new NewsModal(in);
        }

        @Override
        public NewsModal[] newArray(int size) {
            return new NewsModal[size];
        }
    };

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

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsSouce() {
        return newsSouce;
    }

    public void setNewsSouce(String newsSouce) {
        this.newsSouce = newsSouce;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(lang);
        if (status == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status);
        }
        dest.writeString(newsTitle);
        dest.writeString(newsDesc);
        dest.writeString(newsImage);
        dest.writeString(newsDate);
        dest.writeString(newsSouce);
        dest.writeString(newsUrl);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        if (v == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(v);
        }
    }
}
