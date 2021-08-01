package com.android.abulkhayerbijoy.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.abulkhayerbijoy.model.OrderDetail;

import java.util.List;


@Dao
public interface OrderDetailsDao {

    @Insert
    long insert(OrderDetail item);

    @Insert
    void insertAll(OrderDetail items);

    @Query("SELECT * from orderdetail_table")
    List<OrderDetail> getAll();

    @Query("SELECT SUM(orderNetValue - cashCollection) from orderdetail_table")
    double getPreviousCreditByOutlet();

    @Query("SELECT * from orderdetail_table a Where a.uploadStatus= :uploadStatus")
    List<OrderDetail> getAllOutletByUploadStatus(int uploadStatus);

    @Query("SELECT orderNetValue from orderdetail_table where outletID= :outletID")
    double getSoldValueByOutletID(int outletID);

    @Query("SELECT * from orderdetail_table where outletID= :outletID limit 1")
    OrderDetail getOrderDetailsByOutletID(int outletID);

    @Query("SELECT COUNT(*) from orderdetail_table")
    int getOrderedOutletCount();

    @Query("DELETE FROM orderdetail_table where outletID= :outletID")
    void deleteByOutlet(int outletID);

    @Query("DELETE FROM orderdetail_table")
    void deleteAll();

}
