package com.android.abulkhayerbijoy.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.abulkhayerbijoy.model.SalesOrderPromotion;

import java.util.List;


@Dao
public interface SalesOrderPromotionDao {

    @Query("SELECT * from SalesOrderPromotion_table where orderID= :orderID")
    LiveData<List<SalesOrderPromotion>> getPromotionsByOrderID(int orderID);

    @Query("SELECT * from SalesOrderPromotion_table where orderID= :orderID and type= :type")
    List<SalesOrderPromotion> getPromotionsByType(int orderID, int type);


    @Query("SELECT * from SalesOrderPromotion_table")
    List<SalesOrderPromotion> getAll();

    @Query("SELECT * from SalesOrderPromotion_table WHERE orderID IN(:orderIds)")
    List<SalesOrderPromotion> findAllByIds(List<Integer> orderIds);

    @Query("Delete from SalesOrderPromotion_table WHERE orderID = :orderID and type not in (5)") // Delete Promo without CLP
    void DeletePromoByOrderID(int orderID);

    @Insert
    void insert(SalesOrderPromotion item);

    @Query("DELETE FROM SalesOrderPromotion_table")
    void deleteAll();
}
