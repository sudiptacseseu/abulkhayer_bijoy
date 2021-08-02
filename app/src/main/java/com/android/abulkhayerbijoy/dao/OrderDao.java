package com.android.abulkhayerbijoy.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.abulkhayerbijoy.model.Order;

import java.util.List;


@Dao
public interface OrderDao {

    @Insert
    long insert(Order item);

    @Insert
    void insertAll(Order items);

    @Query("SELECT * from Order_table")
    List<Order> getAll();

    @Query("SELECT SUM(orderNetValue - cashCollection) from Order_table")
    double getPreviousCreditByOutlet();

    @Query("SELECT * from Order_table a Where a.uploadStatus= :uploadStatus")
    List<Order> getAllOutletByUploadStatus(int uploadStatus);

    @Query("SELECT orderNetValue from Order_table where outletID= :outletID")
    double getSoldValueByOutletID(int outletID);

    @Query("SELECT * from Order_table where outletID= :outletID limit 1")
    Order getOrderDetailsByOutletID(int outletID);

    @Query("SELECT COUNT(*) from Order_table")
    int getOrderedOutletCount();

    @Query("DELETE FROM Order_table where outletID= :outletID")
    void deleteByOutlet(int outletID);

    @Query("DELETE FROM Order_table")
    void deleteAll();

}
