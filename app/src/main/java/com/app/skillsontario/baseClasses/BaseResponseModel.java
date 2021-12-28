package com.app.skillsontario.baseClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseResponseModel<T> implements Serializable {


    @Expose
    @SerializedName("status")
    private boolean status;

    @Expose
    @SerializedName("error")
    public T error;

    @Expose
    @SerializedName("errorCode")
    public int errorCode;

    @Expose
    @SerializedName("message")
    public String message;

    @Expose
    @SerializedName("result")
    public T output;

    @Expose
    @SerializedName("hasMore")
    public boolean hasMore;

    @Expose
    @SerializedName("totalCount")
    public int totalCount;

    public boolean isStatus() {
        return status;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }


    public T getError() {
        return error;
    }

    public void setError(T error) {
        this.error = error;
    }

    public boolean getStatus() {
        return status;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public T getOutput() {
        return output;
    }

    public void setOutput(T output) {
        this.output = output;
    }
}
