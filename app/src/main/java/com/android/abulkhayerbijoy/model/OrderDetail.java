package com.android.abulkhayerbijoy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = DatabaseConstants.DatabaseName.ORDER)
public class OrderDetail implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    private int id;


    @SerializedName("OutletID")
    @Expose
    private Integer outletID;

    @SerializedName("OutletName")
    @Expose
    private String outletName;

    @SerializedName("OrderValue")
    @Expose
    private Double orderValue;

    @SerializedName("OrderNetValue")
    @Expose
    private Double orderNetValue;

    @SerializedName("PreviousCredit")
    @Expose
    private Double previousCredit;

    @SerializedName("Longitude")
    @Expose
    private Double longitude;

    @SerializedName("Latitude")
    @Expose
    private Double latitude;

    @SerializedName("orderStatus")
    @Expose
    private int orderStatus;

    @SerializedName("uploadStatus")
    @Expose
    private int uploadStatus;

    @SerializedName("CashCollection")
    @Expose
    private double cashCollection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Integer getOutletID() {
        return outletID;
    }

    public void setOutletID(Integer outletID) {
        this.outletID = outletID;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public Double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Double orderValue) {
        this.orderValue = orderValue;
    }

    public Double getOrderNetValue() {
        return orderNetValue;
    }

    public void setOrderNetValue(Double orderNetValue) {
        this.orderNetValue = orderNetValue;
    }

    public Double getPreviousCredit() {
        return previousCredit;
    }

    public void setPreviousCredit(Double previousCredit) {
        this.previousCredit = previousCredit;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(int uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public double getCashCollection() {
        return cashCollection;
    }

    public void setCashCollection(double cashCollection) {
        this.cashCollection = cashCollection;
    }

    public OrderDetail() {
    }
}