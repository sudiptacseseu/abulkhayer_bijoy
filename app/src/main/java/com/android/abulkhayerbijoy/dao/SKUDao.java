package com.android.abulkhayerbijoy.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import android.database.Cursor;

import com.android.abulkhayerbijoy.model.SKU;

import java.util.List;


@Dao
public interface SKUDao {

    @Query("SELECT * from SKU_table where skuId= :skuID")
    SKU getSKUByID(int skuID);

    @Query("SELECT * from SKU_table")
    Cursor getAllSKU();

    @Query("SELECT * from SKU_table")
    List<SKU> getSkuItems();

    @Insert
    void insert(SKU item);

    @Insert
    void insertAll(List<SKU> items);

    @Query("DELETE FROM SKU_table")
    void deleteAll();
}
