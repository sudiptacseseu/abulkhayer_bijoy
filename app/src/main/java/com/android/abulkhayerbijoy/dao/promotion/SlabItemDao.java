package com.android.abulkhayerbijoy.dao.promotion;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.abulkhayerbijoy.model.promotion.Slab;

import java.util.List;


@Dao
public interface SlabItemDao {

    @Query("SELECT * from slabitem_table")
    List<Slab> getAll();

    @Insert
    void insert(Slab item);

    @Insert
    void insertAll(List<Slab> items);

    @Query("SELECT * from slabitem_table limit 1")
    Slab getMTDStatus();

    @Query("DELETE FROM slabitem_table")
    void deleteAll();

    @Query("SELECT * FROM slabitem_table WHERE SlabID= :slabID AND TPRID= :tprID ")
    Slab getSlab(int slabID, int tprID);
}
