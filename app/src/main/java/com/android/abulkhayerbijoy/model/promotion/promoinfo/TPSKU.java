package com.android.abulkhayerbijoy.model.promotion.promoinfo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = DatabaseConstants.DatabaseName.TABLE_TP_SKU)
public class TPSKU {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("ID")
    @Expose
    public int ID;

    @SerializedName("TPID")
    @Expose
    public int TPID;

    @SerializedName("SKUID")
    @Expose
    public int SKUID;

    @SerializedName("Ratio")
    @Expose
    public int Ratio;

}
