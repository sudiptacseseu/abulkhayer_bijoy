package com.android.abulkhayerbijoy.dao.promotion;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import android.database.Cursor;

import com.android.abulkhayerbijoy.model.promotion.ResponseSPSlab;

import java.util.List;


@Dao
public interface SPSlabDao {

    @Query("SELECT * from tprslab_table")
    List<ResponseSPSlab> getAll();

    @Insert
    void insert(ResponseSPSlab item);

    @Insert
    void insertAll(List<ResponseSPSlab> items);

    @Query("SELECT * from tprslab_table limit 1")
    ResponseSPSlab getMTDStatus();

    @Query("DELETE FROM tprslab_table")
    void deleteAll();

    @Query("Select slabID FROM tprslab_table where sPID = :tprID AND threshold <= :minQty ORDER BY threshold DESC")
    Cursor getSlabListByTpID(int tprID, double minQty);
}
