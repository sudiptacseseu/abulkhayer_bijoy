package com.android.abulkhayerbijoy.model.promotion;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = DatabaseConstants.DatabaseName.TPRSlab)
public class ResponseSPSlab {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    private int id;
    @SerializedName("SlabID")
    @Expose
    private Integer slabID;
    @SerializedName("SPID")
    @Expose
    private Integer sPID;
    @SerializedName("SlabNo")
    @Expose
    private Integer slabNo;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Threshold")
    @Expose
    private Integer threshold;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("IsOTP")
    @Expose
    private Integer isOTP;
    @SerializedName("OperationType")
    @Expose
    private Integer operationType;

    @SerializedName("Margin")
    @Expose
    private Double margin;

    @SerializedName("Details")
    @Expose
    private String details;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSlabID() {
        return slabID;
    }

    public void setSlabID(Integer slabID) {
        this.slabID = slabID;
    }

    public Integer getSPID() {
        return sPID;
    }

    public void setSPID(Integer sPID) {
        this.sPID = sPID;
    }

    public Integer getSlabNo() {
        return slabNo;
    }

    public void setSlabNo(Integer slabNo) {
        this.slabNo = slabNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsOTP() {
        return isOTP;
    }

    public void setIsOTP(Integer isOTP) {
            this.isOTP = isOTP;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Double getMargin() {
        return margin;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}