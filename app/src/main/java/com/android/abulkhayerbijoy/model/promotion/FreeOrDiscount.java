package com.android.abulkhayerbijoy.model.promotion;

import com.android.abulkhayerbijoy.model.promotion.promoinfo.TPGiven;

import java.util.ArrayList;

public class FreeOrDiscount {
    public ArrayList<TPGiven> tpGivens;
    public ArrayList<FreeItem> freeItemList;
    public double discount;

    public FreeOrDiscount() {
        freeItemList = new ArrayList<FreeItem>();
        discount = 0;
    }
}
