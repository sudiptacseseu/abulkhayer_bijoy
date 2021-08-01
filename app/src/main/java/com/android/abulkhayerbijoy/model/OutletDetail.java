package com.android.abulkhayerbijoy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = DatabaseConstants.DatabaseName.OUTLET)
public class OutletDetail implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    public int id;

    @SerializedName("CustomerID")
    @Expose
    public int customerID;

    @SerializedName("Name")
    @Expose
    public String name;

    @SerializedName("BanglaName")
    @Expose
    public String banglaName;

    @SerializedName("ChannelID")
    @Expose
    public int channelID;

    @SerializedName("OwnerName")
    @Expose
    public String ownerName;

    @SerializedName("Address")
    @Expose
    public String address;

    @SerializedName("BanglaAddress")
    @Expose
    public String banglaAddress;

    @SerializedName("ContactNo")
    @Expose
    public String contactNo;

    @SerializedName("Visited")
    @Expose
    public int visited;

    @SerializedName("MarketID")
    @Expose
    public String marketID;

    @SerializedName("RouteID")
    @Expose
    public int routeID;

    @SerializedName("Latitude")
    @Expose
    public double latitude;

    @SerializedName("Longitude")
    @Expose
    public double longitude;

    @SerializedName("Location")
    @Expose
    public String location;

    //Helper - Display Property
    public double outletDue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanglaName() {
        return banglaName;
    }

    public void setBanglaName(String banglaName) {
        this.banglaName = banglaName;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBanglaAddress() {
        return banglaAddress;
    }

    public void setBanglaAddress(String banglaAddress) {
        this.banglaAddress = banglaAddress;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public String getMarketID() {
        return marketID;
    }

    public void setMarketID(String marketID) {
        this.marketID = marketID;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public OutletDetail() {
    }
}