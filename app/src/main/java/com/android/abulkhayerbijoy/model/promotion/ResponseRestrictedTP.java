package com.android.abulkhayerbijoy.model.promotion;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = DatabaseConstants.DatabaseName.TPOutlet)
public class ResponseRestrictedTP {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    public int id;

    @SerializedName("TPID")
    @Expose
    private int TPID;

    @SerializedName("OutletID")
    @Expose
    private int OutletID;

    @SerializedName("IsOTPApplicable")
    @Expose
    private int IsOTPApplicable;

    public int getTPID() {
        return TPID;
    }

    public void setTPID(int TPID) {
        this.TPID = TPID;
    }

    public int getOutletID() {
        return OutletID;
    }

    public void setOutletID(int outletID) {
        OutletID = outletID;
    }

    public int getIsOTPApplicable() {
        return IsOTPApplicable;
    }

    public void setIsOTPApplicable(int isOTPApplicable) {
        IsOTPApplicable = isOTPApplicable;
    }
}
