package com.android.abulkhayerbijoy.activity;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.abulkhayerbijoy.R;
import com.android.abulkhayerbijoy.databinding.ActivityLoginBinding;
import com.android.abulkhayerbijoy.model.OutletDetail;
import com.android.abulkhayerbijoy.model.SKUDetail;
import com.android.abulkhayerbijoy.model.SRBasic;
import com.android.abulkhayerbijoy.model.SectionDetail;
import com.android.abulkhayerbijoy.model.promotion.ResponseSPSlab;
import com.android.abulkhayerbijoy.model.promotion.SPSKUChannel;
import com.android.abulkhayerbijoy.model.promotion.Slab;
import com.android.abulkhayerbijoy.model.promotion.Tpr;
import com.android.abulkhayerbijoy.network.NetworkService;
import com.android.abulkhayerbijoy.repository.DatabaseCallRepository;
import com.android.abulkhayerbijoy.startup.Startup;
import com.android.abulkhayerbijoy.utils.SystemHelper;
import com.android.abulkhayerbijoy.utils.Util;
import com.android.abulkhayerbijoy.viewmodel.LoginActivityViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import es.dmoral.toasty.Toasty;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    SRBasic userObject = new SRBasic();
    private LoginActivityViewModel mViewModel;
    private Context context;
    private DatabaseCallRepository dbCallRepository;
    private int outletCount = 0;
    public static int SUCCESS = 1;
    public static int FAILED = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.setObj(userObject);
        context = LoginActivity.this;

        dbCallRepository = new DatabaseCallRepository(Startup.getInstance());
        mViewModel = ViewModelProviders.of(LoginActivity.this).get(LoginActivityViewModel.class);

        setUserIDIfLoggedIn();

        binding.buttonLogin.setOnClickListener((View v) -> {

            if (binding.editTextUserId.getText().toString().isEmpty() || binding.editTextPassword.getText().toString().isEmpty()) {
                Toasty.error(LoginActivity.this, "দয়া করে সঠিক ইউজার এবং পাসওয়ার্ড প্রদান করুন!").show();
            } else {
                if (SystemHelper.isInternetOn(LoginActivity.this)) {
                    showProgressView();
                    doLoginAction(binding.editTextUserId.getText().toString(), binding.editTextPassword.getText().toString());
                } else {
                    Toasty.error(LoginActivity.this, "ইন্টারনেটে যোগাযোগ সম্ভব হচ্ছে না!").show();
                }
            }
        });
    }

    private void setUserIDIfLoggedIn() {
        SRBasic oUser = SystemHelper.getUserInfOnSharedPreference();
        if (oUser != null) {
            if (oUser.getSectionID() != null)
                goToMainActivity();
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void doLoginAction(String userID, String password) {
        try {

            mViewModel
                    .UserLogin(userID, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<String>>() {
                        @Override
                        public void onSubscribe(Disposable dispose) {
                        }

                        @Override
                        public void onNext(Response<String> result) {
                            String res = result.body();

                            if (!Util.isJSONValid(res)) {
                                Toasty.error(context, res, Toast.LENGTH_SHORT, true).show();
                                sendFailNotification(res);
                            } else {
                                try {
                                    JSONArray jsonMainNode = new JSONArray(res);
                                    String data = jsonMainNode.getString(0);
                                    SRBasic item = new Gson().fromJson(data, new TypeToken<SRBasic>() {
                                    }.getType());

                                    if (item != null) {

                                        SystemHelper.setUserInfoOnSharedPreference(item);

                                        dbCallRepository
                                                .insertSRBasic(item)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new CompletableObserver() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {

                                                    }

                                                    @Override
                                                    public void onComplete() {
                                                        //Getting Additional Info After Authorization
                                                        SRBasic oUser = SystemHelper.getUserInfOnSharedPreference();
                                                        getDSRBasicInfos(oUser);
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        sendFailNotification(e.getMessage());
                                                        Log.v("Response", "onError:: " + e);
                                                        Toasty.error(context, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                                                    }
                                                });

                                    }

                                } catch (JSONException e) {
                                    sendFailNotification(e.getMessage());
                                    Log.v("Response", "onError:: " + e);
                                    Toasty.error(context, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            sendFailNotification(e.getMessage());
                            String error = e.getMessage() + " ::UserLogin";
                            Log.v("Response", "onError:: " + error);
                            Toasty.error(context, error, Toast.LENGTH_SHORT, true).show();
                        }

                        @Override
                        public void onComplete() {
                        }
                    });

        } catch (Exception ex) {
            sendFailNotification(ex.getMessage());
            ex.printStackTrace();
        }

    }

    //region PROGRESS-VIEW
    AlertDialog dialog = null;

    public void showProgressView() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true); // if you want user to wait for some process to finish
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();
        if (!dialog.isShowing()) {
            dialog.show(); // to show this dialog
        }
    }

    public void closeProgressView() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
    //endregion

    //region API Calls

    private void getDSRBasicInfos(SRBasic oUser) {

        mViewModel
                .GetDSRBasicInfos(oUser.getUserID(), oUser.getPassword(), oUser.getSrID(), oUser.getSystemID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<String> result) {
                        String res = result.body();

                        try {
                            JSONArray jsonMainNode = new JSONArray(res);
                            String data = jsonMainNode.getString(0);
                            SRBasic item = new Gson().fromJson(data, new TypeToken<SRBasic>() {
                            }.getType());

                            //region Update Info on mem
                            SRBasic user = SystemHelper.getUserInfOnSharedPreference();
                            user.setName(item.getName());
                            user.setBanglaName(item.getBanglaName());
                            user.setSectionID(item.getSectionID());
                            user.setDeliveryGroupID(item.getDeliveryGroupID());
                            user.setRouteID(item.getRouteID());
                            user.setDistributorName(item.getDistributorName());
                            user.setDistributorBanglaName(item.getDistributorBanglaName());
                            user.setVisitDate(item.getVisitDate());
                            SystemHelper.setUserInfoOnSharedPreference(user);
                            //endregion

                            //closeProgressView();

                            if (item != null) {

                                dbCallRepository
                                        .updateSRBasicInfo(item)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new CompletableObserver() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onComplete() {
                                                //Getting Data After Getting All Necessary Info OF DSR
                                                doGetData();
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Toasty.error(context, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                                                sendFailNotification(e.getMessage());
                                            }
                                        });

                            }

                        } catch (Exception e) {
                            Toasty.error(context, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                            sendFailNotification(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toasty.error(context, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                        sendFailNotification(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void doGetData() {

        SRBasic oUser = SystemHelper.getUserInfOnSharedPreference();

        try {
            //call for SKU, Outlet, Section & Promotional data
            res = 0;
            getSKUs(oUser);
            getOutlets(oUser);
            getSections(oUser);
            getPromotionData(oUser);
        } catch (Exception ex) {
            ex.printStackTrace();
            sendFailNotification(ex.getMessage());
        }

    }

    private void getSKUs(SRBasic oUser) {

        mViewModel
                .GetSKUs(oUser.getUserID(), oUser.getPassword(), oUser.getSystemID(), oUser.getDeliveryGroupID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable dispose) {
                    }

                    @Override
                    public void onNext(Response<String> result) {
                        String res = result.body();
                        try {
                            List<SKUDetail> items = new ArrayList<>();

                            JSONArray jsonMainNode = new JSONArray(res);
                            for (int i = 0; i < jsonMainNode.length(); i++) {
                                String data = jsonMainNode.getString(i);
                                SKUDetail item = new Gson().fromJson(data, new TypeToken<SKUDetail>() {
                                }.getType());
                                items.add(item);
                            }

                            if (items != null && items.size() > 0) {

                                dbCallRepository
                                        .insertSKUDetails(items)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new CompletableObserver() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onComplete() {
                                                networkCallback.sendEmptyMessage(SUCCESS);

                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Toasty.error(context, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                                                networkCallback.sendEmptyMessage(FAILED);
                                            }
                                        });
                            }

                        } catch (JSONException e) {
                            Toasty.error(context, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                            networkCallback.sendEmptyMessage(FAILED);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String error = e.getMessage() + " ::UserLogin";
                        Log.v("Response", "onError:: " + error);
                        Toasty.error(context, error, Toast.LENGTH_SHORT, true).show();
                        networkCallback.sendEmptyMessage(FAILED);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void getSections(SRBasic oUser) {

        mViewModel
                .GetSections(oUser.getUserID(), oUser.getPassword(), oUser.getSrID(), oUser.getSystemID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable dispose) {
                    }

                    @Override
                    public void onNext(Response<String> result) {
                        String res = result.body();
                        try {
                            List<SectionDetail> items = new ArrayList<>();

                            JSONArray jsonMainNode = new JSONArray(res);
                            for (int i = 0; i < jsonMainNode.length(); i++) {
                                String data = jsonMainNode.getString(i);
                                SectionDetail item = new Gson().fromJson(data, new TypeToken<SectionDetail>() {
                                }.getType());
                                items.add(item);
                            }

                            if (items != null && items.size() > 0) {

                                dbCallRepository
                                        .insertSectionDetails(items)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new CompletableObserver() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onComplete() {
                                                networkCallback.sendEmptyMessage(SUCCESS);
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Toasty.error(context, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                                                networkCallback.sendEmptyMessage(FAILED);
                                            }
                                        });
                            }

                        } catch (JSONException e) {
                            Toasty.error(context, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                            networkCallback.sendEmptyMessage(FAILED);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String error = e.getMessage() + " ::UserLogin";
                        Log.v("Response", "onError:: " + error);
                        Toasty.error(context, error, Toast.LENGTH_SHORT, true).show();
                        networkCallback.sendEmptyMessage(FAILED);
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    private void getOutlets(SRBasic oUser) {

        mViewModel
                .GetOutletInfos(oUser.getUserID(), oUser.getPassword(), oUser.getSrID(), oUser.getSystemID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable dispose) {
                    }

                    @Override
                    public void onNext(Response<String> result) {
                        String res = result.body();
                        try {
                            if (result.code() == HttpsURLConnection.HTTP_OK) {
                                List<OutletDetail> items = new ArrayList<>();

                                JSONArray jsonMainNode = new JSONArray(res);
                                for (int i = 0; i < jsonMainNode.length(); i++) {
                                    String data = jsonMainNode.getString(i);
                                    OutletDetail item = new Gson().fromJson(data, new TypeToken<OutletDetail>() {
                                    }.getType());
                                    items.add(item);
                                }

                                if (items != null && items.size() > 0) {

                                    dbCallRepository
                                            .insertOutletDetails(items)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new CompletableObserver() {
                                                @Override
                                                public void onSubscribe(Disposable d) {

                                                }

                                                @Override
                                                public void onComplete() {
                                                    networkCallback.sendEmptyMessage(SUCCESS);
                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    Toasty.error(context, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                                                    networkCallback.sendEmptyMessage(FAILED);
                                                }
                                            });
                                }
                            } else {
                                Toasty.error(context, res, Toast.LENGTH_SHORT, true).show();
                                networkCallback.sendEmptyMessage(FAILED);
                            }

                        } catch (JSONException e) {
                            Toasty.error(context, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                            networkCallback.sendEmptyMessage(FAILED);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String error = e.getMessage() + " ::UserLogin";
                        Log.v("Response", "onError:: " + error);
                        Toasty.error(context, error, Toast.LENGTH_SHORT, true).show();
                        networkCallback.sendEmptyMessage(FAILED);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void getPromotionData(SRBasic oUser) {
        GetSalesPromotions(oUser.getUserID(), oUser.getPassword(), oUser.getSystemID(), oUser.getVisitDate());
        GetSPSKUChannel(oUser.getUserID(), oUser.getPassword(), oUser.getSystemID(), oUser.getVisitDate());
        GetSPSlabs(oUser.getUserID(), oUser.getPassword(), oUser.getSystemID(), oUser.getVisitDate());
        GetSPBonuses(oUser.getUserID(), oUser.getPassword(), oUser.getSystemID(), oUser.getVisitDate());
        GetPromotionalInfo(oUser.getUserID(), oUser.getPassword(), oUser.getSrID(), oUser.getSystemID());
    }

    //endregion

    //region Promotion Requests
    public void GetSalesPromotions(String userID, String password, int systemID, String operationDate) {

        mViewModel
                .GetSalesPromotions(userID, password, systemID, operationDate).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<String> result) {
                        if (result.code() == HttpsURLConnection.HTTP_OK) {
                            List<Tpr> items = new Gson().fromJson(result.body(), new TypeToken<List<Tpr>>() {
                            }.getType());

                            if (items != null) {
                                ArrayList<Tpr> fItems = new ArrayList<>();
                                for (Tpr oItem : items) {
                                    oItem.programmType = Tpr.ProgramType_TPRTypeCombination;
                                    oItem.isRestricted = oItem.isRestrict ? 1 : 0;
                                    fItems.add(oItem);
                                }

                                dbCallRepository
                                        .deleteNinsertPromotions(fItems)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new CompletableObserver() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onComplete() {
                                                Log.v("Response", "GetSalesPromotions");
                                                networkCallback.sendEmptyMessage(SUCCESS);
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                String error = e.getMessage() + " ::GetSalesPromotions";
                                                Log.v("Response", "onError:: " + error);
                                                e.printStackTrace();

                                                sendFailNotification(error);
                                            }
                                        });
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        sendFailNotification(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void GetSPSKUChannel(String userID, String password, int systemID, String operationDate) {

        new NetworkService()
                .GetSPSKUChannel(userID, password, systemID, operationDate).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<String> result) {

                        if (result.code() == HttpsURLConnection.HTTP_OK) {

                            List<SPSKUChannel> items = new Gson().fromJson(result.body(), new TypeToken<List<SPSKUChannel>>() {
                            }.getType());

                            if (items != null) {
                                dbCallRepository
                                        .deleteNinsertSPSKUChannel(new ArrayList<>(items))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new CompletableObserver() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onComplete() {
                                                Log.v("Response", "deleteNinsertSPSKUChannel");
                                                networkCallback.sendEmptyMessage(SUCCESS);
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                String error = e.getMessage() + " ::GetSPSKUChannel";
                                                Log.v("Response", "onError:: " + error);
                                                e.printStackTrace();

                                                sendFailNotification(error);
                                            }
                                        });
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        sendFailNotification(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void GetSPSlabs(String userID, String password, int systemID, String operationDate) {

        new NetworkService()
                .GetSPSlabs(userID, password, systemID, operationDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<String> result) {

                        if (result.code() == HttpsURLConnection.HTTP_OK) {

                            List<ResponseSPSlab> items = new Gson().fromJson(result.body(), new TypeToken<List<ResponseSPSlab>>() {
                            }.getType());

                            if (items != null) {

                                dbCallRepository
                                        .deleteNinsertSPSlabs(new ArrayList<>(items))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new CompletableObserver() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onComplete() {
                                                Log.v("Response", "deleteNinsertSPSKUChannel");
                                                networkCallback.sendEmptyMessage(SUCCESS);
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                String error = e.getMessage() + " ::GetSPSlabs";
                                                Log.v("Response", "onError:: " + error);
                                                e.printStackTrace();

                                                sendFailNotification(error);
                                            }
                                        });
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        sendFailNotification(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void GetSPBonuses(String userID, String password, int systemID, String operationDate) {

        new NetworkService()
                .GetSPBonuses(userID, password, systemID, operationDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<String> result) {

                        if (result.code() == HttpsURLConnection.HTTP_OK) {


                            List<Slab> items = new Gson().fromJson(result.body(), new TypeToken<List<Slab>>() {
                            }.getType());

                            if (items != null) {

                                dbCallRepository
                                        .deleteNinsertSPBonuses(new ArrayList<>(items))
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new CompletableObserver() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onComplete() {
                                                Log.v("Response", "deleteNinsertSPBonuses");
                                                networkCallback.sendEmptyMessage(SUCCESS);
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                String error = e.getMessage() + " ::GetSPBonuses";
                                                Log.v("Response", "onError:: " + error);
                                                e.printStackTrace();
                                                sendFailNotification(error);
                                            }
                                        });
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        sendFailNotification(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void GetPromotionalInfo(String userID, String password, int srID, int systemID) {

        new NetworkService()
                .GetPromotionalInfo(userID, password, srID, systemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<String> result) {
                        if (result.code() == HttpsURLConnection.HTTP_OK) {
                            String resultBody = result.body();

                            dbCallRepository
                                    .insertPromotionInfoFromWeb(resultBody)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new CompletableObserver() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onComplete() {
                                            Log.v("Response", "deleteNinsertSPBonuses");
                                            networkCallback.sendEmptyMessage(SUCCESS);
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            String error = e.getMessage() + " ::GetSPBonuses";
                                            Log.v("Response", "onError:: " + error);
                                            e.printStackTrace();

                                            sendFailNotification(error);
                                        }
                                    });

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        sendFailNotification(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void sendFailNotification(String error) {
        Message msg = Message.obtain();
        msg.obj = error;
        msg.what = FAILED;
        msg.setTarget(networkCallback);
        networkCallback.sendMessage(msg);
    }
    //endregion

    int res = 0;
    @SuppressLint("HandlerLeak")
    Handler networkCallback = new Handler() {
        public void handleMessage(Message msg) {
            res += msg.what;
            if (res >= 8) {
                closeProgressView();
                res = 0;
                goToMainActivity();
            }
            if (msg.what == FAILED) {
                res = 0;
                closeProgressView();
            }
        }
    };
}
