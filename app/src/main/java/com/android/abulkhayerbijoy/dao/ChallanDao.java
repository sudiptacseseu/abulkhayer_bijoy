package com.android.abulkhayerbijoy.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.abulkhayerbijoy.model.ChallanDetail;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface ChallanDao {

    @Insert
    void insert(ChallanDetail item);

    @Update
    void updateChallan(ChallanDetail item);

    @Query("SELECT * FROM challanitem_table WHERE skuId=:skuID")
    ChallanDetail getChallanBySKUID(int skuID);

    @Insert
    void insertAll(ArrayList<ChallanDetail> items);

    @Query("SELECT * FROM challanitem_table")
    List<ChallanDetail> getChallanStockItems();

    @Query("SELECT * FROM challanitem_table WHERE stockQty > skuSoldQty")
    List<ChallanDetail> getReturnProductItems();

    @Query("SELECT SUM(challanValue) from challanitem_table")
    double getChallanValue();

    @Query("SELECT skuSoldQty * pcsRate from challanitem_table WHERE skuSoldQty > 0 AND id=:id")
    double getSalesProductValue(int id);

    @Query("SELECT SUM(stockQty - skuSoldQty) * pcsRate from challanitem_table WHERE (stockQty - skuSoldQty) > 0 AND id=:id")
    double getReturnProductValue(int id);

    @Update
    void updateChallanItems(List<ChallanDetail> items);

    @Query("DELETE FROM challanitem_table")
    void deleteAll();

}
