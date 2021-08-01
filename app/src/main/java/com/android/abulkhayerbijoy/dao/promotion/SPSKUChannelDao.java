package com.android.abulkhayerbijoy.dao.promotion;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import android.database.Cursor;

import com.android.abulkhayerbijoy.model.promotion.SPSKUChannel;

import java.util.List;


@Dao
public interface SPSKUChannelDao {

    @Query("SELECT * from tprskuchannel_table")
    List<SPSKUChannel> getAll();

    @Insert
    void insert(SPSKUChannel item);

    @Insert
    void insertAll(List<SPSKUChannel> items);

    @Query("SELECT * from tprskuchannel_table limit 1")
    SPSKUChannel getMTDStatus();

    @Query("DELETE FROM tprskuchannel_table")
    void deleteAll();

    @Query("SELECT tprskuchannel_table.SKUID SKUID FROM tprskuchannel_table INNER JOIN sku_table ON tprskuchannel_table.SKUID = sku_table.SKUID WHERE tprskuchannel_table.sPID= :tprID AND tprskuchannel_table.channelID= :channelID")
    Cursor getSkuListForTpr(int tprID, int channelID);
}
