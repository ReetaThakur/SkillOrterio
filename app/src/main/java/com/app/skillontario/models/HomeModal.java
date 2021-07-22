package com.app.skillontario.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HomeModal implements Serializable {

    @SerializedName("careerData")
    @Expose
    private List<CareerModal> careerData = null;
    @SerializedName("eventData")
    @Expose
    private List<EventsModal> eventData = null;
    @SerializedName("newsData")
    @Expose
    private List<NewsModal> newsData = null;

    public List<CareerModal> getCareerData() {
        return careerData;
    }

    public void setCareerData(List<CareerModal> careerData) {
        this.careerData = careerData;
    }

    public List<EventsModal> getEventData() {
        return eventData;
    }

    public void setEventData(List<EventsModal> eventData) {
        this.eventData = eventData;
    }

    public List<NewsModal> getNewsData() {
        return newsData;
    }

    public void setNewsData(List<NewsModal> newsData) {
        this.newsData = newsData;
    }
}
