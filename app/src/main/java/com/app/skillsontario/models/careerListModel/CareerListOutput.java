package com.app.skillsontario.models.careerListModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CareerListOutput {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private List<CareerListDetails> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("hasMore")
    @Expose
    private Boolean hasMore;
    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<CareerListDetails> getResult() {
        return result;
    }

    public void setResult(List<CareerListDetails> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public static class Error {
        @Expose
        @SerializedName("message")
        private String message;
        @Expose
        @SerializedName("errorCode")
        private int errorCode;

        public String getMessage() {
            return message;
        }

        public int getErrorCode() {
            return errorCode;
        }

    }
}
