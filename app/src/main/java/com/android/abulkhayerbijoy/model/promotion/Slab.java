package com.android.abulkhayerbijoy.model.promotion;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = DatabaseConstants.DatabaseName.SlabItem)
public class Slab {

    //Constants
    public static final int GIFT_ITEM = 3;  //previous 0
    public static final int FREE_PRODUCT = 2;  //previous 0
    public static final int DISCOUNT = 1;  // previous 1

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    public int id;

    @SerializedName("SPID")
    @Expose
    public int tprId;

    @SerializedName("SlabID")
    @Expose
    public int slabId;

    @SerializedName("SlabNo")
    @Expose
    public int slabNo;

    @SerializedName("BonusType")
    @Expose
    public int slabType;

    @SerializedName("ForEach")
    @Expose
    public int forEach;

    @SerializedName("ItemDesc")
    @Expose
    public String description;

    @SerializedName("FreeAmount")
    @Expose
    public double itemQty; //Changed 02 Sep 2018. Server Sending double number

    public int skuId;
    public int minQty;

    //Extra prop
    @Ignore
    public double margin;
    @Ignore
    public String slabSeqName;
    @Ignore
    public double mrpPrice;
    @Ignore
    public double tradePrice;
    @Ignore
    public int outletId;
    @Ignore
    public int channelId;
    @Ignore
    public String skuTitle;

    @Ignore
    public double bonusQty;
    @Ignore
    public int freeSKU_ID;
    @Ignore
    public String giftItem;
    @Ignore
    public String giftItemBanglaName;
    @Ignore
    public int giftItemID;
    @Ignore
    public int DiscountType;
}
