package com.android.abulkhayerbijoy.dao.promotion.promotioninfo;


import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.abulkhayerbijoy.model.promotion.promoinfo.SPSlabBonuses;

import java.util.List;


@Dao
public interface SPSlabBonusesDao {

    @Query("SELECT * from tblTPSlab")
    List<SPSlabBonuses> getAll();

    @Insert
    void insert(SPSlabBonuses item);

    @Insert
    void insertAll(List<SPSlabBonuses> items);

    @Query("DELETE FROM tblTPSlab")
    void deleteAll();


    @Query("Select slabID, TPID, threshold, bonusType, forEach, freeQty, freeSKUID, giftItem, giftItemBanglaName, DiscountType,giftItemID " +
            "FROM tblTPSlab where TPID = :tpId AND threshold <= :quantity ORDER BY threshold DESC")
    Cursor getTradePromotionSlabs(int tpId, double quantity);

    @Query("Select slabID, TPID, threshold, bonusType, forEach, freeQty, freeSKUID, giftItem, giftItemBanglaName, DiscountType,giftItemID FROM tblTPSlab where TPID =:tpId ORDER BY threshold DESC")
    Cursor getTradePromotionSlabs(int tpId);

}
