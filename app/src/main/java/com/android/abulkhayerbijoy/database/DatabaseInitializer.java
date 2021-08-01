package com.android.abulkhayerbijoy.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.abulkhayerbijoy.dao.ChallanDao;
import com.android.abulkhayerbijoy.dao.OrderDetailsDao;
import com.android.abulkhayerbijoy.dao.OrderItemsDao;
import com.android.abulkhayerbijoy.dao.OutletDao;
import com.android.abulkhayerbijoy.dao.SKUDao;
import com.android.abulkhayerbijoy.dao.SRBasicDao;
import com.android.abulkhayerbijoy.dao.SalesOrderPromotionDao;
import com.android.abulkhayerbijoy.dao.SectionDao;
import com.android.abulkhayerbijoy.dao.promotion.SPSKUChannelDao;
import com.android.abulkhayerbijoy.dao.promotion.SPSlabDao;
import com.android.abulkhayerbijoy.dao.promotion.SlabItemDao;
import com.android.abulkhayerbijoy.dao.promotion.TPOutletDao;
import com.android.abulkhayerbijoy.dao.promotion.TprDao;
import com.android.abulkhayerbijoy.dao.promotion.promotioninfo.SPSlabBonusesDao;
import com.android.abulkhayerbijoy.dao.promotion.promotioninfo.TPChannelsDao;
import com.android.abulkhayerbijoy.dao.promotion.promotioninfo.TPCustomersDao;
import com.android.abulkhayerbijoy.dao.promotion.promotioninfo.TPSKUDao;
import com.android.abulkhayerbijoy.dao.promotion.promotioninfo.TradePromotionsDao;
import com.android.abulkhayerbijoy.model.ChallanDetail;
import com.android.abulkhayerbijoy.model.OrderDetail;
import com.android.abulkhayerbijoy.model.OrderItemDetail;
import com.android.abulkhayerbijoy.model.OutletDetail;
import com.android.abulkhayerbijoy.model.SKUDetail;
import com.android.abulkhayerbijoy.model.SRBasic;
import com.android.abulkhayerbijoy.model.SalesOrderPromotion;
import com.android.abulkhayerbijoy.model.SectionDetail;
import com.android.abulkhayerbijoy.model.promotion.ResponseRestrictedTP;
import com.android.abulkhayerbijoy.model.promotion.ResponseSPSlab;
import com.android.abulkhayerbijoy.model.promotion.SPSKUChannel;
import com.android.abulkhayerbijoy.model.promotion.Slab;
import com.android.abulkhayerbijoy.model.promotion.Tpr;
import com.android.abulkhayerbijoy.model.promotion.promoinfo.SPCustomer;
import com.android.abulkhayerbijoy.model.promotion.promoinfo.SPSlabBonuses;
import com.android.abulkhayerbijoy.model.promotion.promoinfo.TPChannels;
import com.android.abulkhayerbijoy.model.promotion.promoinfo.TPSKU;
import com.android.abulkhayerbijoy.model.promotion.promoinfo.TradePromotion;


@Database(entities = {SRBasic.class, Tpr.class, SPSKUChannel.class, ResponseSPSlab.class, Slab.class, ResponseRestrictedTP.class,
        SKUDetail.class, ChallanDetail.class, OrderDetail.class, OrderItemDetail.class, SectionDetail.class, OutletDetail.class, SalesOrderPromotion.class,
        TradePromotion.class, TPChannels.class, SPCustomer.class, TPSKU.class, SPSlabBonuses.class}, version = 1, exportSchema = false)

public abstract class DatabaseInitializer extends RoomDatabase {

    public abstract SRBasicDao SRBasicDao();

    public abstract TprDao tprDao();

    public abstract SPSKUChannelDao sPSKUChannelDao();

    public abstract SPSlabDao sPSlabDao();

    public abstract SlabItemDao slabItemDao();

    public abstract TPOutletDao tPOutletDao();

    public abstract SKUDao sKUDao();

    public abstract ChallanDao challanDao();

    public abstract OrderDetailsDao orderDetailsDao();

    public abstract OrderItemsDao orderItemsDao();

    public abstract SectionDao sectionDao();

    public abstract OutletDao outletDao();

    public abstract SalesOrderPromotionDao salesOrderPromotionDao();

    public abstract TradePromotionsDao tradePromotionsDao();

    public abstract TPCustomersDao tpCustomersDao();

    public abstract TPChannelsDao tpChannelsDao();

    public abstract TPSKUDao tpSkuDao();

    public abstract SPSlabBonusesDao spSlabBonusesDao();

    private static DatabaseInitializer INSTANCE;

    public static DatabaseInitializer getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatabaseInitializer.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DatabaseInitializer.class, "abulkhayer_bijoy")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback = new Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            //new PopulateDbAsync(INSTANCE).execute();
        }
    };

}
