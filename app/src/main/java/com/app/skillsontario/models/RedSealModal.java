package com.app.skillsontario.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RedSealModal implements Serializable {

    @SerializedName("Yes")
    @Expose
    private Integer yes;
    @SerializedName("No")
    @Expose
    private Integer no;

    boolean  IsSelector;

    public boolean isSelector() {
        return IsSelector;
    }

    public void setSelector(boolean selector) {
        IsSelector = selector;
    }

    public Integer getYes() {
        return yes;
    }

    public void setYes(Integer yes) {
        this.yes = yes;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }
}
