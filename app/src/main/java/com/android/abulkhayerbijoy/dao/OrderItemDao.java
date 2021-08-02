package com.android.abulkhayerbijoy.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.abulkhayerbijoy.model.OrderItem;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface OrderItemDao {

    @Insert
    void insert(OrderItem item);

    @Insert
    void insertAll(ArrayList<OrderItem> items);

    @Query("SELECT * from OrderItem_table where orderID= :orderID")
    List<OrderItem> getOrderDetailsByOrderID(int orderID);

    @Query("SELECT SUM(orderQty) from OrderItem_table where skuID= :skuID")
    int getSoldQtyBySKU(int skuID);

    @Query("SELECT * from OrderItem_table WHERE orderID IN(:salesOrderIds)")
    List<OrderItem> findAllByIds(List<Integer> salesOrderIds);

    @Query("SELECT * from OrderItem_table")
    List<OrderItem> getAll();

    @Query("DELETE FROM OrderItem_table where orderID= :orderID")
    void deleteByOrderID(int orderID);

    @Query("DELETE FROM OrderItem_table")
    void deleteAll();

}
