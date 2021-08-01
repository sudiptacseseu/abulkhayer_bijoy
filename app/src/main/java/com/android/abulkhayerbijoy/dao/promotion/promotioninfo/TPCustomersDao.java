package com.android.abulkhayerbijoy.dao.promotion.promotioninfo;


import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.abulkhayerbijoy.model.promotion.promoinfo.SPCustomer;

import java.util.List;


@Dao
public interface TPCustomersDao {

    @Query("SELECT * from tblSPCustomer")
    List<SPCustomer> getAll();

    @Insert
    void insert(SPCustomer item);

    @Insert
    void insertAll(List<SPCustomer> items);

    @Query("DELETE FROM tblSPCustomer")
    void deleteAll();

    @Query("SELECT * FROM tblSPCustomer WHERE SPID= :spID AND CustomerID = :customerID")
    Cursor GetSPCustomer(int spID, int customerID);


}
