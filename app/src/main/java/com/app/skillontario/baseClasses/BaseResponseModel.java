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
    private int status;

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
    @SerializedName("output")
    public T output;


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

    public int getStatus() {
        return status;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public void setStatus(int status) {
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
