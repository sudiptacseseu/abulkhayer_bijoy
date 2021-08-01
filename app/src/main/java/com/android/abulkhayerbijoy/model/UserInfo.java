package com.android.abulkhayerbijoy.model;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo extends BaseObservable {

    @SerializedName("CreationDate")
    @Expose
    private String CreationDate;

    @SerializedName("Address")
    @Expose
    private String address;

    @SerializedName("ChannelName")
    @Expose
    private String channelName;

    public UserInfo() { }

    public String getCreatedDate() {
        return CreationDate;
    }

    public void setCreatedDate(String createdDate) {
        this.CreationDate = createdDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
