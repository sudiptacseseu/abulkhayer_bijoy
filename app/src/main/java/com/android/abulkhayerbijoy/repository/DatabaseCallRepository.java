package com.android.abulkhayerbijoy.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import android.database.Cursor;
import android.util.ArrayMap;

import com.android.abulkhayerbijoy.dao.ChallanDao;
import com.android.abulkhayerbijoy.dao.OrderDao;
import com.android.abulkhayerbijoy.dao.OrderItemDao;
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
import com.android.abulkhayerbijoy.database.DatabaseConstants;
import com.android.abulkhayerbijoy.database.DatabaseInitializer;
import com.android.abulkhayerbijoy.model.CashDeposit;
import com.android.abulkhayerbijoy.model.Challan;
import com.android.abulkhayerbijoy.model.Order;
import com.android.abulkhayerbijoy.model.OrderItem;
import com.android.abulkhayerbijoy.model.Outlet;
import com.android.abulkhayerbijoy.model.SKU;
import com.android.abulkhayerbijoy.model.SRBasic;
import com.android.abulkhayerbijoy.model.SalesOrderPromotion;
import com.android.abulkhayerbijoy.model.Section;
import com.android.abulkhayerbijoy.model.promotion.FreeItem;
import com.android.abulkhayerbijoy.model.promotion.FreeOrDiscount;
import com.android.abulkhayerbijoy.model.promotion.ResponseSPSlab;
import com.android.abulkhayerbijoy.model.promotion.SPSKUChannel;
import com.android.abulkhayerbijoy.model.promotion.Slab;
import com.android.abulkhayerbijoy.model.promotion.Tpr;
import com.android.abulkhayerbijoy.model.promotion.promoinfo.SPCustomer;
import com.android.abulkhayerbijoy.model.promotion.promoinfo.SPSlabBonuses;
import com.android.abulkhayerbijoy.model.promotion.promoinfo.TPChannels;
import com.android.abulkhayerbijoy.model.promotion.promoinfo.TPGiven;
import com.android.abulkhayerbijoy.model.promotion.promoinfo.TPSKU;
import com.android.abulkhayerbijoy.model.promotion.promoinfo.TradePromotion;
import com.android.abulkhayerbijoy.model.response.BijoyInfo;
import com.android.abulkhayerbijoy.utils.BijoyHelper;
import com.android.abulkhayerbijoy.utils.MemoHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;


public class DatabaseCallRepository {


    //private DeliveryManBasicDao mUserDao;
    private SRBasicDao userDao;
    private TprDao mTprDao;
    private SPSKUChannelDao mSPSKUChannelDao;
    private SPSlabDao sPSlabDao;
    private SlabItemDao slabItemDao;
    private TPOutletDao tPOutletDao;
    private SKUDao sKUDao;
    private ChallanDao challanDao;
    private OrderDao orderDetailsDao;
    private OrderItemDao orderItemsDao;
    private SectionDao sectionDao;
    private OutletDao outletDao;
    private SalesOrderPromotionDao salesOrderPromotionDao;
    private TradePromotionsDao tradePromotionsDao;
    private TPCustomersDao tpCustomersDao;
    private TPChannelsDao tpChannelsDao;
    private TPSKUDao tpSkuDao;
    private SPSlabBonusesDao spSlabBonusesDao;
    DatabaseInitializer db = null;

    public DatabaseCallRepository(Application application) {
        db = DatabaseInitializer.getDatabase(application);
        userDao = db.SRBasicDao();
        mTprDao = db.tprDao();
        mSPSKUChannelDao = db.sPSKUChannelDao();
        sPSlabDao = db.sPSlabDao();
        slabItemDao = db.slabItemDao();
        tPOutletDao = db.tPOutletDao();
        sKUDao = db.sKUDao();
        challanDao = db.challanDao();
        orderDetailsDao = db.orderDetailsDao();
        orderItemsDao = db.orderItemsDao();
        sectionDao = db.sectionDao();
        outletDao = db.outletDao();
        salesOrderPromotionDao = db.salesOrderPromotionDao();
        tradePromotionsDao = db.tradePromotionsDao();
        tpCustomersDao = db.tpCustomersDao();
        tpChannelsDao = db.tpChannelsDao();
        tpSkuDao = db.tpSkuDao();
        spSlabBonusesDao = db.spSlabBonusesDao();
    }

    //region OTHERS

    public Completable clearAllTables() {
        return Completable.fromRunnable(
                () -> {
                    db.clearAllTables();
                }
        );
    }

    public Completable deleteChallanNOrderInfo() {
        return Completable.fromRunnable(
                () -> {
                    challanDao.deleteAll();
                    orderDetailsDao.deleteAll();
                    orderItemsDao.deleteAll();
                }
        );
    }

    public Completable insertSRBasic(SRBasic item) {
        return Completable.fromRunnable(
                () -> userDao.insert(item)
        );
    }

    public Completable updateSRBasicInfo(SRBasic item) {
        return Completable.fromRunnable(
                () -> userDao.updateDSRinfo(item.getName(), item.getBanglaName(), item.getSectionID(),
                        item.getDeliveryGroupID(), item.getRouteID(), item.getDistributorName(), item.getDistributorBanglaName(), item.getVisitDate(), item.getDailyTarget(), item.getMonthlyTarget())
        );
    }


    public Maybe<SRBasic> getSRBasic() {
        return Maybe.fromCallable(() -> userDao.getUser());
    }

    public Completable deleteDeliveryManBasic() {
        return Completable.fromRunnable(
                () -> userDao.deleteAll()
        );
    }

    public Completable insertSKUDetails(List<SKU> items) {
        return Completable.fromRunnable(
                () -> {
                    sKUDao.deleteAll();
                    sKUDao.insertAll(items);
                }
        );
    }

    public Completable insertChallanDetails(ArrayList<Challan> items) {
        return Completable.fromRunnable(
                () -> {
                    //challanDao.deleteAll();
                    //challanDao.insertAll(items);

                    for (Challan citem : items) {
                        Challan challan = challanDao.getChallanBySKUID(citem.skuId);
                        if (challan == null)
                            challanDao.insert(citem);
                        else {
                            int previousQty = challan.stockQty;
                            int currentQty = citem.stockQty;
                            int finalQty = previousQty + currentQty;

                            Challan editChallan = new Challan();
                            editChallan.id = challan.id;
                            editChallan.challanID = challan.challanID;
                            editChallan.skuId = citem.skuId;
                            editChallan.stockQty = finalQty;
                            editChallan.banglaName = citem.banglaName;
                            editChallan.pcsRate = citem.pcsRate;
                            editChallan.cartonPcsRatio = citem.cartonPcsRatio;
                            editChallan.challanValue = finalQty * citem.pcsRate;
                            editChallan.setSkuSoldQty(challan.getSkuSoldQty());

                            challanDao.updateChallan(editChallan);
                        }
                    }
                }
        );
    }

    public Completable insertOrderInformation(ArrayList<OrderItem> orderItems, Order orderDetail) {
        return Completable.fromRunnable(
                () -> {

                    //Handling Old Order
                    Order itemOrder = orderDetailsDao.getOrderDetailsByOutletID(orderDetail.getOutletID());
                    if (itemOrder != null) {
                        orderItemsDao.deleteByOrderID(itemOrder.getId()); //child
                        orderDetailsDao.deleteByOutlet(itemOrder.getOutletID()); //parent
                    }

                    //Inserting Order
                    long id = orderDetailsDao.insert(orderDetail);

                    //Inserting Order Item
                    for (OrderItem item : orderItems) {
                        item.setOrderID((int) id);
                        orderItemsDao.insert(item);
                    }

                    //Update Outlet Status
                    outletDao.updateOutletStatus(orderDetail.getOrderStatus(), orderDetail.getOutletID());

                    //Updating Stock
                    List<Challan> challanItems = challanDao.getChallanStockItems();
                    for (Challan chItem : challanItems) {
                        for (OrderItem oItem : orderItems) {
                            if (chItem.getSkuId() == oItem.getSkuId()) {
                                int skuSoldQty = orderItemsDao.getSoldQtyBySKU(chItem.skuId);
                                int prev_skuSoldQty = chItem.skuSoldQty;
                                chItem.setSkuSoldQty(skuSoldQty);

                                //Update Stock Only If Negative
                                int remaining = prev_skuSoldQty - skuSoldQty;
                                int currentStockQty = chItem.getStockQty();
                                if (remaining > 0) {
                                    chItem.setStockQty(currentStockQty + remaining);
                                }
                            }
                        }
                    }
                    challanDao.updateChallanItems(challanItems);
                }
        );
    }

    public Completable insertOrderInformationV2(ArrayList<OrderItem> orderItems, Order orderDetail) {
        return Completable.fromRunnable(
                () -> {

                    //Handling Old Order
                    Order itemOrder = orderDetailsDao.getOrderDetailsByOutletID(orderDetail.getOutletID());
                    if (itemOrder != null) {
                        orderItemsDao.deleteByOrderID(itemOrder.getId());
                        orderDetailsDao.deleteByOutlet(itemOrder.getOutletID());
                    }

                    //Mapping Previous Sold Qty
                    HashMap<Integer, Integer> stockMapping = new HashMap<>();
                    for (OrderItem item : orderItems) {
                        int soldQty = orderItemsDao.getSoldQtyBySKU(item.getSkuId());
                        if (soldQty > 0)
                            stockMapping.put(item.getSkuId(), soldQty);
                    }


                    //Inserting Order
                    long id = orderDetailsDao.insert(orderDetail);

                    //Inserting Order Item
                    for (OrderItem item : orderItems) {
                        item.setOrderID((int) id);
                        orderItemsDao.insert(item);

                        //region Mapping Current Sold Qty
                        if (stockMapping.containsKey(item.getSkuId())) {
                            int currentlySold = stockMapping.get(item.getSkuId());
                            currentlySold += item.getOrderQty();
                            stockMapping.remove(item.getSkuId());
                            stockMapping.put(item.getSkuId(), currentlySold);
                        } else {
                            stockMapping.put(item.getSkuId(), item.getOrderQty());
                        }
                        //endregion
                    }

                    //Challan Table Update -- ?
                    List<Challan> challanItems = challanDao.getChallanStockItems();
                    for (Challan item : challanItems) {
                        int skuID = item.getSkuId();
                        int stockQty = item.getStockQty();
                        int actualSoldQty = 0;

                        if (stockMapping.containsKey(skuID))
                            actualSoldQty = stockMapping.get(skuID);
                        else
                            continue;
                        int remainingQty = stockQty - actualSoldQty;

                        item.setRemainingQty(remainingQty);
                    }

                    outletDao.updateOutletStatus(orderDetail.getOrderStatus(), orderDetail.getOutletID());
                    challanDao.updateChallanItems(challanItems);
                }
        );
    }

    public Observable<Order> getOrderInformation(int OutletID) {
        return Observable.fromCallable(() -> orderDetailsDao.getOrderDetailsByOutletID(OutletID));
    }

    public Observable<Double> getSoldValueByOutletID(int OutletID) {
        return Observable.fromCallable(() -> orderDetailsDao.getSoldValueByOutletID(OutletID));
    }

    public Observable<List<OrderItem>> getAllByOrder(int orderID) {
        return Observable.fromCallable(() -> orderItemsDao.getOrderDetailsByOrderID(orderID));
    }

    public Completable insertSectionDetails(List<Section> items) {
        return Completable.fromRunnable(
                () -> {
                    sectionDao.deleteAll();
                    sectionDao.insertAll(items);
                }
        );
    }

    public Completable insertOutletDetails(List<Outlet> items) {
        return Completable.fromRunnable(
                () -> {
                    outletDao.deleteAll();
                    outletDao.insertAll(items);
                }

        );
    }

    public Observable<List<Challan>> getTotalChallanValue() {
        return Observable.fromCallable(() ->
                challanDao.getChallanStockItems()
        );
    }

    public Observable<List<SKU>> getSkuItems() {
        return Observable.fromCallable(() -> {
            List<SKU> skus = sKUDao.getSkuItems();
            List<Challan> cItems = challanDao.getChallanStockItems();

            if (cItems.size() > 0 && skus.size() > 0) {
                for (SKU sKItem : skus) {
                    for (Challan cItem : cItems) {
                        if (sKItem.getSkuId() == cItem.getSkuId()) {
                            sKItem.setStockQty(cItem.getStockQty());
                            sKItem.soldQty = cItem.skuSoldQty;
                            sKItem.remaining = cItem.getStockQty() - cItem.getSkuSoldQty();
                        }
                    }
                }
            }

            return skus;
        });
    }

    public Observable<List<SKU>> getReturnSkuItems() {
        return Observable.fromCallable(() -> {
            List<SKU> skus = sKUDao.getSkuItems();
            List<Challan> cItems = challanDao.getReturnProductItems();
            List<SKU> fItem = new ArrayList<>();

            if (cItems.size() > 0 && skus.size() > 0) {
                for (SKU sKItem : skus) {
                    for (Challan cItem : cItems) {
                        if (sKItem.getSkuId() == cItem.getSkuId()) {
                            sKItem.setStockQty(cItem.getStockQty());
                            sKItem.soldQty = cItem.skuSoldQty;
                            sKItem.remaining = cItem.getStockQty() - cItem.getSkuSoldQty();
                            fItem.add(sKItem);
                        }
                    }
                }
            }

            return fItem;
        });
    }

    public Observable<List<Challan>> getChallanStockItems() {
        return Observable.fromCallable(() ->
                challanDao.getChallanStockItems());
    }

    public Observable<CashDeposit> getCashDepositInformation() {
        return Observable.fromCallable(() ->
                {
                    double returnProdcutValue = 0.0;
                    double todaysAllOutletCredit = orderDetailsDao.getPreviousCreditByOutlet();
                    double totalChallanValue = challanDao.getChallanValue();
                    double totalDueValue = outletDao.getDueValue();
                    List<Challan> cItems = challanDao.getReturnProductItems();

                    for (Challan cItem : cItems) {

                        double productPriceById = challanDao.getReturnProductValue(cItem.getId());
                        returnProdcutValue += productPriceById;
                    }

                    CashDeposit cDeposit = new CashDeposit();
                    cDeposit.setPreviousDue(totalDueValue);//TODO: NEED TO ADD DUE FROM SERVER
                    cDeposit.setChallanValue(totalChallanValue);
                    cDeposit.setReturnProductPrice(returnProdcutValue);

                    return cDeposit;
                }

        );
    }

    public Observable<Double> getSalesInformation() {
        return Observable.fromCallable(() ->
                {
                    double salesProductValue = 0.0;
                    List<Challan> cItems = challanDao.getChallanStockItems();

                    for (Challan cItem : cItems) {

                        double productPriceById = challanDao.getSalesProductValue(cItem.getId());
                        salesProductValue += productPriceById;
                    }

                    return salesProductValue;
                }

        );
    }

    public Observable<List<Challan>> getChallanStockItemWithMapping(int outletID, MemoHelper memoInstance) {
        return Observable.fromCallable(() -> {
            List<Challan> challanItems = challanDao.getChallanStockItems();
            //calculate remaining
            for (Challan challan : challanItems) {
                challan.remainingQty = challan.getStockQty() - challan.getSkuSoldQty();
            }

            Order oDetails = orderDetailsDao.getOrderDetailsByOutletID(outletID);
            if (oDetails != null) {
                List<OrderItem> oChildItems = orderItemsDao.getOrderDetailsByOrderID(oDetails.getId());
                memoInstance.setOrderItems(new ArrayList<>(oChildItems));
                for (Challan cItem : challanItems) {
                    for (OrderItem oItem : oChildItems) {
                        if (cItem.getSkuId() == oItem.getSkuId()) {
                            cItem.orderQty = oItem.getOrderQty();
                            cItem.netProductPrice = oItem.getPcsRate() * oItem.getOrderQty();
                        }
                    }
                    cItem.cashCollected = oDetails.getCashCollection();
                    //cItem.previousNetCredit = oDetails.getCashCollection();
                }
            } /*else {
                //If the outlet have no previous order - it needs not to concern about sold
                for (ChallanDetail cItem : challanItems) {
                    cItem.remainingQty = cItem.getStockQty() - cItem.getSkuSoldQty();
                    cItem.setSkuSoldQty(0);
                }
            }*/

            return challanItems;

        });
    }

    //endregion

    //region Promotion
    public Completable deleteNinsertPromotions(ArrayList<Tpr> items) {
        return Completable.fromRunnable(
                () -> {
                    mTprDao.deleteAll();
                    mTprDao.insertAll(items);
                }
        );
    }

    public Completable deleteNinsertSPSKUChannel(ArrayList<SPSKUChannel> items) {
        return Completable.fromRunnable(
                () -> {
                    mSPSKUChannelDao.deleteAll();
                    mSPSKUChannelDao.insertAll(items);
                }
        );
    }

    public Completable deleteNinsertSPSlabs(ArrayList<ResponseSPSlab> items) {
        return Completable.fromRunnable(
                () -> {
                    sPSlabDao.deleteAll();
                    sPSlabDao.insertAll(items);
                }
        );
    }

    public Completable deleteNinsertSPBonuses(ArrayList<Slab> items) {
        return Completable.fromRunnable(
                () -> {
                    slabItemDao.deleteAll();
                    slabItemDao.insertAll(items);
                }
        );
    }

    public Completable deleteNinsertSalesOrderPromotion(int orderID, ArrayList<FreeItem> items) {
        return Completable.fromRunnable(
                () -> {
                    salesOrderPromotionDao.DeletePromoByOrderID(orderID);
                    for (FreeItem obj : items) {
                        SalesOrderPromotion item = new SalesOrderPromotion();
                        item.setDescription(obj.skuName);
                        item.setOrderID(orderID);
                        if (obj.discountValue > 0) {
                            item.setType(DatabaseConstants.PromotionFlag.DISCOUNT);
                            item.setOfferInfo(obj.discountValue + " ৳");
                        } else if (obj.qty > 0) {
                            item.setType(DatabaseConstants.PromotionFlag.FREE_PRODUCT);
                            item.setOfferInfo(obj.qty + " পিস");
                        }
                        salesOrderPromotionDao.insert(item);
                    }
                }
        );
    }

    private Slab getSlab(int tprId, int slabId) {
        Slab returnSlab = new Slab();

        try {

            returnSlab = slabItemDao.getSlab(slabId, tprId);
            /*Cursor cursor = null;
            cursor = slabItemDao.getSlab(slabId, tprId);*/

            /*if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    returnSlab.slabId = slabId;
                    returnSlab.tprId = tprId;
                    returnSlab.slabType = cursor
                            .getInt(cursor
                                    .getColumnIndex(DatabaseConstants.tblSlabItem.SLAB_TYPE));
                    returnSlab.description = cursor
                            .getString(cursor
                                    .getColumnIndex(DatabaseConstants.tblSlabItem.ITEM_DESC));
                    returnSlab.forQty = cursor
                            .getInt(cursor
                                    .getColumnIndex(DatabaseConstants.tblSlabItem.FOR_QTY));
                    returnSlab.itemQty = cursor
                            .getInt(cursor
                                    .getColumnIndex(DatabaseConstants.tblSlabItem.ITEM_QTY));
                }
                cursor.close();
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnSlab;
    }

    public Completable insertPromotionInfoFromWeb(String result) {
        return Completable.fromRunnable(
                () -> {
                    try {
                        JSONObject webData = new JSONObject(result);

                        if (webData.has("TradePromotions")) {
                            JSONArray itemArray = webData.optJSONArray("TradePromotions");
                            if (itemArray != null) {
                                List<TradePromotion> items = new Gson().fromJson(itemArray.toString(), new TypeToken<List<TradePromotion>>() {
                                }.getType());
                                tradePromotionsDao.deleteAll();
                                tradePromotionsDao.insertAll(items);
                            }
                        }

                        if (webData.has("TPCustomers")) {
                            JSONArray itemArray = webData.optJSONArray("TPCustomers");
                            if (itemArray != null) {
                                List<SPCustomer> items = new Gson().fromJson(itemArray.toString(), new TypeToken<List<SPCustomer>>() {
                                }.getType());
                                tpCustomersDao.deleteAll();
                                tpCustomersDao.insertAll(items);
                            }
                        }

                        if (webData.has("TPChannels")) {
                            JSONArray itemArray = webData.optJSONArray("TPChannels");
                            if (itemArray != null) {
                                List<TPChannels> items = new Gson().fromJson(itemArray.toString(), new TypeToken<List<TPChannels>>() {
                                }.getType());

                                tpChannelsDao.deleteAll();
                                tpChannelsDao.insertAll(items);
                            }
                        }

                        if (webData.has("TPSKU")) {
                            JSONArray itemArray = webData.optJSONArray("TPSKU");
                            if (itemArray != null) {
                                List<TPSKU> items = new Gson().fromJson(itemArray.toString(), new TypeToken<List<TPSKU>>() {
                                }.getType());
                                tpSkuDao.deleteAll();
                                tpSkuDao.insertAll(items);
                            }
                        }
                        if (webData.has("SPSlabBonuses")) {
                            JSONArray itemArray = webData.optJSONArray("SPSlabBonuses");
                            if (itemArray != null) {
                                List<SPSlabBonuses> items = new Gson().fromJson(itemArray.toString(), new TypeToken<List<SPSlabBonuses>>() {
                                }.getType());
                                spSlabBonusesDao.deleteAll();
                                spSlabBonusesDao.insertAll(items);
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }

    public Observable<FreeOrDiscount> getTradePromotion(int channelId, int customerID, final MemoHelper memoHelper) {
        return Observable.fromCallable(() -> {
            FreeOrDiscount freeOrDiscount = new FreeOrDiscount();
            try {
                ArrayList<OrderItem> orderList = memoHelper.getOrderItems();
                int skuID = 0;
                double ratioQty = 0;
                Map<Integer, Integer> map = new HashMap<Integer, Integer>();
                SPCustomer oSPCustomer = null;

                freeOrDiscount.tpGivens = new ArrayList<>();
                ArrayList<TradePromotion> promotions = getTradePromotionOffers(channelId);

                for (TradePromotion nowTpr : promotions) {
                    if (nowTpr.isCustSpecificBool()) {
                        if (!IsCustomerEligibleForTradePromotion(customerID, nowTpr.ID))
                            //IF CUSTOMER IS NOT ELIGIBLE THEN CONTINUE
                            continue;
                    }
                    if (nowTpr.isRestrictedBool()) {
                        oSPCustomer = GetSPCustomer(customerID, nowTpr.ID);
                    }

                    double orderQuantity = 0;
                    double orderTotal = 0;

                    if (nowTpr.Basis == TradePromotion.BUNDLE) {
                        //ratioQty = 0;
                        double minBundle = Double.MAX_VALUE;
                        //orderQuantity = Double.MAX_VALUE;
                        orderQuantity = 0;

                        map = new HashMap<Integer, Integer>();
                        for (SKU nowSku : nowTpr.skus) {

                            OrderItem nowOrder = getOrderTotalBySkuId(nowSku, orderList);
                            skuID = nowSku.skuId;

                            map.put(nowSku.skuId, nowOrder.orderQty);
                            double quantity = 0;
                            double weight = nowOrder.orderQty * nowSku.packSize;
                            switch (nowTpr.Unit) {
                                case DatabaseConstants.PromotionFlag.TPR_PROGRAM_TYPE_QUANTITY_2:
                                    quantity = nowOrder.orderQty; //Math.floor(sticks / nowSku.packSize);
                                    break;
                                case DatabaseConstants.PromotionFlag.TPR_PROGRAM_TYPE_VALUE:
                                    quantity = nowOrder.Total;//Math.floor(value / nowSku.packSize);
                                    break;
                                case DatabaseConstants.PromotionFlag.TPR_PROGRAM_TYPE_WEIGHT:
                                    quantity = weight;//Math.floor(weight / nowSku.packSize);
                                    break;
                                default:
                                    break;
                            }

                            if (Math.floor(quantity / nowSku.ratio) < minBundle) {
                                minBundle = Math.floor(quantity / nowSku.ratio);
                            }
                            orderQuantity += quantity;
                        }

                        if (minBundle == Double.MAX_VALUE) {
                            orderQuantity = 0;
                            minBundle = 0;
                        }

                        if (minBundle > 0) {

                            if (nowTpr.isRestrictedBool()) {
                                if (oSPCustomer != null) {
                                    minBundle = oSPCustomer.Remaining;
                                }
                            }

                            ArrayList<Slab> slabList = getTradePromotionSlabs(nowTpr.ID, orderQuantity);

                            for (Slab nowSlab : slabList) {
//                        Log.d(TAG, "Promotion:" + nowSlab.toString() );
                                Boolean slabEnrolled = false;
                                TPGiven given = new TPGiven();
                                given.tpId = nowTpr.ID;
                                given.slabId = nowSlab.slabId;
                                given.bonusType = nowSlab.slabType;
                                given.soldQuantity = (int) orderQuantity;

                                double bonus = 0;
                                if (nowSlab.forEach > 0) {
                                    bonus = (double) nowSlab.bonusQty * (minBundle / (double) nowSlab.forEach);
                                    //bonus = (double) nowSlab.bonusQty * (orderQuantity / (double) nowSlab.forEach);
                                } else {
                                    bonus = nowSlab.bonusQty;
                                }
//                        Log.d(TAG, "Slab list::" + bonus );

                                if (nowSlab.slabType == Slab.FREE_PRODUCT && nowSlab.minQty <= minBundle && nowSlab.forEach <= minBundle) {

                                    SKU sku = sKUDao.getSKUByID(nowSlab.freeSKU_ID);
                                    nowSlab.skuTitle = sku.banglaName == null || String.valueOf(sku.banglaName).isEmpty() || String.valueOf(sku.banglaName).equals("null") ? sku.title : sku.banglaName;

                                    freeOrDiscount.freeItemList.add(new FreeItem(nowSlab.freeSKU_ID, nowSlab.skuTitle, (int) bonus, nowSlab.giftItemID));
                                    given.freeSKU_ID = nowSlab.freeSKU_ID;
                                    given.freeQty = (int) bonus;
                                    given.giftItem = "";
                                    given.discount = 0;
                                    freeOrDiscount.tpGivens.add(given);
                                    slabEnrolled = true;

                                } else if (nowSlab.slabType == Slab.GIFT_ITEM && nowSlab.minQty <= orderQuantity && nowSlab.bonusQty <= orderQuantity) {

                                    String giftItemName = nowSlab.giftItemBanglaName == null || String.valueOf(nowSlab.giftItemBanglaName).isEmpty() || String.valueOf(nowSlab.giftItemBanglaName).equals("null") ? nowSlab.giftItem : nowSlab.giftItemBanglaName;

                                    freeOrDiscount.freeItemList.add(new FreeItem(-1, giftItemName, (int) bonus, nowSlab.giftItemID));

                                    given.freeSKU_ID = -1;
                                    given.freeQty = (int) bonus;
                                    given.giftItem = nowSlab.giftItem;
                                    given.giftItemBanglaName = nowSlab.giftItemBanglaName;
                                    given.giftItemID = nowSlab.giftItemID;
                                    given.discount = 0;
                                    freeOrDiscount.tpGivens.add(given);
                                    slabEnrolled = true;

                                } else if (nowSlab.slabType == Slab.DISCOUNT && nowSlab.minQty <= orderQuantity) {

                                    freeOrDiscount.discount += bonus;

                                    given.freeSKU_ID = -1;
                                    given.freeQty = 0;
                                    given.giftItem = "";
                                    given.discount = bonus;
                                    freeOrDiscount.tpGivens.add(given);
                                    slabEnrolled = true;
                                }
                                if (slabEnrolled) {
                                    Set<Integer> keys = map.keySet();  //get all keys
                                    for (Integer i : keys) {
                                        orderList = UpdatedOrderList(nowTpr, nowSlab, i, orderList, orderQuantity);
                                    }
                                }

                                orderQuantity = orderQuantity * nowSlab.bonusQty % (double) nowSlab.forEach;

                            }
                        }

                    } else if (nowTpr.Basis == TradePromotion.COMBINATIONAL && nowTpr.Unit == DatabaseConstants.PromotionFlag.TPR_PROGRAM_TYPE_LINECOUNT) {

                        //Get All Orders
                        //Get All Promo SKUs for this specific TP
                        //Check how much they avail
                        //Add discount
                        ArrayList<Tpr> spSKUList = getSPSkuByTpID(nowTpr.ID);
                        ArrayList<Slab> promoSlabs = getTradePromotionSlabs(nowTpr.ID);
                        int lineCount = 0;

                        for (OrderItem order : orderList) {
                            for (Tpr item : spSKUList) {
                                if (order.skuId == item.sku.skuId) {
                                    lineCount++;
                                    break;
                                }
                            }
                        }

                        double lineCountDiscount = 0.0;
                        if (lineCount > 0) {
                            for (Slab slab : promoSlabs) {
                                if (slab.slabType == Slab.DISCOUNT && lineCount >= slab.minQty) {
                                    freeOrDiscount.discount += slab.bonusQty;
                                    lineCountDiscount += slab.bonusQty;
                                    break;
                                }
                            }
                        }

                        if (lineCountDiscount > 0) {
                            TPGiven given = new TPGiven();
                            given.bonusType = Slab.DISCOUNT;
                            given.freeSKU_ID = -1;
                            given.freeQty = 0;
                            given.giftItem = "";
                            given.discount = lineCountDiscount;
                            freeOrDiscount.tpGivens.add(given);
                        }

                    } else {
                        for (SKU nowSku : nowTpr.skus) {

                            OrderItem nowOrder = getOrderTotalBySkuId(nowSku, orderList);
                            double quantity = nowOrder.orderQty;//double quantity = nowOrder.soldPieces;//*nowOrder.Carton * nowSku.pcsPerCtn +*//* ;
                            double value = nowOrder.Total;
                            skuID = nowOrder.skuId;

                            if (value <= 0 || quantity <= 0)
                                continue;
                            double weight = quantity * nowSku.packSize;
                            switch (nowTpr.Unit) {
                                case DatabaseConstants.PromotionFlag.TPR_PROGRAM_TYPE_QUANTITY_2:
                                    orderQuantity += quantity;
                                    orderTotal += nowOrder.Total;
                                    break;
                                case DatabaseConstants.PromotionFlag.TPR_PROGRAM_TYPE_VALUE:
                                    orderQuantity += value;
                                    orderTotal += nowOrder.Total;
                                    break;
                                case DatabaseConstants.PromotionFlag.TPR_PROGRAM_TYPE_WEIGHT:
                                    orderQuantity += weight;
                                    orderTotal += nowOrder.Total;
                                    break;
                                default:
                                    break;
                            }
                            for (int i = 0; i < orderList.size(); i++) {
                                if (orderList.get(i).skuId == nowSku.skuId) {
                                    SKU sku = sKUDao.getSKUByID(nowSku.skuId);
                                    int qty = orderList.get(i).Carton * orderList.get(i).cartonPcsRatio + orderList.get(i).Piece - (int) (quantity);
                                    if (qty < 0)
                                        qty = 0;
                                    int ctnNumber = qty / sku.cartonPcsRatio;
                                    int pcsNumber = qty % sku.cartonPcsRatio;
                                    orderList.get(i).Carton = ctnNumber;
                                    orderList.get(i).Piece = pcsNumber;
                                }
                            }
                        }

                        // Slab checking
                        if (orderQuantity > 0) {
                            ArrayList<Slab> slabList = getTradePromotionSlabs(nowTpr.ID, orderQuantity);

                            //Log.d(TAG, "Promotion basis:" + nowTpr.basis);
                            //Log.d(TAG, "Slab list:" + orderQuantity + ", " + slabList.size());
                            boolean isPercentageTPcalculated = false;

                            for (Slab nowSlab : slabList) {
                                TPGiven given = new TPGiven();
                                given.tpId = nowTpr.ID;
                                given.slabId = nowSlab.slabId;
                                given.bonusType = nowSlab.slabType;
                                given.soldQuantity = (int) orderQuantity;

                                //Log.d(TAG, "Slab list:" + nowSlab.forEach);
                                double bonus = 0.00;
                                if (nowSlab.forEach > 0) {
                                    if (nowSlab.DiscountType == 1) {
                                        if (nowSlab.minQty <= orderQuantity && !isPercentageTPcalculated) {
                                            bonus = (orderTotal * nowSlab.bonusQty) / nowSlab.forEach;
                                            isPercentageTPcalculated = true;
                                        } else {
                                            continue;
                                        }
                                    } else {
                                        bonus = nowSlab.bonusQty * (int) (orderQuantity / nowSlab.forEach);
                                    }
                                } else {
                                    bonus = nowSlab.bonusQty;
                                }
                                if (nowSlab.slabType == Slab.DISCOUNT)
                                    nowSlab.bonusQty = 0;

                                if (nowSlab.slabType == Slab.FREE_PRODUCT && nowSlab.minQty <= orderQuantity && nowSlab.forEach <= orderQuantity) {
                                    SKU sku = sKUDao.getSKUByID(nowSlab.freeSKU_ID);
                                    nowSlab.skuTitle = sku.banglaName == null || String.valueOf(sku.banglaName).isEmpty() || String.valueOf(sku.banglaName).equals("null") ? sku.title : sku.banglaName;

                                    freeOrDiscount.freeItemList.add(new FreeItem(nowSlab.freeSKU_ID, nowSlab.skuTitle, bonus, nowSlab.giftItemID)); //previously bonus was cast to int. changed due to perfetti broken free sku qty on 16 Nov 2017
                                    given.freeQty = bonus; //previously bonus was cast to int. changed due to perfetti broken free sku qty on 16 Nov 2017

                                    given.freeSKU_ID = nowSlab.freeSKU_ID;
                                    given.giftItem = "";
                                    given.discount = 0;
                                    freeOrDiscount.tpGivens.add(given);
                                } else if (nowSlab.slabType == Slab.GIFT_ITEM && nowSlab.minQty <= orderQuantity) {
                                    //&& nowSlab.bonusQty <= orderQuantity
                                    String giftItemName = "";
                                    if (nowSlab.giftItemBanglaName == null || String.valueOf(nowSlab.giftItemBanglaName).isEmpty() || String.valueOf(nowSlab.giftItemBanglaName).equals("null"))
                                        giftItemName = nowSlab.giftItem;
                                    else
                                        giftItemName = nowSlab.giftItemBanglaName;

                                    freeOrDiscount.freeItemList.add(new FreeItem(-1, giftItemName, (int) bonus, nowSlab.giftItemID));

                                    given.freeSKU_ID = -1;
                                    given.freeQty = (int) bonus;
                                    given.giftItem = nowSlab.giftItem;
                                    given.giftItemBanglaName = nowSlab.giftItemBanglaName;
                                    given.giftItemID = nowSlab.giftItemID;
                                    given.discount = 0;
                                    freeOrDiscount.tpGivens.add(given);
                                } else if (nowSlab.slabType == Slab.DISCOUNT && nowSlab.minQty <= orderQuantity && nowSlab.bonusQty <= orderQuantity) {

                                    // TODO show it or not?
                                    // freeOrDiscount.freeItemList.add(new FreeItem(nowSlab.description, bonus));
                                    freeOrDiscount.discount += bonus;

                                    given.freeSKU_ID = -1;
                                    given.freeQty = 0;
                                    given.giftItem = "";
                                    given.discount = bonus; //This was cast as (int) don't know why. now just removing the cast
                                    freeOrDiscount.tpGivens.add(given);
                                }

                                /***********************Changed by Abrar on 13 Sep 2017*********************/
                                //orderQuantity = orderQuantity * nowSlab.bonusQty % (double) nowSlab.forEach;
                                orderQuantity = orderQuantity % (double) nowSlab.forEach; //here orderQuantity is rem qty.
                            }
                        }
                        if (orderQuantity > 0) {
                            if (nowTpr.skus.size() == 1) {
                                for (int i = 0; i < orderList.size(); i++) {
                                    if (orderList.get(i).skuId == nowTpr.skus.get(0).skuId) {
                                        SKU sku = sKUDao.getSKUByID(skuID); //DatabaseQueryUtil.getSku(context, skuID);
                                        //Modified by abrar for devided by 0 error
                                        int ctnNumber = 0;
                                        int pcsNumber = 0;
                                        if (sku.cartonPcsRatio > 0) {
                                            ctnNumber = (int) orderQuantity / sku.cartonPcsRatio;
                                            pcsNumber = (int) orderQuantity % sku.cartonPcsRatio;
                                        }
                                        orderList.get(i).Carton = ctnNumber;
                                        orderList.get(i).Piece = pcsNumber;
                                    }

                                }
                            }
                        }
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return freeOrDiscount;
        });
    }

    public ArrayList<Tpr> getSPSkuByTpID(int tprID) {
        ArrayList<Tpr> returnTprList = new ArrayList<>();

        try {

            Cursor cursor = tpSkuDao.getSPSkuByTpID(tprID);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Tpr returnTpr = new Tpr();
                        returnTpr.sku = new SKU();
                        returnTpr.tprId = cursor
                                .getInt(cursor
                                        .getColumnIndex("TPID"));
                        returnTpr.sku.skuId = cursor
                                .getInt(cursor
                                        .getColumnIndex("skuId"));
                        returnTprList.add(returnTpr);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }

            if (cursor != null)
                cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnTprList;
    }

    private ArrayList<OrderItem> UpdatedOrderList(TradePromotion nowTpr, Slab nowSlab, int skuID, ArrayList<OrderItem> orderList, double orderQuantity) {

        switch (nowTpr.Unit) {
            case DatabaseConstants.PromotionFlag.TPR_PROGRAM_TYPE_QUANTITY_2:
                for (int i = 0; i < orderList.size(); i++) {
                    if (orderList.get(i).skuId == skuID) {
                        int orderQty = orderList.get(i).Carton * orderList.get(i).cartonPcsRatio + orderList.get(i).Piece;
                        orderQty = orderQty - (int) orderQuantity * nowSlab.forEach;
                        if (orderQty < 0)
                            orderQty = 0;
                        SKU sku = sKUDao.getSKUByID(skuID);
                        int ctnNumber = orderQty / sku.cartonPcsRatio;
                        int pcsNumber = orderQty % sku.cartonPcsRatio;
                        orderList.get(i).Carton = ctnNumber;
                        orderList.get(i).Piece = pcsNumber;
                    }
                } //Math.floor(sticks / nowSku.packSize);
                break;
            case DatabaseConstants.PromotionFlag.TPR_PROGRAM_TYPE_VALUE:

                for (int i = 0; i < orderList.size(); i++) {
                    if (orderList.get(i).skuId == skuID) {
                        double orderTotal = orderList.get(i).Total;
                        orderTotal = orderTotal - nowSlab.bonusQty;
                        if (orderTotal < 0)
                            orderTotal = 0;
                        orderList.get(i).Total = orderTotal;
                    }
                }//Math.floor(value / nowSku.packSize);
                break;
            case DatabaseConstants.PromotionFlag.TPR_PROGRAM_TYPE_WEIGHT:

                for (int i = 0; i < orderList.size(); i++) {
                    if (orderList.get(i).skuId == skuID) {
                        SKU sku = sKUDao.getSKUByID(skuID);
                        int orderQty = orderList.get(i).Carton * orderList.get(i).cartonPcsRatio + orderList.get(i).Piece;
                        double weight = orderQty * sku.packSize;
                        weight = weight - (int) orderQuantity * nowSlab.forEach * sku.packSize;
                        orderQty = (int) (weight / sku.packSize);
                        if (orderQty < 0)
                            orderQty = 0;

                        int ctnNumber = orderQty / sku.cartonPcsRatio;
                        int pcsNumber = orderQty % sku.cartonPcsRatio;
                        orderList.get(i).Carton = ctnNumber;
                        orderList.get(i).Piece = pcsNumber;
                    }
                } //Math.floor(weight / nowSku.packSize);
                break;
            default:
                break;
        }

        return orderList;
    }

    private ArrayList<Slab> getTradePromotionSlabs(int tpId) {
        ArrayList<Slab> returnSlabList = new ArrayList<Slab>();
        try {


            Cursor cursor = spSlabBonusesDao.getTradePromotionSlabs(tpId);


            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Slab now = new Slab();

                        int pos = 0;
                        now.slabId = cursor.getInt(pos++);
                        now.tprId = cursor.getInt(pos++);
                        now.minQty = cursor.getInt(pos++);
                        now.slabType = cursor.getInt(pos++);
                        now.forEach = cursor.getInt(pos++);
                        now.bonusQty = cursor.getDouble(pos++);
                        now.freeSKU_ID = cursor.getInt(pos++);
                        now.giftItem = cursor.getString(pos++);
                        now.giftItemBanglaName = cursor.getString(pos++);
                        now.DiscountType = cursor.getInt(pos++);
                        now.giftItemID = cursor.getInt(pos++);
                        //now = getSlab(context, now.tprId, now.slabId);
                        returnSlabList.add(now);

                        /****************************Multiple TP Consider************************************/
                        //TODO CHECK IT
                        /*There is a break was before. But due to NDP TP policy we need to consider TP with multiple slab. So we are ommitting the Break
                         * Please make sure to double check with all the scenario to remove break condition */

                        //break;
                        /****************************************************************/

                    } while (cursor.moveToNext());
                }
                cursor.close();
            }

            if (cursor != null)
                cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnSlabList;
    }

    private ArrayList<Slab> getTradePromotionSlabs(int tpId, double quantity) {
        ArrayList<Slab> returnSlabList = new ArrayList<Slab>();
        try {

            Cursor cursor = spSlabBonusesDao.getTradePromotionSlabs(tpId, quantity);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Slab now = new Slab();

                        int pos = 0;
                        now.slabId = cursor.getInt(pos++);
                        now.tprId = cursor.getInt(pos++);
                        now.minQty = cursor.getInt(pos++);
                        now.slabType = cursor.getInt(pos++);
                        now.forEach = cursor.getInt(pos++);
                        now.bonusQty = cursor.getDouble(pos++);
                        now.freeSKU_ID = cursor.getInt(pos++);
                        now.giftItem = cursor.getString(pos++);
                        now.giftItemBanglaName = cursor.getString(pos++);
                        now.DiscountType = cursor.getInt(pos++);
                        now.giftItemID = cursor.getInt(pos++);
                        //now = getSlab(context, now.tprId, now.slabId);
                        returnSlabList.add(now);

                        /****************************Multiple TP Consider************************************/
                        //TODO CHECK IT
                        /*There is a break was before. But due to NDP TP policy we need to consider TP with multiple slab. So we are ommitting the Break
                         * Please make sure to double check with all the scenario to remove break condition */

                        //break;
                        /****************************************************************/

                    } while (cursor.moveToNext());
                }
                cursor.close();
            }

            if (cursor != null)
                cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnSlabList;
    }


    private OrderItem getOrderTotalBySkuId(SKU skuItem, ArrayList<OrderItem> orderList) {
        OrderItem returnOrder = new OrderItem();
        returnOrder.skuId = skuItem.skuId;
        returnOrder.Carton = 0;
        returnOrder.Total = 0;
        returnOrder.Piece = 0;

        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).skuId == skuItem.skuId) {
                returnOrder.Carton += BijoyHelper.getCtn(orderList.get(i).orderQty, orderList.get(i).cartonPcsRatio);
                returnOrder.Piece += BijoyHelper.getPcs(orderList.get(i).orderQty, orderList.get(i).cartonPcsRatio);
                returnOrder.Total += orderList.get(i).orderQty * orderList.get(i).pcsRate;
                returnOrder.orderQty += orderList.get(i).orderQty;
            }
        }
        return returnOrder;
    }

    private SPCustomer GetSPCustomer(int customerID, int spID) {

        SPCustomer item = new SPCustomer();

        try {

            Cursor cursor = tpCustomersDao.GetSPCustomer(spID, customerID);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    item.SPID = cursor.getInt(cursor.getColumnIndex("SPID"));
                    item.CustomerID = cursor.getInt(cursor.getColumnIndex("CustomerID"));
                    item.Remaining = cursor.getInt(cursor.getColumnIndex("Remaining"));
                }
            }

            if (cursor != null)
                cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return item;
    }


    private boolean IsCustomerEligibleForTradePromotion(int customerID, int spID) {
        int count = 0;
        try {
            Cursor cursor = tradePromotionsDao.IsCustomerEligibleForTradePromotion(spID, customerID);

            if (cursor != null) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
            if (cursor != null)
                cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count > 0;
    }

    private ArrayList<TradePromotion> getTradePromotionOffers(int channelId) {

        ArrayList<TradePromotion> promotions = new ArrayList<>();
        try {

            Cursor cursor = tradePromotionsDao.getTradePromotionOffers(channelId);

            if (cursor != null) {
                cursor.moveToFirst();

                ArrayMap<Integer, TradePromotion> promotionIds = new ArrayMap<>();

                while (!cursor.isAfterLast()) {
                    int promotionId = cursor.getInt(0);

                    SKU sku = new SKU();
                    sku.skuId = cursor.getInt(7);
                    sku.title = cursor.getString(8);
                    sku.packSize = cursor.getInt(9);
                    sku.ratio = cursor.getInt(10);


                    if (promotionIds.containsKey(promotionId)) {
                        TradePromotion promotion = promotionIds.get(promotionId);
                        promotion.skus.add(sku);
                    } else {
                        TradePromotion promotion = new TradePromotion();
                        promotion.ID = promotionId;
                        promotion.Name = cursor.getString(1);
                        promotion.NameBangla = cursor.getString(2);
                        promotion.Basis = cursor.getInt(3);
                        promotion.Unit = cursor.getInt(4);
                        promotion.Type = cursor.getInt(5);
                        promotion.IsCustSpecific = cursor.getInt(6);//cursor.getInt(6) > 0 ? true : false;
                        promotion.IsRestricted = cursor.getInt(11); //cursor.getInt(11) > 0 ? true : false;
                        promotion.skus = new ArrayList<>();
                        promotion.skus.add(sku);

                        promotions.add(promotion);

                        promotionIds.put(promotionId, promotion);
                    }

                    cursor.moveToNext();
                }
            }

            if (cursor != null)
                cursor.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return promotions;
    }


    //endregion

    //region DeliveryOrderList Activity

    public Observable<List<Outlet>> getDeliveryManOrders(int status) {
        return Observable.fromCallable(() -> {
            List<Outlet> outlets = outletDao.getAllByStatus(status);
            List<Order> orderDetails = orderDetailsDao.getAll();

            for (Outlet oItem : outlets) {
                for (Order orderDetailItem : orderDetails) {
                    if (oItem.getCustomerID() == orderDetailItem.getOutletID()) {
                        oItem.outletDue = (orderDetailItem.getOrderNetValue() - orderDetailItem.getCashCollection());
                    }
                }
            }

            return outlets;
        });
    }

    public Observable<List<Outlet>> getAllOutlets() {
        return Observable.fromCallable(() -> outletDao.getAll());
    }

    public Observable<SRBasic> getSRInfo() {
        return Observable.fromCallable(() -> {
            SRBasic basic = userDao.getUser();
            Section section = sectionDao.getSRSectionInfo(basic.getSectionID(), basic.getRouteID());
            basic.setRouteName(section.routeBanglaName);

            return basic;
        });
    }

    public int getAllOutletsCount() {
        return outletDao.getOutletCount();
    }

    public int getOutletCountByStatus(int status) {
        return outletDao.getOutletCountByStatus(status);
    }

    public LiveData<List<SalesOrderPromotion>> getPromotionsByOrderID(int orderID) {
        return salesOrderPromotionDao.getPromotionsByOrderID(orderID);
    }

    public Observable<List<SalesOrderPromotion>> getPromotionsByType(int orderID, int promoType) {
        return Observable.fromCallable(() -> salesOrderPromotionDao.getPromotionsByType(orderID, promoType));
    }
    //endregion

    public Observable<BijoyInfo> doPrepareUploadData() {
        return Observable.fromCallable(() -> {
            BijoyInfo item = null;
            int orderedOutletCount = orderDetailsDao.getOrderedOutletCount(); //invoice count
            int outletCount = outletDao.getOutletCount();
            //TODO: PLEASE CHANGE AFTER TESTING
            //orderedOutletCount == outletCount
            if (true) {
                item = new BijoyInfo();

                List<Order> orders = orderDetailsDao.getAll();
                List<OrderItem> orderItems = orderItemsDao.getAll();
                List<Challan> challans = challanDao.getChallanStockItems();
                SRBasic srInfo = userDao.getUser();

                //SET UPLOADABLE ITEMS
                item.orderMaster = new ArrayList<>(orders);
                item.orderItems = new ArrayList<>(orderItems);
                item.challanItems = new ArrayList<>(challans);
                item.srBasic = srInfo;
            }

            return item;
        });
    }
}
