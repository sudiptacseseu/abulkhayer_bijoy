package com.android.abulkhayerbijoy.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.abulkhayerbijoy.model.Challan;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface ChallanDao {

    @Insert
    void insert(Challan item);

    @Update
    void updateChallan(Challan item);

    @Query("SELECT * FROM ChallanItem_table WHERE skuId=:skuID")
    Challan getChallanBySKUID(int skuID);

    @Insert
    void insertAll(ArrayList<Challan> items);

    @Query("SELECT * FROM ChallanItem_table")
    List<Challan> getChallanStockItems();

    @Query("SELECT * FROM ChallanItem_table WHERE stockQty > skuSoldQty")
    List<Challan> getReturnProductItems();

    @Query("SELECT SUM(challanValue) from ChallanItem_table")
    double getChallanValue();

    @Query("SELECT skuSoldQty * pcsRate from ChallanItem_table WHERE skuSoldQty > 0 AND id=:id")
    double getSalesProductValue(int id);

    @Query("SELECT SUM(stockQty - skuSoldQty) * pcsRate from ChallanItem_table WHERE (stockQty - skuSoldQty) > 0 AND id=:id")
    double getReturnProductValue(int id);

    @Update
    void updateChallanItems(List<Challan> items);

    @Query("DELETE FROM ChallanItem_table")
    void deleteAll();

}
