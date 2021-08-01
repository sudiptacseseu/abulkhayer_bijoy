package com.android.abulkhayerbijoy.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.android.abulkhayerbijoy.BR;
import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = DatabaseConstants.DatabaseName.SR_BASIC_TABLE)
public class SRBasic extends BaseObservable {

    public static final String USER_INFO = "SRBasic_USER_INFO";

    public SRBasic() {
    }

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    @Expose
    private Integer id;

    @SerializedName("SRID")
    @Expose
    private Integer srID;
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("Authorised")
    @Expose
    private Integer authorized;
    @SerializedName("SystemID")
    @Expose
    private Integer systemID;
    @SerializedName("SubsystemID")
    @Expose
    private Integer subsystemID;
    @SerializedName("SalesPointID")
    @Expose
    private Integer salesPointID;
    @SerializedName("LastSyncDate")
    @Expose
    private String lastSyncDate;
    @SerializedName("SessionData")
    @Expose
    private String sessionData;
    @SerializedName("UserType")
    @Expose
    private Integer userType;
    @SerializedName("DeliveryManID")
    @Expose
    private Integer deliveryManID;
    @SerializedName("NAME")
    @Expose
    private String Name;
    @SerializedName("BanglaName")
    @Expose
    private String BanglaName;
    @SerializedName("VisitDate")
    @Expose
    private String VisitDate;

    @SerializedName("SectionID")
    @Expose
    private Integer sectionID;
    @SerializedName("DeliveryGroupID")
    @Expose
    private Integer deliveryGroupID;
    @SerializedName("RouteID")
    @Expose
    private Integer routeID;

    @SerializedName("DailyTarget")
    @Expose
    private double dailyTarget;

    @SerializedName("Target")
    @Expose
    private double monthlyTarget;

    @SerializedName("DistributorName")
    @Expose
    private String DistributorName;

    @SerializedName("DistributorBanglaName")
    @Expose
    private String DistributorBanglaName;

    //Helper Property
    private String routeName;

    @Bindable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public static String getUserInfo() {
        return USER_INFO;
    }

    public Integer getSrID() {
        return srID;
    }

    public void setSrID(Integer srID) {
        this.srID = srID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Integer authorized) {
        this.authorized = authorized;
    }

    public Integer getSystemID() {
        return systemID;
    }

    public void setSystemID(Integer systemID) {
        this.systemID = systemID;
    }

    public Integer getSubsystemID() {
        return subsystemID;
    }

    public void setSubsystemID(Integer subsystemID) {
        this.subsystemID = subsystemID;
    }

    public Integer getSalesPointID() {
        return salesPointID;
    }

    public void setSalesPointID(Integer salesPointID) {
        this.salesPointID = salesPointID;
    }

    public String getLastSyncDate() {
        return lastSyncDate;
    }

    public void setLastSyncDate(String lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }

    public String getSessionData() {
        return sessionData;
    }

    public void setSessionData(String sessionData) {
        this.sessionData = sessionData;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getDeliveryManID() {
        return deliveryManID;
    }

    public void setDeliveryManID(Integer deliveryManID) {
        this.deliveryManID = deliveryManID;
    }

    public String getBanglaName() {
        return BanglaName;
    }

    public void setBanglaName(String banglaName) {
        BanglaName = banglaName;
    }

    public String getVisitDate() {
        return VisitDate;
    }

    public void setVisitDate(String visitDate) {
        VisitDate = visitDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getSectionID() {
        return sectionID;
    }

    public void setSectionID(Integer sectionID) {
        this.sectionID = sectionID;
    }

    public Integer getDeliveryGroupID() {
        return deliveryGroupID;
    }

    public void setDeliveryGroupID(Integer deliveryGroupID) {
        this.deliveryGroupID = deliveryGroupID;
    }

    public Integer getRouteID() {
        return routeID;
    }

    public void setRouteID(Integer routeID) {
        this.routeID = routeID;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public double getDailyTarget() {
        return dailyTarget;
    }

    public void setDailyTarget(double dailyTarget) {
        this.dailyTarget = dailyTarget;
    }

    public double getMonthlyTarget() {
        return monthlyTarget;
    }

    public void setMonthlyTarget(double monthlyTarget) {
        this.monthlyTarget = monthlyTarget;
    }

    public String getDistributorName() {
        return DistributorName;
    }

    public void setDistributorName(String distributorName) {
        DistributorName = distributorName;
    }

    public String getDistributorBanglaName() {
        return DistributorBanglaName;
    }

    public void setDistributorBanglaName(String distributorBanglaName) {
        DistributorBanglaName = distributorBanglaName;
    }
}
