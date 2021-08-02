package com.android.abulkhayerbijoy.dao.promotion.promotioninfo;


import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.android.abulkhayerbijoy.model.promotion.promoinfo.TradePromotion;

import java.util.List;


@Dao
public interface TradePromotionsDao {

    @Query("SELECT * from tblTradePromotion")
    List<TradePromotion> getAll();

    @Insert
    void insert(TradePromotion item);

    @Insert
    void insertAll(List<TradePromotion> items);

    @Query("DELETE FROM tblTradePromotion")
    void deleteAll();

    @Query("SELECT tblTradePromotion.id, name, tblTPSlab.giftItemBanglaName nameBangla, basis, unit, type, IsCustSpecific, tblTP_SKU.skuId, Title, Packsize, tblTP_SKU.ratio, IsRestricted " +
            "FROM tblTradePromotion LEFT OUTER JOIN tblTP_SKU ON tblTradePromotion.id = tblTP_SKU.TPID LEFT JOIN SKU_table ON SKU_table.SKUID = tblTP_SKU.skuId LEFT JOIN tblTPChannels " +
            "ON tblTradePromotion.id = tblTPChannels.TPID LEFT JOIN tblTPSlab ON tblTPSlab.TPID = tblTradePromotion.id WHERE tblTPChannels.ChannelID = :channelID AND type!=2")
    Cursor getTradePromotionOffers(int channelID);

    @Query("SELECT COUNT(CustomerID) FROM tblSPCustomer WHERE SPID = :spID AND CustomerID = :customerID")
    Cursor IsCustomerEligibleForTradePromotion(int spID, int customerID);
}
