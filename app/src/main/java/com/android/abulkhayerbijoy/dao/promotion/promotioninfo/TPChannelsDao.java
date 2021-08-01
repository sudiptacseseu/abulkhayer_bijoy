package com.android.abulkhayerbijoy.dao.promotion.promotioninfo;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.abulkhayerbijoy.model.promotion.promoinfo.TPChannels;

import java.util.List;


@Dao
public interface TPChannelsDao {

    @Query("SELECT * from tblTPChannels")
    List<TPChannels> getAll();

    @Insert
    void insert(TPChannels item);

    @Insert
    void insertAll(List<TPChannels> items);

    @Query("DELETE FROM tblTPChannels")
    void deleteAll();
}
