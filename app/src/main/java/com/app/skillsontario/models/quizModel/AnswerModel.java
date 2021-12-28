package com.app.skillsontario.models.quizModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnswerModel {

    @SerializedName("opt")
    @Expose
    private String opt;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("CatIds")
    @Expose
    private List<String> catIds = null;

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getCatIds() {
        return catIds;
    }

    public void setCatIds(List<String> catIds) {
        this.catIds = catIds;
    }

}
