package com.android.abulkhayerbijoy.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.abulkhayerbijoy.R;
import com.android.abulkhayerbijoy.adapter.StoreDetailsListAdapter;
import com.android.abulkhayerbijoy.model.Challan;
import com.android.abulkhayerbijoy.model.Order;
import com.android.abulkhayerbijoy.model.OrderItem;
import com.android.abulkhayerbijoy.model.promotion.FreeOrDiscount;
import com.android.abulkhayerbijoy.repository.DatabaseCallRepository;
import com.android.abulkhayerbijoy.startup.Startup;
import com.android.abulkhayerbijoy.utils.BanglaFontUtil;
import com.android.abulkhayerbijoy.utils.MemoHelper;
import com.android.abulkhayerbijoy.utils.SystemConstants;
import com.android.abulkhayerbijoy.utils.SystemHelper;
import com.android.abulkhayerbijoy.viewmodel.StoreDetailsActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StoreDetailsActivity extends AppCompatActivity {

    private StoreDetailsActivityViewModel mViewModel;
    private Context context;
    private MemoHelper memoInstance;
    private RecyclerView recyclerView;
    private StoreDetailsListAdapter adapter;
    private DatabaseCallRepository dbCallRepository;
    private BanglaFontUtil banglaUtil = null;

    public TextView textViewPreviousCredit, textViewNetVaule, textViewNetCredit, textViewDiscount;
    public Button btnSubmit;
    public EditText editTextCashCollection;
    public String outletName, outletBanglaName;

    Intent intent;
    Bundle bundle;
    int outletID = 0;
    int id = 0;
    double netValue = 0.00;
    double cashCollection = 0.00;
    double previousCashCollection = 0.00;
    double previousCredit = 0.00;
    private int channelID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        banglaUtil = new BanglaFontUtil();

        textViewPreviousCredit = findViewById(R.id.textView_PreviousCredit);
        textViewNetVaule = findViewById(R.id.textView_NetVaule);
        textViewNetCredit = findViewById(R.id.textView_NetCredit);
        editTextCashCollection = findViewById(R.id.editText_CashCollected);
        textViewDiscount = findViewById(R.id.textView_Discount);

        intent = getIntent();
        bundle = intent.getExtras();

        mViewModel = ViewModelProviders.of(StoreDetailsActivity.this).get(StoreDetailsActivityViewModel.class);
        context = StoreDetailsActivity.this;

        memoInstance = MemoHelper.instance();

        dbCallRepository = new DatabaseCallRepository(Startup.getInstance());

        if (bundle != null) {
            outletName = bundle.getString("OutletName");
            outletBanglaName = bundle.getString("OutletBanglaName");
            setTitle(TextUtils.isEmpty(outletBanglaName) ? outletName : outletBanglaName);
            outletID = bundle.getInt("OutletID");
            id = bundle.getInt("ID");
            channelID = bundle.getInt("ChannelID");
        }

        loadChallan(outletID);

        btnSubmit = findViewById(R.id.button_Submit);
        recyclerView = findViewById(R.id.recyclerView_ChallanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textViewPreviousCredit.setText(banglaUtil.NumberToBangla(String.valueOf(previousCredit)));

        editTextCashCollection.setOnClickListener(view -> {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(StoreDetailsActivity.this);
            LayoutInflater inflater = LayoutInflater.from(StoreDetailsActivity.this);
            View dialogView = inflater.inflate(R.layout.custom_user_input_picker_dialog, null);
            dialogBuilder.setView(dialogView);

            EditText editText = dialogView.findViewById(R.id.editText_NumberPicker);
            Button buttonConfirm = dialogView.findViewById(R.id.button_Confirm);
            Button buttonCancel = dialogView.findViewById(R.id.button_Cancel);
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

            buttonConfirm.setOnClickListener(view1 -> {

                if(editText.getText().toString().isEmpty()){
                    Toasty.error(StoreDetailsActivity.this, "দয়া করে পরিমাণ অ্যাড করুন!").show();
                }
                else{
                    cashCollection = SystemHelper.formatDouble(Double.valueOf(editText.getText().toString()));
                    double totalDue = SystemHelper.formatDouble(previousCredit + netValue);
                    double netCredit = SystemHelper.formatDouble(totalDue - cashCollection);

                    if(cashCollection > totalDue){
                        Toasty.error(StoreDetailsActivity.this, "বাকির থেকে বেশি পেমেন্ট গ্রহণযোগ্য নয়!").show();
                    }else{
                        editTextCashCollection.setText(banglaUtil.NumberToBangla(String.valueOf(cashCollection)));
                        textViewNetCredit.setText(banglaUtil.NumberToBangla(String.valueOf(netCredit)));
                    }
                }
                alertDialog.cancel();
            });

            buttonCancel.setOnClickListener(view12 -> alertDialog.cancel());
        });

        btnSubmit.setOnClickListener(view -> {

            if (netValue == 0.00){
                Toasty.error(StoreDetailsActivity.this, "দয়া করে পণ্য অ্যাড করুন!").show();
            }

            else if (cashCollection == 0.00) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(StoreDetailsActivity.this);
                LayoutInflater inflater = LayoutInflater.from(StoreDetailsActivity.this);
                View dialogView = inflater.inflate(R.layout.custom_user_confirmation_dialog, null);
                dialogBuilder.setView(dialogView);

                ImageView imgCross = dialogView.findViewById(R.id.imageView_Cross);
                Button buttonConfirmVisit = dialogView.findViewById(R.id.button_Confirm);
                Button buttonPendingVisit = dialogView.findViewById(R.id.button_Cancel);
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                buttonConfirmVisit.setOnClickListener(view13 -> {

                    if (memoInstance != null) {
                        ArrayList<OrderItem> orderItems = memoInstance.getOrderItems();
                        Order orderDetail = new Order();
                        orderDetail.setOutletID(outletID);
                        orderDetail.setOutletName(outletName);
                        orderDetail.setPreviousCredit(0.00);
                        orderDetail.setCashCollection(0.00);
                        orderDetail.setOrderNetValue(netValue);
                        orderDetail.setOrderStatus(SystemConstants.STATUS_VISITED);

                        //save
                        setOrderInformation(orderItems, orderDetail);
                        alertDialog.cancel();

                        SystemHelper.setStatus(outletName, true);

                    }
                });

                buttonPendingVisit.setOnClickListener(view14 -> {

                    if (memoInstance != null) {
                        ArrayList<OrderItem> orderItems = memoInstance.getOrderItems();
                        Order orderDetail = new Order();
                        orderDetail.setOutletID(outletID);
                        orderDetail.setOutletName(outletName);
                        orderDetail.setPreviousCredit(0.00);
                        orderDetail.setCashCollection(0.00);
                        orderDetail.setOrderNetValue(netValue);
                        orderDetail.setOrderStatus(SystemConstants.STATUS_VISITED_AND_PENDING);
                        orderDetail.setUploadStatus(0);

                        //save
                        setOrderInformation(orderItems, orderDetail);
                        alertDialog.cancel();
                        SystemHelper.setStatus(outletName, true);

                    }
                });

                imgCross.setOnClickListener(view15 -> alertDialog.cancel());

            } else {

                if (memoInstance != null) {
                    ArrayList<OrderItem> orderItems = memoInstance.getOrderItems();
                    Order orderDetail = new Order();
                    orderDetail.setOutletID(outletID);
                    orderDetail.setOutletName(outletName);
                    orderDetail.setPreviousCredit(0.00);
                    orderDetail.setCashCollection(cashCollection);
                    orderDetail.setOrderNetValue(netValue);
                    orderDetail.setOrderStatus(SystemConstants.STATUS_VISITED);
                    orderDetail.setUploadStatus(1);

                    //save
                    setOrderInformation(orderItems, orderDetail);
                    SystemHelper.setStatus(SystemConstants.ORDERED, true);
                }
            }
        });
    }

    private void loadChallan(int outletID) {

        mViewModel.getChallanStockItemWithMapping(outletID, memoInstance)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Challan>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onNext(List<Challan> deliveryManChallanItems) {
                        if (deliveryManChallanItems != null) {
                            adapter = new StoreDetailsListAdapter(deliveryManChallanItems, uiUpdateCallback);
                            recyclerView.setAdapter(adapter);
                            previousCashCollection = getNetCashCollection(deliveryManChallanItems);
                            doCalculatePromotion();
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

    }

    private double getNetProductPrice(List<Challan> deliveryManChallanItems) {
        double netProiductPrice = 0.0;
        if (deliveryManChallanItems != null) {
            for (Challan cItem : deliveryManChallanItems) {
                netProiductPrice += cItem.netProductPrice;
            }
        }
        return netProiductPrice;
    }

    private double getNetProductPriceFormatted(ArrayList<OrderItem> orderItems) {
        double netProductPrice = 0.0;
        double netProductPriceFormatted = 0.0;
        if (memoInstance.getOrderItems() != null) {
            for (OrderItem cItem : orderItems) {
                netProductPriceFormatted = SystemHelper.formatDouble(netProductPrice += cItem.pcsRate * cItem.orderQty);
            }
        }
        return netProductPriceFormatted;
    }

    private double getNetCashCollection(List<Challan> deliveryManChallanItems) {
        double collectedCash = 0.0;
        if (deliveryManChallanItems != null) {
            for (Challan cItem : deliveryManChallanItems) {
                collectedCash = cItem.cashCollected;
            }
        }
        return collectedCash;
    }

    @SuppressLint("HandlerLeak")
    Handler uiUpdateCallback = new Handler() {
        public void handleMessage(Message msg) {
            textViewDiscount.setText(("(কমিশন ৳ "+"অপেক্ষা করুন"+")"));
            doCalculatePromotion();
        }
    };


    private void doCalculatePromotion(){

        //getTradePromotion
        dbCallRepository
                .getTradePromotion(channelID, outletID, memoInstance)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FreeOrDiscount>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FreeOrDiscount freeOrDiscount) {
                        //Commission
                        double commission = freeOrDiscount.discount;

                        netValue = memoInstance.getOrderNetTotal() - commission;
                        textViewNetVaule.setText(banglaUtil.NumberToBangla(String.valueOf(netValue)));
                        textViewDiscount.setText(("(কমিশন ৳ "+banglaUtil.NumberToBangla(String.valueOf(commission))+")"));

                        if(editTextCashCollection.getText().toString().isEmpty()){
                            cashCollection = previousCashCollection;
                        }
                        else {
                            cashCollection = SystemHelper.formatDouble(Double.valueOf(banglaUtil.BanglaToEnglish(editTextCashCollection.getText().toString())));
                        }
                        double totalDue = SystemHelper.formatDouble(previousCredit + netValue);
                        double netCredit = SystemHelper.formatDouble(totalDue - cashCollection);

                        editTextCashCollection.setText(banglaUtil.NumberToBangla(String.valueOf(cashCollection)));

                        if(cashCollection > netCredit){
                            Toasty.error(StoreDetailsActivity.this, "বাকির থেকে বেশি পেমেন্ট গ্রহণযোগ্য নয়!").show();
                        }else{
                            textViewNetCredit.setText(banglaUtil.NumberToBangla(String.valueOf(netCredit)));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toasty.error(context,e.getMessage()).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setOrderInformation(ArrayList<OrderItem> orderItems, Order orderDetail) {

        dbCallRepository
                .insertOrderInformation(orderItems, orderDetail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        Toasty.info(StoreDetailsActivity.this, "অর্ডারটি সফলভাবে সেভ হয়েছে!").show();
                        memoInstance.clear();
                        Intent intent = new Intent(StoreDetailsActivity.this, SalesOutletListActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toasty.error(StoreDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (memoInstance != null)
            memoInstance.clear();
        Intent intent = new Intent(StoreDetailsActivity.this, SalesOutletListActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SystemConstants.MemoDetailsActivity_N_StockItemsActivity) {
            String stockItems = data.getStringExtra(SystemConstants.STOCK_ITEMS);

        }
    }
}
