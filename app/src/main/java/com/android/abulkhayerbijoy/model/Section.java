package com.android.abulkhayerbijoy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = DatabaseConstants.DatabaseName.SECTION)
public class Section implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    public int id;

    @SerializedName("Title")
    @Expose
    public String title;

    @SerializedName("SRID")
    @Expose
    public int srID;

    @SerializedName("SectionID")
    @Expose
    public int sectionID;

    @SerializedName("RouteID")
    @Expose
    public int routeID;

    @SerializedName("RouteName")
    @Expose
    public String routeName;

    @SerializedName("RouteBanglaName")
    @Expose
    public String routeBanglaName;

    @SerializedName("DeliveryGroupID")
    @Expose
    public int deliveryGroupID;

    @SerializedName("Code1")
    @Expose
    public String code1;

    @SerializedName("OrderColDay")
    @Expose
    public String orderColDay;

    @SerializedName("OrderDlvDay")
    @Expose
    public String orderDlvDay;

    @SerializedName("OrderDate")
    @Expose
    public String orderDate;

    @SerializedName("OwnSection")
    @Expose
    public String ownSection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSrID() {
        return srID;
    }

    public void setSrID(int srID) {
        this.srID = srID;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteBanglaName() {
        return routeBanglaName;
    }

    public void setRouteBanglaName(String routeBanglaName) {
        this.routeBanglaName = routeBanglaName;
    }

    public int getDeliveryGroupID() {
        return deliveryGroupID;
    }

    public void setDeliveryGroupID(int deliveryGroupID) {
        this.deliveryGroupID = deliveryGroupID;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getOrderColDay() {
        return orderColDay;
    }

    public void setOrderColDay(String orderColDay) {
        this.orderColDay = orderColDay;
    }

    public String getOrderDlvDay() {
        return orderDlvDay;
    }

    public void setOrderDlvDay(String orderDlvDay) {
        this.orderDlvDay = orderDlvDay;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOwnSection() {
        return ownSection;
    }

    public void setOwnSection(String ownSection) {
        this.ownSection = ownSection;
    }

    public Section() {
    }
}