package com.android.abulkhayerbijoy.dao;


import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.abulkhayerbijoy.model.Outlet;

import java.util.List;


@Dao
public interface OutletDao {

    @Query("SELECT * from Outlet_table")
    List<Outlet> getAll();

    @Query("SELECT * from Outlet_table where visited= :status")
    List<Outlet> getAllByStatus(int status);

    @Query("SELECT COUNT(name) from Outlet_table where visited= :status")
    int getOutletCountByStatus(int status);

    @Query("SELECT COUNT(id) from Outlet_table")
    int getOutletCount();

    @Query("SELECT SUM(outletDue) from Outlet_table")
    double getDueValue();

    @Query("UPDATE Outlet_table SET visited= :status where customerID= :outletID")
    void updateOutletStatus(int status, int outletID);

    @Query("SELECT * from Outlet_table")
    Cursor getAllSKU();

    @Insert
    void insert(Outlet item);

    @Insert
    void insertAll(List<Outlet> items);

    @Query("DELETE FROM Outlet_table")
    void deleteAll();
}
