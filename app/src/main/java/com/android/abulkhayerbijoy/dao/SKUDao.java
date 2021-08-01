package com.android.abulkhayerbijoy.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import android.database.Cursor;

import com.android.abulkhayerbijoy.model.SKUDetail;

import java.util.List;


@Dao
public interface SKUDao {

    @Query("SELECT * from sku_table where skuId= :skuID")
    SKUDetail getSKUByID(int skuID);

    @Query("SELECT * from sku_table")
    Cursor getAllSKU();

    @Query("SELECT * from sku_table")
    List<SKUDetail> getSkuItems();

    @Insert
    void insert(SKUDetail item);

    @Insert
    void insertAll(List<SKUDetail> items);

    @Query("DELETE FROM sku_table")
    void deleteAll();
}
