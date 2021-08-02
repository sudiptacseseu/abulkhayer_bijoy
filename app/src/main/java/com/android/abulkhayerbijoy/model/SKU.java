package com.android.abulkhayerbijoy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = DatabaseConstants.DatabaseName.SKU)
public class SKU implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    public int id;

    @SerializedName("SKUID")
    @Expose
    public int skuId;

    @SerializedName("Title")
    @Expose
    public String title;

    @SerializedName("ProductID")
    @Expose
    public int productId;

    @SerializedName("ProductType")
    @Expose
    public String productType;

    @SerializedName("ShortName")
    @Expose
    public String ShortName;

    @SerializedName("BanglaName")
    @Expose
    public String banglaName;

    @SerializedName("BrandID")
    @Expose
    public int brandId;

    @SerializedName("StockQty")
    @Expose
    public int stockQty;

    @SerializedName("InputNumbers")
    @Expose
    public int inputNumbers;

    @SerializedName("ImageModifiedDate")
    @Expose
    public String imageModifiedDate;

    @SerializedName("PcsRate")
    @Expose
    public double pcsRate;

    @SerializedName("CartonPcsRatio")
    @Expose
    public int cartonPcsRatio;

    @SerializedName("PackSize")
    @Expose
    public double packSize;

    @SerializedName("CtnRate")
    @Expose
    public double ctnRate;

    @SerializedName("IsNewProduct")
    @Expose
    public int isNewProduct;

    @SerializedName("IsNonSaleable")
    @Expose
    public int isNonSaleable;

    @SerializedName("CriticalStock")
    @Expose
    public int criticalStock;

    @SerializedName("PositionValue")
    @Expose
    public int positionValue;

    @SerializedName("MonoPcsRatio")
    @Expose
    public int monoPcsRatio;

    @Ignore
    public int soldQty;
    @Ignore
    public int remaining;
    @Ignore
    public int ratio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }

    public String getBanglaName() {
        return banglaName;
    }

    public void setBanglaName(String banglaName) {
        this.banglaName = banglaName;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public int getInputNumbers() {
        return inputNumbers;
    }

    public void setInputNumbers(int inputNumbers) {
        this.inputNumbers = inputNumbers;
    }

    public String getImageModifiedDate() {
        return imageModifiedDate;
    }

    public void setImageModifiedDate(String imageModifiedDate) {
        this.imageModifiedDate = imageModifiedDate;
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

    public double getPackSize() {
        return packSize;
    }

    public void setPackSize(double packSize) {
        this.packSize = packSize;
    }

    public double getCtnRate() {
        return ctnRate;
    }

    public void setCtnRate(double ctnRate) {
        this.ctnRate = ctnRate;
    }

    public int getIsNewProduct() {
        return isNewProduct;
    }

    public void setIsNewProduct(int isNewProduct) {
        this.isNewProduct = isNewProduct;
    }

    public int getIsNonSaleable() {
        return isNonSaleable;
    }

    public void setIsNonSaleable(int isNonSaleable) {
        this.isNonSaleable = isNonSaleable;
    }

    public int getCriticalStock() {
        return criticalStock;
    }

    public void setCriticalStock(int criticalStock) {
        this.criticalStock = criticalStock;
    }

    public int getPositionValue() {
        return positionValue;
    }

    public void setPositionValue(int positionValue) {
        this.positionValue = positionValue;
    }

    public int getMonoPcsRatio() {
        return monoPcsRatio;
    }

    public void setMonoPcsRatio(int monoPcsRatio) {
        this.monoPcsRatio = monoPcsRatio;
    }

    public SKU() {
    }

    @Ignore
    public SKU(int skuId, String title) {
        this.skuId = skuId;
        this.title = title;
    }
}