package com.app.skillontario.baseClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseResponseModel<T> implements Serializable {

    @Expose
    @SerializedName("error")
    private Error error;

    @Expose
    @SerializedName("status")
    private boolean status;

    @Expose
    @SerializedName("message")
    public String message;

    @Expose
    @SerializedName("isSinValid")
    public Boolean isSinValid;

    @Expose
    @SerializedName("isSinValidInteger")
    public Boolean isSinValidInteger;

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


    public Error getError() {
        return error;
    }

    public boolean getStatus() {
        return status;
    }

    public void setError(Error error) {
        this.error = error;
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

    public Boolean getSinValid() {
        return isSinValid;
    }

    public void setSinValid(Boolean sinValid) {
        isSinValid = sinValid;
    }

    public Boolean getSinValidInteger() {
        return isSinValidInteger;
    }

    public void setSinValidInteger(Boolean sinValidInteger) {
        isSinValidInteger = sinValidInteger;
    }

    public T getOutput() {
        return output;
    }

    public void setOutput(T output) {
        this.output = output;
    }
}
