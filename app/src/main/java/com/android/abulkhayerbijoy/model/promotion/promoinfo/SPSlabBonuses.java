package com.android.abulkhayerbijoy.model.promotion.promoinfo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = DatabaseConstants.DatabaseName.TABLE_TP_SLAB)
public class SPSlabBonuses {

    //{"BonusID":1149,"SPID":411,"SlabID":794,"Threshold":1.0000,"BonusType":1,
    // "ForEach":1.0000,"FreeQty":0.0800,"FreeSKUID":null,
    // "GiftItem":null,"GiftItemBanglaName":null,"GiftItemID":null,"DiscountType":0}

    @PrimaryKey(autoGenerate = true)
    @SerializedName("ID")
    @Expose
    public int ID;

    @SerializedName("BonusID")
    @Expose
    public int BonusID;

    @SerializedName("SPID")
    @Expose
    public int TPID;

    @SerializedName("SlabID")
    @Expose
    public int SlabID;

    @SerializedName("Threshold")
    @Expose
    public double Threshold;

    @SerializedName("BonusType")
    @Expose
    public int BonusType;

    @SerializedName("ForEach")
    @Expose
    public double ForEach;

    @SerializedName("FreeQty")
    @Expose
    public double FreeQty;

    @SerializedName("FreeSKUID")
    @Expose
    public int FreeSKUID;

    @SerializedName("GiftItem")
    @Expose
    public String GiftItem;

    @SerializedName("GiftItemBanglaName")
    @Expose
    public String GiftItemBanglaName;

    @SerializedName("GiftItemID")
    @Expose
    public int GiftItemID;

    @SerializedName("DiscountType")
    @Expose
    public int DiscountType;

}
