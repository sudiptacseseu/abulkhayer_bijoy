package com.android.abulkhayerbijoy.model.promotion.promoinfo;

import java.io.Serializable;

public class TPGiven implements Serializable {
    public int tpId;
    public int slabId;
    public int soldQuantity;
    public int bonusType;
    public int freeSKU_ID;
    public double freeQty; //Previouslt it was int
    public double discount;
    public String giftItem;
    public String giftItemBanglaName="";
    public int giftItemID;

    //Extra property
    public String tpItemName;

    @Override
    public String toString() {
        return "TPGiven: tpId:" + tpId + ", sold quantity:" + soldQuantity + ", bonusType:" + bonusType +
                ", freeSKU_ID:" + freeSKU_ID +
                ", freeQty: " + freeQty + ", discount: " + discount + ", giftItem: " + giftItem+ ", giftItemBanglaName: " + giftItemBanglaName;
    }
}