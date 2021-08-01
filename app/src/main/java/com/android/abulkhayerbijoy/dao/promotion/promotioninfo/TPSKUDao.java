package com.android.abulkhayerbijoy.dao.promotion.promotioninfo;


import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.abulkhayerbijoy.model.promotion.promoinfo.TPSKU;

import java.util.List;


@Dao
public interface TPSKUDao {

    @Query("SELECT * from tblTP_SKU")
    List<TPSKU> getAll();

    @Insert
    void insert(TPSKU item);

    @Insert
    void insertAll(List<TPSKU> items);

    @Query("DELETE FROM tblTP_SKU")
    void deleteAll();

    @Query("SELECT * FROM tblTP_SKU A WHERE A.TPID = :tprID")
    Cursor getSPSkuByTpID(int tprID);

}
