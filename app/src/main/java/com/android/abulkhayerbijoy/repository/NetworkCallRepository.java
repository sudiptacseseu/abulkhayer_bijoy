package com.android.abulkhayerbijoy.repository;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.abulkhayerbijoy.activity.ChallanActivity;
import com.android.abulkhayerbijoy.activity.LoginActivity;
import com.android.abulkhayerbijoy.activity.MainActivity;
import com.android.abulkhayerbijoy.model.ChallanDetail;
import com.android.abulkhayerbijoy.model.SRBasic;
import com.android.abulkhayerbijoy.model.response.BijoyInfo;
import com.android.abulkhayerbijoy.model.response.BijoyTransactionResponse;
import com.android.abulkhayerbijoy.network.NetworkService;
import com.android.abulkhayerbijoy.startup.Startup;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;


public class NetworkCallRepository {

    private DatabaseCallRepository dbCallRepository;
    private Context context;
    public static int SUCCESS = 1;
    public static int FAILED = 0;

    public NetworkCallRepository(Application application) {
        context = application;
        dbCallRepository = new DatabaseCallRepository(application);
    }

    public Observable<Response<String>> UserLogin(String userID, String password) {
        return new NetworkService().UserLogin(userID, password);
    }

    public Observable<Response<String>> GetDSRBasicInfos(String userID, String password, int srID, int systemID) {
        return new NetworkService().GetDSRBasicInfos(userID, password, srID, systemID);
    }

    public Observable<Response<String>> GetSKUs(String userID, String password, int systemID, int deliveryGroupID) {
        return new NetworkService().GetSKUs(userID, password, systemID, deliveryGroupID);
    }

    public Observable<Response<String>> GetSections(String userID, String password, int srID, int systemID) {
        return new NetworkService().GetSections(userID, password, srID, systemID);
    }

    public Observable<Response<String>> GetOutletInfos(String userID, String password, int srID, int systemID) {
        return new NetworkService().GetOutletInfos(userID, password, srID, systemID);
    }

    //region Promotion Block

    public Observable<Response<String>> GetSalesPromotions(String userID, String password, int systemID, String operationDate) {
        return new NetworkService().GetSalesPromotions(userID, password, systemID, operationDate);
    }

    public Observable<Response<String>> GetSPSKUChannel(String userID, String password, int systemID, String operationDate) {
        return new NetworkService()
                .GetSPSKUChannel(userID, password, systemID, operationDate).subscribeOn(Schedulers.io());
    }

    public Observable<Response<String>> GetSPSlabs(String userID, String password, int systemID, String operationDate) {
        return new NetworkService()
                .GetSPSlabs(userID, password, systemID, operationDate);
    }

    public Observable<Response<String>> GetSPBonuses(String userID, String password, int systemID, String operationDate) {
        return new NetworkService()
                .GetSPBonuses(userID, password, systemID, operationDate);
    }

    public Observable<Response<String>> GetPromotionalInfo(String userID, String password, int srID, int systemID) {
        return new NetworkService()
                .GetPromotionalInfo(userID, password, srID, systemID);
    }

    //endregion

    public void UploadBijoyInformation() {

        dbCallRepository
                .doPrepareUploadData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BijoyInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BijoyInfo bijoyInfo) {

                        //region CALL Network For Upload

                        if (bijoyInfo != null) {
                            JSONObject paramObject = new JSONObject();
                            try {
                                paramObject.put("OrderMasterData", bijoyInfo.orderMaster);
                                paramObject.put("OrderItemData", bijoyInfo.orderItems);
                                paramObject.put("ChallanData", bijoyInfo.challanItems);
                                paramObject.put("SRBasic", bijoyInfo.srBasic);

                                new NetworkService()
                                        .UploadBijoyInformation(paramObject)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<BijoyTransactionResponse>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(BijoyTransactionResponse response) {
                                                if (response.responseCode == HttpURLConnection.HTTP_OK) {

                                                    //region Clear Info from database

                                                    //clear All Tables
                                                    //dbCallRepository.clearAllTables();
                                                    //endregion

                                                    //clear Challan & Order info
                                                    dbCallRepository
                                                            .deleteChallanNOrderInfo()
                                                            .subscribeOn(Schedulers.io())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .subscribe(new CompletableObserver() {
                                                                @Override
                                                                public void onSubscribe(Disposable d) {

                                                                }

                                                                @Override
                                                                public void onComplete() {
                                                                    Toasty.info(context, "নগদগ্রহণ সফলভাবে সম্পন্ন হয়েছে!", Toast.LENGTH_SHORT).show();
                                                                }

                                                                @Override
                                                                public void onError(Throwable e) {
                                                                    e.printStackTrace();
                                                                    if (e.getMessage() != null)
                                                                        Toasty.error(Startup.getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
                                                                }
                                                            });
                                                } else {
                                                    Toasty.error(context, "Error: " + response.message, Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Toasty.error(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();
                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });

                            } catch (JSONException e) {
                                e.printStackTrace();
                                if (e.getMessage() != null)
                                    Toasty.error(context, "Error: " + e.getMessage()).show();
                            }

                        } else {
                            Toasty.info(context, "দয়া করে সকল দোকান ভিজিট করুন!", Toast.LENGTH_SHORT).show();
                        }
                        //endregion
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toasty.error(context, "Error: " + e.getMessage()).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void challanItemsConfirmation(ArrayList<ChallanDetail> challanItems, SRBasic srBasic) {

        JSONObject paramObject = new JSONObject();

        //region CALL Network For ChallanConfirmation
        try {

            paramObject.put("ChalllanItems", challanItems);
            paramObject.put("SRInfo", srBasic);

            new NetworkService()
                    .ChallanConfirmation(paramObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BijoyTransactionResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BijoyTransactionResponse response) {

                            if (response.responseCode == HttpURLConnection.HTTP_OK) {
                                try {

                                    ArrayList<ChallanDetail> cItems = new ArrayList<>();

                                    for (ChallanDetail citem : challanItems) {
                                        citem.challanID = Integer.parseInt(response.data);
                                        cItems.add(citem);
                                    }

                                    //region Save Challan
                                    dbCallRepository
                                            .insertChallanDetails(cItems)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new CompletableObserver() {
                                                @Override
                                                public void onSubscribe(Disposable d) {

                                                }

                                                @Override
                                                public void onComplete() {
                                                    Toasty.info(Startup.getContext(), "চালান সফলভাবে তৈরি হয়েছে!").show();
                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    e.printStackTrace();
                                                    if (e.getMessage() != null)
                                                        Toasty.error(Startup.getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
                                                }
                                            });//endregion


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            else if (response.responseCode == HTTP_BAD_REQUEST){
                                Toasty.error(Startup.getContext(), "পর্যাপ্ত এস.কে.ইউ নেই! "+ response.message, Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });


        } catch (JSONException e) {
            e.printStackTrace();
        }
        //endregion
    }
}
