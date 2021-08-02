package com.android.abulkhayerbijoy.model.response;

import com.android.abulkhayerbijoy.model.Challan;
import com.android.abulkhayerbijoy.model.Order;
import com.android.abulkhayerbijoy.model.OrderItem;
import com.android.abulkhayerbijoy.model.SRBasic;
import com.android.abulkhayerbijoy.model.SalesOrderPromotion;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BijoyInfo {

    @SerializedName("OrderMaster")
    @Expose
    public ArrayList<Order> orderMaster;

    @SerializedName("OrderItems")
    @Expose
    public ArrayList<OrderItem> orderItems;

    @SerializedName("ChallanItems")
    @Expose
    public ArrayList<Challan> challanItems;

    @SerializedName("SalesOrderPromotion")
    @Expose
    public ArrayList<SalesOrderPromotion> salesOrderPromotion;

    @SerializedName("SRBasic")
    @Expose
    public SRBasic srBasic;
}
