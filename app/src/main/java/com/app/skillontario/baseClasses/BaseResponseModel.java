package com.app.skillontario.baseClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseResponseModel<T>  implements Serializable {

    @Expose
    @SerializedName("result")
    public T data;

    @Expose
    @SerializedName("error")
    public Error error;

    @Expose
    @SerializedName("status")
    public boolean status;

    @Expose
    @SerializedName("hasMore")
    public boolean hasMore;

    @Expose
    @SerializedName("message")
    public String message;

    @Expose
    @SerializedName("thumPath")
    public String thumbPath;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public String getMessage() {
        if (message == null||message.isEmpty()) {
            return "";
        } else {
            return message;
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public static class Error {
        @Expose
        @SerializedName("message")
        private String message;
        @Expose
        @SerializedName("errorCode")
        private int errorCode;

        public void setMessage(String message) {
            this.message = message;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getMessage() {
            return message;
        }


    }
}