package com.android.abulkhayerbijoy.dao.promotion;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import android.database.Cursor;

import com.android.abulkhayerbijoy.model.promotion.Tpr;

import java.util.List;


@Dao
public interface TprDao {

    @Query("SELECT * from tpr_table")
    List<Tpr> getAll();

    @Insert
    void insert(Tpr item);

    @Insert
    void insertAll(List<Tpr> items);

    @Query("SELECT * from tpr_table WHERE tprId= :tprID")
    Tpr getTPRByID(int tprID);

    @Query("DELETE FROM tpr_table")
    void deleteAll();

    @Query("SELECT * from tpr_table WHERE TPRID IN (SELECT TPRID FROM tprskuchannel_table WHERE ChannelID= :channelID)")
    Cursor getAllTprList(int channelID);

    @Query("SELECT * from tpr_table WHERE TPRID IN (SELECT TPRID FROM tprskuchannel_table WHERE channelID= :channelID AND sKUID= :skuID)")
    Cursor getTprListBySKU(int channelID, int skuID);

}
