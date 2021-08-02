package com.android.abulkhayerbijoy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = DatabaseConstants.DatabaseName.CHALLAN)
public class Challan implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    public int id;

    @SerializedName("ChallanID")
    @Expose
    public int challanID;

    @SerializedName("SKUID")
    @Expose
    public int skuId;

    @SerializedName("Title")
    @Expose
    public String title;

    @SerializedName("BanglaName")
    @Expose
    public String banglaName;

    @SerializedName("StockQty")
    @Expose
    public int stockQty;

    @SerializedName("SkuSoldQty")
    @Expose
    public int skuSoldQty;

    @SerializedName("PcsRate")
    @Expose
    public double pcsRate;

    @SerializedName("CartonPcsRatio")
    @Expose
    public int cartonPcsRatio;

    @SerializedName("ChallanValue")
    @Expose
    public double challanValue;

    //Helper Property
    @Ignore
    public int remainingQty;
    @Ignore
    public double netProductPrice;
    @Ignore
    public double cashCollected;
    @Ignore
    public double previousNetCredit;
    @Ignore
    public int orderQty;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChallanID() {
        return challanID;
    }

    public void setChallanID(int challanID) {
        this.challanID = challanID;
    }

    public double getChallanValue() {
        return challanValue;
    }

    public void setChallanValue(double challanValue) {
        this.challanValue = challanValue;
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

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public int getRemainingQty() {
        return remainingQty;
    }

    public void setRemainingQty(int remainingQty) {
        this.remainingQty = remainingQty;
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

    public int getSkuSoldQty() {
        return skuSoldQty;
    }

    public void setSkuSoldQty(int skuSoldQty) {
        this.skuSoldQty = skuSoldQty;
    }

    public Challan() {
    }

    @Ignore
    public Challan(int skuId, String title) {
        this.skuId = skuId;
        this.title = title;
    }

}