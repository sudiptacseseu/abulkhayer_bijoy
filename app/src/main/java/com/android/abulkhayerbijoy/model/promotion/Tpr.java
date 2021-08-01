package com.android.abulkhayerbijoy.model.promotion;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.android.abulkhayerbijoy.model.SKUDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity(tableName = DatabaseConstants.DatabaseName.Tpr)
public class Tpr {

    public static int ProgramType_TPRTypeCombination = 4;

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    public int id;

    @SerializedName("Name")
    @Expose
    public String description;

    @SerializedName("CeilingAmount")
    @Expose
    public int ceilingQty;

    @SerializedName("PromotionID")
    @Expose
    public int tprId;

    public int isRestricted;

    public int isFixedTPR = 0; // BY DEFAULT 0 IS ADDED

    public int programmType;

    //For mapping purpose only
    @SerializedName("IsRestricted")
    @Expose
    @Ignore
    public boolean isRestrict;

    @Ignore
    public SKUDetail sku;
}
