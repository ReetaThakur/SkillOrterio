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


    }
}