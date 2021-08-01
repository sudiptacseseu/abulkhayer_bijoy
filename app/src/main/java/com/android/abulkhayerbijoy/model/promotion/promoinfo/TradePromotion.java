package com.android.abulkhayerbijoy.model.promotion.promoinfo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.android.abulkhayerbijoy.model.SKUDetail;
import com.android.abulkhayerbijoy.model.promotion.Slab;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = DatabaseConstants.DatabaseName.TABLE_TRADE_PROMOTION)
public class TradePromotion {

    //Constants
    public static final int COMBINATIONAL = 2;
    public static final int BUNDLE = 3;

    @PrimaryKey
    @SerializedName("ID")
    @Expose
    public int ID;

    @SerializedName("Name")
    @Expose
    public String Name;

    @SerializedName("NameBangla")
    @Expose
    public String NameBangla;

    @SerializedName("Basis")
    @Expose
    public int Basis;

    @SerializedName("Unit")
    @Expose
    public int Unit;

    @SerializedName("Type")
    @Expose
    public int Type;

    @SerializedName("IsCustSpecific")
    @Expose
    public int IsCustSpecific;

    @SerializedName("IsRestricted")
    @Expose
    public int IsRestricted;

    @SerializedName("Color")
    @Expose
    public String Color;

    @Ignore
    public ArrayList<SKUDetail> skus;
    @Ignore
    public ArrayList<Slab> slabs;

    @Ignore
    private boolean IsCustSpecificBool;
    @Ignore
    private boolean IsRestrictedBool;

    public boolean isCustSpecificBool() {
        return IsCustSpecific > 0 ? true : false;
    }

    public boolean isRestrictedBool() {
        return IsRestricted > 0 ? true : false;
    }
}
