package com.android.abulkhayerbijoy.dao;


import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.abulkhayerbijoy.model.Section;

import java.util.List;


@Dao
public interface SectionDao {

    @Query("SELECT * from Section_table")
    List<Section> getAll();

    @Query("SELECT * from Section_table where sectionID=:sectionID and routeID=:routeID")
    Section getSRSectionInfo(int sectionID, int routeID);

    @Query("SELECT * from Section_table")
    Cursor getAllSKU();

    @Insert
    void insert(Section item);

    @Insert
    void insertAll(List<Section> items);

    @Query("DELETE FROM Section_table")
    void deleteAll();
}
