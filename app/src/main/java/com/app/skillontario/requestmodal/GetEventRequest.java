package com.app.skillontario.requestmodal;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class GetEventRequest implements Parcelable {
    String eType;
    String eventId;
    String page;
    String pageLimit;
    String search;
    private Context context;
    public GetEventRequest(Context context) {
        this.context = context;
    }

    protected GetEventRequest(Parcel in) {
        eType = in.readString();
        eventId = in.readString();
        page = in.readString();
        pageLimit = in.readString();
        search = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eType);
        dest.writeString(eventId);
        dest.writeString(page);
        dest.writeString(pageLimit);
        dest.writeString(search);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GetEventRequest> CREATOR = new Creator<GetEventRequest>() {
        @Override
        public GetEventRequest createFromParcel(Parcel in) {
            return new GetEventRequest(in);
        }

        @Override
        public GetEventRequest[] newArray(int size) {
            return new GetEventRequest[size];
        }
    };

    public String geteType() {
        return eType;
    }

    public void seteType(String eType) {
        this.eType = eType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageLimit() {
        return pageLimit;
    }

    public void setPageLimit(String pageLimit) {
        this.pageLimit = pageLimit;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
