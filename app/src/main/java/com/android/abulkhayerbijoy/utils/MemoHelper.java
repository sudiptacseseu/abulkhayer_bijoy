package com.android.abulkhayerbijoy.utils;

import com.android.abulkhayerbijoy.model.Challan;
import com.android.abulkhayerbijoy.model.Order;
import com.android.abulkhayerbijoy.model.OrderItem;
import com.android.abulkhayerbijoy.model.SalesOrderPromotion;
import com.android.abulkhayerbijoy.model.promotion.FreeOrDiscount;

import java.util.ArrayList;

public class MemoHelper {

    private static MemoHelper instance;
    private ArrayList<OrderItem> orderItemDetails;
    private ArrayList<Order> orderDetails;
    private ArrayList<Challan> challanDetails;
    private FreeOrDiscount freeOrDiscount;
    private ArrayList<SalesOrderPromotion> clpPromotions;


    private MemoHelper() {
        setFreeOrDiscount(new FreeOrDiscount());
        setOrderDetails(new ArrayList<>());
        setOrderItems(new ArrayList<>());
        setChallanItems(new ArrayList<>());
    }

    public static MemoHelper instance() {
        if (instance == null)
            instance = new MemoHelper();
        return instance;
    }

    public ArrayList<Challan> getChallanItems() {
        return challanDetails;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItemDetails;
    }

    public ArrayList<Order> getOrderDetails() {
        return orderDetails;
    }

    public void setChallanItems(ArrayList<Challan> challanDetails) {
        this.challanDetails = challanDetails;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItemDetails) {
        this.orderItemDetails = orderItemDetails;
    }

    public double getOrderNetTotal() {
        double total = 0.0;
        if (orderItemDetails != null && orderItemDetails.size() > 0)
            for (OrderItem item : orderItemDetails) {
                total += item.orderQty * item.pcsRate;
            }
        return SystemHelper.formatDouble(total);
    }

    public double getCashCollection() {
        double total = 0.0;
        if (orderItemDetails != null && orderItemDetails.size() > 0)
            for (OrderItem item : orderItemDetails) {
                total = item.orderQty;
            }
        return SystemHelper.formatDouble(total);
    }

    public void setOrderDetails(ArrayList<Order> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setChallanItem(Challan challanDetailItem) {
        if (this.challanDetails != null && challanDetails != null)
            this.challanDetails.add(challanDetailItem);
    }


    public FreeOrDiscount getFreeOrDiscountItem() {
        return freeOrDiscount;
    }

    public void setFreeOrDiscount(FreeOrDiscount freeOrDiscount) {
        this.freeOrDiscount = freeOrDiscount;
    }

    public ArrayList<SalesOrderPromotion> getCLPPromotionsItems() {
        return clpPromotions;
    }

    public void setCLPPromotionsItems(ArrayList<SalesOrderPromotion> clpPromotions) {
        this.clpPromotions = clpPromotions;
    }

    public void clear() {
        instance = null;
    }


}
