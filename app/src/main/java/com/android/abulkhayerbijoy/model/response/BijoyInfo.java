package com.android.abulkhayerbijoy.model.response;

import com.android.abulkhayerbijoy.model.ChallanDetail;
import com.android.abulkhayerbijoy.model.OrderDetail;
import com.android.abulkhayerbijoy.model.OrderItemDetail;
import com.android.abulkhayerbijoy.model.SRBasic;
import com.android.abulkhayerbijoy.model.SalesOrderPromotion;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BijoyInfo {

    @SerializedName("OrderMaster")
    @Expose
    public ArrayList<OrderDetail> orderMaster;

    @SerializedName("OrderItems")
    @Expose
    public ArrayList<OrderItemDetail> orderItems;

    @SerializedName("ChallanItems")
    @Expose
    public ArrayList<ChallanDetail> challanItems;

    @SerializedName("SalesOrderPromotion")
    @Expose
    public ArrayList<SalesOrderPromotion> salesOrderPromotion;

    @SerializedName("SRBasic")
    @Expose
    public SRBasic srBasic;
}
