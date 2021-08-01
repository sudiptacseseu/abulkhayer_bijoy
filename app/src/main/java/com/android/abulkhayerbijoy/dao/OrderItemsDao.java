package com.android.abulkhayerbijoy.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.abulkhayerbijoy.model.OrderItemDetail;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface OrderItemsDao {

    @Insert
    void insert(OrderItemDetail item);

    @Insert
    void insertAll(ArrayList<OrderItemDetail> items);

    @Query("SELECT * from orderitem_table where orderID= :orderID")
    List<OrderItemDetail> getOrderDetailsByOrderID(int orderID);

    @Query("SELECT SUM(orderQty) from orderitem_table where skuID= :skuID")
    int getSoldQtyBySKU(int skuID);

    @Query("SELECT * from orderitem_table WHERE orderID IN(:salesOrderIds)")
    List<OrderItemDetail> findAllByIds(List<Integer> salesOrderIds);

    @Query("SELECT * from orderitem_table")
    List<OrderItemDetail> getAll();

    @Query("DELETE FROM orderitem_table where orderID= :orderID")
    void deleteByOrderID(int orderID);

    @Query("DELETE FROM orderitem_table")
    void deleteAll();

}
