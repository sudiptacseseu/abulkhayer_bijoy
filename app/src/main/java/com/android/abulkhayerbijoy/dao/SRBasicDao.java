package com.android.abulkhayerbijoy.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.abulkhayerbijoy.model.SRBasic;

import java.util.List;


@Dao
public interface SRBasicDao {

    @Query("SELECT * from srbasic_table")
    List<SRBasic> getAll();

    @Query("SELECT * from srbasic_table limit 1")
    SRBasic getUser();

    @Insert
    void insert(SRBasic item);

    @Query("UPDATE srbasic_table SET Name = :name, BanglaName = :banglaName, SectionID = :sectionID, DeliveryGroupID = :deliveryGroupID, RouteID = :routeID, DistributorName = :distributorName, DistributorBanglaName = :distributorBanglaName, VisitDate = :visitDate, DailyTarget = :dailyTarget, monthlyTarget = :monthlyTarget")
    void updateDSRinfo(String name, String banglaName, int sectionID, int deliveryGroupID, int routeID, String distributorName, String distributorBanglaName, String visitDate, double dailyTarget, double monthlyTarget);

    @Query("DELETE FROM srbasic_table")
    void deleteAll();
}
