package com.android.abulkhayerbijoy.model.promotion.promoinfo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = DatabaseConstants.DatabaseName.TABLE_SP_CUSTOMER)
public class SPCustomer {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("ID")
    @Expose
    public int ID;

    @SerializedName("CustomerID")
    @Expose
    public int CustomerID;

    @SerializedName("SPID")
    @Expose
    public int SPID;

    @SerializedName("Remaining")
    @Expose
    public int Remaining;
}
