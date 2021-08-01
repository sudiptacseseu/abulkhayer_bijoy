package com.android.abulkhayerbijoy.model.promotion;

public class FreeItem {
    /*public String skuName;
    public int qty;
    public double discountValue;

    public FreeItem(String skuName, int qty) {
        this.skuName = skuName;
        this.qty = qty;
    }

    public FreeItem(String skuName, int qty, double discountValue) {
        this.skuName = skuName;
        this.qty = qty;
        this.discountValue = discountValue;
    }*/

    public int skuId;
    public String skuName;
    public double qty;
    public int giftitemID;
    public double discountValue;

    public FreeItem(int skuId, String skuName, double qty, int giftitemID) {
        this.skuId = skuId;
        this.skuName = skuName;
        this.qty = qty;
        this.giftitemID = giftitemID;
    }

}
