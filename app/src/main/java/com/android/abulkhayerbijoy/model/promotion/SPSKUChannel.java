package com.android.abulkhayerbijoy.model.promotion;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = DatabaseConstants.DatabaseName.TPRSKUChnl)
public class SPSKUChannel {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    private int id;
    @SerializedName("SPID")
    @Expose
    private Integer sPID;
    @SerializedName("SKUID")
    @Expose
    private Integer sKUID;
    @SerializedName("Ratio")
    @Expose
    private Double ratio;
    @SerializedName("ChannelID")
    @Expose
    private Integer channelID;
    @SerializedName("TPRSKUChnlID")
    @Expose
    private int tPRSKUChnlID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSPID() {
        return sPID;
    }

    public void setSPID(Integer sPID) {
        this.sPID = sPID;
    }

    public Integer getSKUID() {
        return sKUID;
    }

    public void setSKUID(Integer sKUID) {
        this.sKUID = sKUID;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public Integer getChannelID() {
        return channelID;
    }

    public void setChannelID(Integer channelID) {
        this.channelID = channelID;
    }

    public int getTPRSKUChnlID() {
        return tPRSKUChnlID;
    }

    public void setTPRSKUChnlID(int tPRSKUChnlID) {
        this.tPRSKUChnlID = tPRSKUChnlID;
    }

}
