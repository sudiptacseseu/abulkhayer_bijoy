package com.android.abulkhayerbijoy.dao.promotion;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import android.database.Cursor;

import com.android.abulkhayerbijoy.model.promotion.ResponseRestrictedTP;

import java.util.List;


@Dao
public interface TPOutletDao {

    @Query("SELECT * from tpoutlet_table")
    List<ResponseRestrictedTP> getAll();

    @Insert
    void insert(ResponseRestrictedTP item);

    @Insert
    void insertAll(List<ResponseRestrictedTP> items);

    @Query("SELECT * from tpoutlet_table limit 1")
    ResponseRestrictedTP getMTDStatus();

    @Query("DELETE FROM tpoutlet_table")
    void deleteAll();

    @Query("SELECT * from tpoutlet_table WHERE OutletID= :OutletID AND TPID= :TPID")
    Cursor getTPOutlet(int OutletID, int TPID);
}
