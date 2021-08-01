package com.android.abulkhayerbijoy.model.promotion.promoinfo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = DatabaseConstants.DatabaseName.TABLE_TP_CHANNEL)
public class TPChannels {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("ID")
    @Expose
    public int ID;

    @SerializedName("TPID")
    @Expose
    public int TPID;

    @SerializedName("ChannelID")
    @Expose
    public int ChannelID;

}
