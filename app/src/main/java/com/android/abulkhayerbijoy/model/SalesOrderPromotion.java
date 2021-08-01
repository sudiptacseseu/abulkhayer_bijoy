package com.android.abulkhayerbijoy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import androidx.databinding.library.baseAdapters.BR;
import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = DatabaseConstants.DatabaseName.SALES_ORDER_PROMOTION)
public class SalesOrderPromotion extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    private int id;

    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("OfferInfo")
    @Expose
    private String offerInfo;
    @SerializedName("OrderID")
    @Expose
    private Integer orderID;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOfferInfo() {
        return offerInfo;
    }

    public void setOfferInfo(String offerInfo) {
        this.offerInfo = offerInfo;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }
}
