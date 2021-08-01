package com.android.abulkhayerbijoy.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.abulkhayerbijoy.R;
import com.android.abulkhayerbijoy.adapter.ChallanListAdapter;
import com.android.abulkhayerbijoy.model.ChallanDetail;
import com.android.abulkhayerbijoy.model.SKUDetail;
import com.android.abulkhayerbijoy.model.SRBasic;
import com.android.abulkhayerbijoy.repository.DatabaseCallRepository;
import com.android.abulkhayerbijoy.startup.Startup;
import com.android.abulkhayerbijoy.utils.BanglaFontUtil;
import com.android.abulkhayerbijoy.utils.MemoHelper;
import com.android.abulkhayerbijoy.utils.SystemHelper;
import com.android.abulkhayerbijoy.viewmodel.ChallanActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChallanActivity extends AppCompatActivity {

    private ChallanActivityViewModel mViewModel;
    private RecyclerView recyclerView;
    private ChallanListAdapter adapter;
    private TextView textViewChallanValue, textViewSkuQuantity;
    private BanglaFontUtil banglaUtil = null;
    private MemoHelper mHelper;
    private Button button_Submit;
    private DatabaseCallRepository dbCallRepository;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        banglaUtil = new BanglaFontUtil();
        setContentView(R.layout.activity_challan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("চালান");
        context = ChallanActivity.this;

        mHelper = MemoHelper.instance();
        dbCallRepository = new DatabaseCallRepository(Startup.getInstance());

        textViewChallanValue = findViewById(R.id.textView_ChallanValue);
        button_Submit = findViewById(R.id.button_Submit);
        textViewSkuQuantity = findViewById(R.id.textView_SkuQuantity);
        recyclerView = findViewById(R.id.recyclerView_ChallanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mViewModel = ViewModelProviders.of(ChallanActivity.this).get(ChallanActivityViewModel.class);

        mViewModel.GetSkuItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SKUDetail>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onNext(List<SKUDetail> deliveryManChallanItems) {
                        if (deliveryManChallanItems != null) {
                            adapter = new ChallanListAdapter(deliveryManChallanItems, uiUpdateCallback);
                            recyclerView.setAdapter(adapter);
                            setHeaderValue();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        button_Submit.setOnClickListener(view -> {

            if (mHelper != null) {
                ArrayList<ChallanDetail> challanItems = mHelper.getChallanItems();

                if (challanItems.size() == 0) {
                    Toasty.error(ChallanActivity.this, "দয়া করে এস.কে.ইউ অ্যাড করুন!").show();
                } else {
                    if (SystemHelper.isInternetOn(ChallanActivity.this)) {
                        //region save
                        /*dbCallRepository
                                .insertChallanDetails(challanItems)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        Toasty.info(ChallanActivity.this, "চালান সফলভাবে তৈরি হয়েছে!").show();
                                        if (mHelper != null)
                                            mHelper.clear();
                                        finish();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Toasty.error(ChallanActivity.this, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                                    }
                                });*/
                        //endregion


                        //region challan confirmation
                        mViewModel.getSRInfo()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<SRBasic>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(SRBasic srBasic) {

                                        mViewModel.doChallanItemsConfirmation(challanItems, srBasic);
                                        finish();
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                        //endregion

                    } else {
                        Toasty.error(ChallanActivity.this, "ইন্টারনেটে যোগাযোগ সম্ভব হচ্ছে না!").show();
                    }
                }
            }
        });
    }

    private void setHeaderValue() {

        mViewModel
                .getTotalChallanValue()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ChallanDetail>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ChallanDetail> cItems) {
                        if (cItems != null) {
                            double challanValue = 0.0;
                            for (ChallanDetail cItem : cItems) {
                                challanValue += cItem.getChallanValue();
                            }

                            textViewChallanValue.setText(banglaUtil.NumberToBangla(SystemHelper.formatDoubleWithTAKASign(challanValue)));
                            textViewSkuQuantity.setText(banglaUtil.NumberToBangla(String.valueOf(cItems.size())));
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

    @SuppressLint("HandlerLeak")
    Handler uiUpdateCallback = new Handler() {
        public void handleMessage(Message msg) {
            int itemCount = 0;
            double productValue = 0.0;

            if (mHelper != null) {
                for (ChallanDetail item : mHelper.getChallanItems()) {
                    itemCount++;
                    productValue += item.challanValue;
                }
            }

            textViewSkuQuantity.setText(banglaUtil.NumberToBangla(String.valueOf(itemCount)));
            textViewChallanValue.setText(banglaUtil.NumberToBangla(String.valueOf(SystemHelper.formatDouble(productValue))));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.print_menu, menu);
        return true;
    }

    //region On Back Pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mHelper != null)
                    mHelper.clear();
                finish();
                return true;

            //case R.id.print:
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion
}