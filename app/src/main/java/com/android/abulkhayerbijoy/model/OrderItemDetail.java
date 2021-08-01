package com.android.abulkhayerbijoy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = DatabaseConstants.DatabaseName.ORDER_ITEMS)
public class OrderItemDetail implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    public int id;

    @SerializedName("OrderID")
    @Expose
    private Integer orderID;

    @SerializedName("SKUID")
    @Expose
    public int skuId;

    @SerializedName("Title")
    @Expose
    public String title;

    @SerializedName("BanglaName")
    @Expose
    public String banglaName;

    @SerializedName("OrderQty")
    @Expose
    public int orderQty;

    @SerializedName("PcsRate")
    @Expose
    public double pcsRate;

    @SerializedName("CartonPcsRatio")
    @Expose
    public int cartonPcsRatio;

    //Helper property
    @Ignore
    public int Carton;
    @Ignore
    public int Piece;
    @Ignore
    public double Total;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanglaName() {
        return banglaName;
    }

    public void setBanglaName(String banglaName) {
        this.banglaName = banglaName;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getPcsRate() {
        return pcsRate;
    }

    public void setPcsRate(double pcsRate) {
        this.pcsRate = pcsRate;
    }

    public int getCartonPcsRatio() {
        return cartonPcsRatio;
    }

    public void setCartonPcsRatio(int cartonPcsRatio) {
        this.cartonPcsRatio = cartonPcsRatio;
    }

    public OrderItemDetail() {
    }

    @Ignore
    public OrderItemDetail(int skuId, String title) {
        this.skuId = skuId;
        this.title = title;
    }
}