package com.android.abulkhayerbijoy.viewmodel;


import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.abulkhayerbijoy.model.Challan;
import com.android.abulkhayerbijoy.model.SalesOrderPromotion;
import com.android.abulkhayerbijoy.model.promotion.FreeItem;
import com.android.abulkhayerbijoy.repository.DatabaseCallRepository;
import com.android.abulkhayerbijoy.repository.NetworkCallRepository;
import com.android.abulkhayerbijoy.utils.MemoHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class OutletDetailsActivityViewModel extends AndroidViewModel {

    private NetworkCallRepository nRepository;
    private DatabaseCallRepository dbRepository;

    public OutletDetailsActivityViewModel(Application application) {
        super(application);
        nRepository = new NetworkCallRepository(application);
        dbRepository = new DatabaseCallRepository(application);
    }


    public LiveData<List<SalesOrderPromotion>> getPromotionsByOrderID(int orderID) {
        return dbRepository.getPromotionsByOrderID(orderID);
    }

    public Observable<List<SalesOrderPromotion>> getPromotionsByType(int orderID, int promoType) {
        return dbRepository.getPromotionsByType(orderID, promoType);
    }

    public Observable<List<Challan>> GetChallanItems() {
        return dbRepository.getChallanStockItems();
    }

    public Observable<List<Challan>> getChallanStockItemWithMapping(int outletID, MemoHelper memoInstance) {
        return dbRepository.getChallanStockItemWithMapping(outletID,memoInstance);
    }


    public Completable deleteNinsertSalesOrderPromotion(int orderID, ArrayList<FreeItem> items) {
        return dbRepository.deleteNinsertSalesOrderPromotion(orderID, items);
    }
}