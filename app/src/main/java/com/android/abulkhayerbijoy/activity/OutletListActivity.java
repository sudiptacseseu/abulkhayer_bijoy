package com.android.abulkhayerbijoy.activity;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.TextView;

import com.android.abulkhayerbijoy.R;
import com.android.abulkhayerbijoy.adapter.OutletListAdapter;
import com.android.abulkhayerbijoy.model.Outlet;
import com.android.abulkhayerbijoy.utils.BanglaFontUtil;
import com.android.abulkhayerbijoy.utils.SystemConstants;
import com.android.abulkhayerbijoy.viewmodel.OutletListActivityViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OutletListActivity extends AppCompatActivity implements OutletListAdapter.OrderItemListener {

    public Context context;
    private OutletListActivityViewModel mViewModel;
    private RecyclerView recyclerView;
    private OutletListAdapter adapter;
    private TextView tvTotalOutlet;
    private BanglaFontUtil banglaUtil = null;
    public TextView notVisitedOutlet, visitedOutlet, pendingOutlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_outlet_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("সেলস");
        banglaUtil = new BanglaFontUtil();

        context = OutletListActivity.this;
        mViewModel = new ViewModelProvider(OutletListActivity.this).get(OutletListActivityViewModel.class);

        notVisitedOutlet = findViewById(R.id.textView_NotVisited);
        visitedOutlet = findViewById(R.id.textView_Visited);
        pendingOutlet = findViewById(R.id.textView_Pending);
        tvTotalOutlet = findViewById(R.id.textView_TotalOutlet);

        recyclerView = findViewById(R.id.recyclerView_OutletList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        loadOutletCount(SystemConstants.STATUS_NOT_VISITED, notVisitedOutlet);
        loadOutletCount(SystemConstants.STATUS_VISITED_AND_PENDING, pendingOutlet);
        loadOutletCount(SystemConstants.STATUS_VISITED, visitedOutlet);

        notVisitedOutlet.setOnClickListener(view -> {
            loadNotVisitedOutlet();
        });

        visitedOutlet.setOnClickListener(view -> {
            loadVisitedOutlet();
        });

        pendingOutlet.setOnClickListener(view -> {
            loadPendingOutlet();
        });

        loadNotVisitedOutlet();
        allOutletCount();

    }

    private void allOutletCount() {

        mViewModel.getAllOutlets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Outlet>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onNext(List<Outlet> outletDetails) {
                        if(outletDetails != null){
                            tvTotalOutlet.setText(String.format("(%s)", banglaUtil.NumberToBangla(String.valueOf(outletDetails.size()))));
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

    private void loadVisitedOutlet() {
        mViewModel
                .getDeliveryManOrders(SystemConstants.STATUS_VISITED)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Outlet>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Outlet> outletDetails) {

                        adapter = new OutletListAdapter(new ArrayList<>(outletDetails),
                                OutletListActivity.this, SystemConstants.STATUS_VISITED);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadPendingOutlet() {
        mViewModel
                .getDeliveryManOrders(SystemConstants.STATUS_VISITED_AND_PENDING)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Outlet>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Outlet> outletDetails) {

                        adapter = new OutletListAdapter(new ArrayList<>(outletDetails),
                                OutletListActivity.this, SystemConstants.STATUS_VISITED_AND_PENDING);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadNotVisitedOutlet() {
        mViewModel
                .getDeliveryManOrders(SystemConstants.STATUS_NOT_VISITED)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Outlet>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Outlet> outletDetails) {

                        adapter = new OutletListAdapter(new ArrayList<>(outletDetails),
                                OutletListActivity.this, SystemConstants.STATUS_NOT_VISITED);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadOutletCount(int status, TextView textViewStatusCount) {
        mViewModel
                .getDeliveryManOrders(status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Outlet>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Outlet> outletDetails) {
                        textViewStatusCount.setText(banglaUtil.NumberToBangla(String.valueOf(outletDetails.size())));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(OutletListActivity.this, MainActivity.class);
        startActivity(intent);
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
    public void onOrderItemClicked(Outlet outletDetail) {
        Intent intent = new Intent(OutletListActivity.this, OutletDetailsActivity.class);
        intent.putExtra("OutletName", outletDetail.getName());
        intent.putExtra("OutletBanglaName", outletDetail.getBanglaName());
        intent.putExtra("OutletID", outletDetail.getCustomerID());
        intent.putExtra("ID", outletDetail.getId());
        intent.putExtra("ChannelID", outletDetail.getChannelID());
        startActivity(intent);
        finish();
    }
}
