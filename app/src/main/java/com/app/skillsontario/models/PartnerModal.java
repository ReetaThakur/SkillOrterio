package com.app.skillsontario.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PartnerModal implements Serializable {
    @SerializedName("platinum")
    @Expose
    ArrayList<PlatinumModal> platinum;

    @SerializedName("premium")
    @Expose
    ArrayList<PlatinumModal> premium;

    @SerializedName("government")
    @Expose
    ArrayList<PlatinumModal> government;

    @SerializedName("silver")
    @Expose
    ArrayList<PlatinumModal> silver;

    @SerializedName("gold")
    @Expose
    ArrayList<PlatinumModal> gold;

    @SerializedName("bronze")
    @Expose
    ArrayList<PlatinumModal> bronze;
    @SerializedName("friends")
    @Expose
    ArrayList<PlatinumModal> friends;

    public ArrayList<PlatinumModal> getPlatinum() {
        return platinum;
    }

    public void setPlatinum(ArrayList<PlatinumModal> platinum) {
        this.platinum = platinum;
    }

    public ArrayList<PlatinumModal> getPremium() {
        return premium;
    }

    public void setPremium(ArrayList<PlatinumModal> premium) {
        this.premium = premium;
    }

    public ArrayList<PlatinumModal> getGovernment() {
        return government;
    }

    public void setGovernment(ArrayList<PlatinumModal> government) {
        this.government = government;
    }

    public ArrayList<PlatinumModal> getSilver() {
        return silver;
    }

    public void setSilver(ArrayList<PlatinumModal> silver) {
        this.silver = silver;
    }

    public ArrayList<PlatinumModal> getGold() {
        return gold;
    }

    public void setGold(ArrayList<PlatinumModal> gold) {
        this.gold = gold;
    }

    public ArrayList<PlatinumModal> getBronze() {
        return bronze;
    }

    public void setBronze(ArrayList<PlatinumModal> bronze) {
        this.bronze = bronze;
    }

    public ArrayList<PlatinumModal> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<PlatinumModal> friends) {
        this.friends = friends;
    }
}
