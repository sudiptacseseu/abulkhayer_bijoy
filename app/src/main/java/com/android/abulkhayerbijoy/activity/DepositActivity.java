package com.android.abulkhayerbijoy.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.abulkhayerbijoy.R;
import com.android.abulkhayerbijoy.adapter.DepositListAdapter;
import com.android.abulkhayerbijoy.model.CashDeposit;
import com.android.abulkhayerbijoy.model.SKUDetail;
import com.android.abulkhayerbijoy.model.SRBasic;
import com.android.abulkhayerbijoy.repository.DatabaseCallRepository;
import com.android.abulkhayerbijoy.startup.Startup;
import com.android.abulkhayerbijoy.utils.BanglaFontUtil;
import com.android.abulkhayerbijoy.utils.MemoHelper;
import com.android.abulkhayerbijoy.utils.SystemHelper;
import com.android.abulkhayerbijoy.viewmodel.DepositActivityViewModel;

import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DepositActivity extends AppCompatActivity {

    private DepositActivityViewModel mViewModel;
    private RecyclerView recyclerView;
    private DepositListAdapter adapter;
    private TextView textViewPreviousCredit, textViewChallanValue, textViewTotalValue,
            textViewReturnProductValue, textViewTotalCredit, textViewNetCredit;
    private EditText editTextCashCollection;
    private BanglaFontUtil banglaUtil = null;
    private MemoHelper mHelper;
    private Button button_Submit;
    private DatabaseCallRepository dbCallRepository;
    private CashDeposit deposit = new CashDeposit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("নগদ জমা");

        mHelper = MemoHelper.instance();
        dbCallRepository = new DatabaseCallRepository(Startup.getInstance());

        textViewPreviousCredit = findViewById(R.id.textView_PreviousCredit);
        textViewChallanValue = findViewById(R.id.textView_ChallanValue);
        textViewTotalValue = findViewById(R.id.textView_TotalValue);
        textViewReturnProductValue = findViewById(R.id.textView_ReturnProductValue);
        textViewTotalCredit = findViewById(R.id.textView_TotalCredit);
        editTextCashCollection = findViewById(R.id.editText_CashCollection);
        textViewNetCredit = findViewById(R.id.textView_NetCredit);
        button_Submit = findViewById(R.id.button_Submit);
        recyclerView = findViewById(R.id.recyclerView_RemainingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        banglaUtil = new BanglaFontUtil();


        mViewModel = ViewModelProviders.of(DepositActivity.this).get(DepositActivityViewModel.class);
        getReturnSkuItems();
        getCashDepositInformation();

        editTextCashCollection.setOnClickListener(view -> {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DepositActivity.this);
            LayoutInflater inflater = LayoutInflater.from(DepositActivity.this);
            View dialogView = inflater.inflate(R.layout.custom_user_input_picker_dialog, null);
            dialogBuilder.setView(dialogView);

            EditText editText = dialogView.findViewById(R.id.editText_NumberPicker);
            Button buttonConfirm = dialogView.findViewById(R.id.button_Confirm);
            Button buttonCancel = dialogView.findViewById(R.id.button_Cancel);
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
            buttonConfirm.setOnClickListener(view1 -> {

                if (editText.getText().toString().isEmpty()) {

                    Toasty.error(DepositActivity.this, "দয়া করে পরিমাণ অ্যাড করুন!").show();
                } else {
                    double cashCollection = Double.parseDouble(editText.getText().toString());
                    if (deposit != null && deposit.getTotalDue() >= cashCollection) {
                        deposit.setTodaysCashCollection(cashCollection);
                        editTextCashCollection.setText(banglaUtil.NumberToBangla(SystemHelper.formatDoubleAsString(cashCollection)));
                        textViewNetCredit.setText(banglaUtil.NumberToBangla(SystemHelper.formatDoubleAsString(deposit.getNetDue())));
                    }else {
                        Toasty.error(DepositActivity.this, "বাকির থেকে বেশি পেমেন্ট গ্রহণযোগ্য নয়!").show();
                    }
                }
                alertDialog.cancel();

            });

            buttonCancel.setOnClickListener(view12 -> alertDialog.cancel());

        });

        button_Submit.setOnClickListener(view -> {
            if (SystemHelper.isInternetOn(DepositActivity.this)){
                if (editTextCashCollection.getText().toString().isEmpty()) {
                    Toasty.error(DepositActivity.this, "দয়া করে জমার পরিমাণ অ্যাড করুন!").show();
                }else {
                    mViewModel.doUploadBijoyInformation();
                }
            }
            else
                Toasty.error(DepositActivity.this, "ইন্টারনেটে যোগাযোগ সম্ভব হচ্ছে না!").show();
        });
    }

    private void getReturnSkuItems() {

        mViewModel.GetReturnSkuItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SKUDetail>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onNext(List<SKUDetail> challanReturnItems) {
                        if (challanReturnItems != null) {
                            adapter = new DepositListAdapter(challanReturnItems);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getCashDepositInformation() {
        mViewModel.getCashDepositInformation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CashDeposit>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onNext(CashDeposit item) {
                        deposit = item;
                        setCashCollectionHeader(deposit);
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

    private void setCashCollectionHeader(CashDeposit deposit) {
        if (deposit != null) {
            textViewPreviousCredit.setText(banglaUtil.NumberToBangla(SystemHelper.formatDoubleAsString(deposit.getPreviousDue())));
            textViewChallanValue.setText(banglaUtil.NumberToBangla(SystemHelper.formatDoubleAsString(deposit.getChallanValue())));
            textViewTotalValue.setText(banglaUtil.NumberToBangla(SystemHelper.formatDoubleAsString(deposit.getTotal())));
            textViewReturnProductValue.setText(banglaUtil.NumberToBangla(SystemHelper.formatDoubleAsString(deposit.getReturnProductPrice())));
            textViewTotalCredit.setText(banglaUtil.NumberToBangla(SystemHelper.formatDoubleAsString(deposit.getTotalDue())));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
}
