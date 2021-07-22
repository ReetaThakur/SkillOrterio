package com.app.skillontario.models.quizModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultModel {

    @SerializedName("answer")
    @Expose
    private List<AnswerModel> answer = null;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("quesType")
    @Expose
    private Integer quesType;
    @SerializedName("catId")
    @Expose
    private String catId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("answerType")
    @Expose
    private Integer answerType;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<AnswerModel> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerModel> answer) {
        this.answer = answer;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQuesType() {
        return quesType;
    }

    public void setQuesType(Integer quesType) {
        this.quesType = quesType;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getAnswerType() {
        return answerType;
    }

    public void setAnswerType(Integer answerType) {
        this.answerType = answerType;
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
