package com.android.abulkhayerbijoy.dao;


import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.abulkhayerbijoy.model.SectionDetail;

import java.util.List;


@Dao
public interface SectionDao {

    @Query("SELECT * from section_table")
    List<SectionDetail> getAll();

    @Query("SELECT * from section_table where sectionID=:sectionID and routeID=:routeID")
    SectionDetail getSRSectionInfo(int sectionID, int routeID);

    @Query("SELECT * from section_table")
    Cursor getAllSKU();

    @Insert
    void insert(SectionDetail item);

    @Insert
    void insertAll(List<SectionDetail> items);

    @Query("DELETE FROM section_table")
    void deleteAll();
}
