package com.android.abulkhayerbijoy.activity;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;

import com.android.abulkhayerbijoy.model.OutletDetail;
import com.android.abulkhayerbijoy.model.SRBasic;
import com.android.abulkhayerbijoy.utils.SystemHelper;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.abulkhayerbijoy.R;
import com.android.abulkhayerbijoy.utils.BanglaFontUtil;
import com.android.abulkhayerbijoy.utils.SystemConstants;
import com.android.abulkhayerbijoy.viewmodel.MainActivityViewModel;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private static final String TAG_HOME = "home";
    public static String CURRENT_TAG = TAG_HOME;
    public TextView textViewDeliveryManName, textViewDeliveryManRoute, textViewMonthAndDate, textViewMonthAndYear,
            textViewDeliveryOutletNumber, textViewSalesTargetMonthly, textViewSalesMonthly, textViewAchievementMonthly,
            textViewSalesTargetDaily, textViewSalesDaily, textViewAchievementDaily;
    public Button buttonDeposit, buttonChallan, buttonSales;
    public static int navItemIndex = 0;
    public Handler mHandler;
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    public boolean shouldLoadHomeFragOnBackPress = true;
    private MainActivityViewModel mViewModel;
    boolean doubleBackToExitPressedOnce = false;
    private BanglaFontUtil banglaUtil = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        banglaUtil = new BanglaFontUtil();
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        mViewModel = ViewModelProviders.of(MainActivity.this).get(MainActivityViewModel.class);

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        setUpNavigationView();
        setHeaderInformation();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
        }

        textViewDeliveryManName = findViewById(R.id.textView_DeliveryManName);
        textViewDeliveryManRoute = findViewById(R.id.textView_DeliveryManRoute);
        textViewMonthAndDate = findViewById(R.id.textView_MonthAndDate);
        textViewMonthAndYear = findViewById(R.id.textView_MonthAndYear);
        textViewDeliveryOutletNumber = findViewById(R.id.textView_DeliveryOutletNumber);
        textViewSalesTargetMonthly = findViewById(R.id.textView_SalesTargetMonthly);
        textViewSalesMonthly = findViewById(R.id.textView_SalesMonthly);
        textViewAchievementMonthly = findViewById(R.id.textView_AchievementMonthly);
        textViewSalesTargetDaily = findViewById(R.id.textView_SalesTargetDaily);
        textViewSalesDaily = findViewById(R.id.textView_SalesDaily);
        textViewAchievementDaily = findViewById(R.id.textView_AchievementDaily);
        buttonDeposit = findViewById(R.id.button_Deposit);
        buttonChallan = findViewById(R.id.button_Challan);
        buttonSales = findViewById(R.id.button_Sales);

        buttonDeposit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DepositActivity.class);
            startActivity(intent);
        });

        buttonChallan.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ChallanActivity.class);
            startActivity(intent);
        });

        buttonSales.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SalesOutletListActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void setHeaderInformation() {

        mViewModel.getAllOutlets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<OutletDetail>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onNext(List<OutletDetail> outletDetails) {
                        if (outletDetails != null && outletDetails.size() > 0) {
                            textViewDeliveryOutletNumber.setText(banglaUtil.NumberToBangla(String.valueOf(outletDetails.size())));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        mViewModel.getSRInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SRBasic>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SRBasic srBasic) {

                        mViewModel.getSalesInformation()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<Double>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(Double saleValue) {

                                        double targetDaily = srBasic.getDailyTarget();
                                        double targetMonthly = srBasic.getMonthlyTarget();
                                        double achievementPercentageMonthly = Double.parseDouble(SystemHelper.formatDoublesAsPercentage(targetMonthly, saleValue));
                                        double achievementPercentageDaily = Double.parseDouble(SystemHelper.formatDoublesAsPercentage(targetDaily, saleValue));

                                        //For visit date, month & year
                                        String visitDate = srBasic.getVisitDate();

                                        if (!visitDate.isEmpty()){

                                            String visitDateInBangla = banglaUtil.dateInBanglaUnicode(visitDate, "dd MMM yyyy");
                                            String monthAndDateInBangla = visitDateInBangla.split(" ")[1]+ " " + visitDateInBangla.split(" ")[0];
                                            String monthAndYearInBangla = visitDateInBangla.split(" ")[1]+ ", " + visitDateInBangla.split(" ")[2];
                                            textViewMonthAndYear.setText(monthAndYearInBangla);
                                            textViewMonthAndDate.setText(monthAndDateInBangla);
                                        }
                                        textViewSalesTargetDaily.setText(banglaUtil.NumberToBangla(String.valueOf(targetDaily)));
                                        textViewSalesTargetMonthly.setText(banglaUtil.NumberToBangla(String.valueOf(targetMonthly)));
                                        textViewSalesDaily.setText(banglaUtil.NumberToBangla(String.valueOf(saleValue)));
                                        textViewSalesMonthly.setText(banglaUtil.NumberToBangla(String.valueOf(saleValue)));
                                        if(!Double.isInfinite(achievementPercentageMonthly) && !Double.isInfinite(achievementPercentageDaily) &&
                                                !String.valueOf(achievementPercentageMonthly).equals("NaN") && !String.valueOf(achievementPercentageDaily).equals("NaN")){
                                            textViewAchievementMonthly.setText(banglaUtil.NumberToBangla(String.valueOf(achievementPercentageMonthly)));
                                            textViewAchievementDaily.setText(banglaUtil.NumberToBangla(String.valueOf(achievementPercentageDaily)));
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

                        textViewDeliveryManName.setText(TextUtils.isEmpty(srBasic.getBanglaName()) ? srBasic.getName() : srBasic.getBanglaName());
                        if (srBasic.getDistributorBanglaName().isEmpty()){
                            textViewDeliveryManRoute.setText(srBasic.getRouteName());
                        }
                        else {
                            textViewDeliveryManRoute.setText(String.format("%s: %s", srBasic.getRouteName(), srBasic.getDistributorBanglaName()));
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

    private void loadHomeFragment() {

        selectNavMenu();
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    navItemIndex = 0;
                    CURRENT_TAG = TAG_HOME;
                    break;
                case R.id.nav_logout: {
                    //region Log out
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(false);
                    builder.setMessage("আপনি কি লগ আউট করতে চান?");
                    builder.setPositiveButton("হ্যাঁ", (dialogInterface, i) -> {
                        Prefs.remove(SystemConstants.SUCCESSFUL_LOGIN);

                        //Clear all tables
                        mViewModel
                                .clearAllTables()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        Prefs.remove(SRBasic.USER_INFO);
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                });
                    });
                    builder.setNegativeButton("না", (dialogInterface, i) -> dialogInterface.dismiss());
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    //endregion
                    return true;
                }
                default:
                    navItemIndex = 0;
            }

            //Checking if the item is in checked state or not, if not make it in checked state
            if (menuItem.isChecked()) {
                menuItem.setChecked(false);
            } else {
                menuItem.setChecked(true);
            }
            menuItem.setChecked(true);

            loadHomeFragment();

            return true;
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {

        //region Drawer Handle Logic
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }
        //endregion

        //region Double Back Pressed Logic
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toasty.info(this, "বাহির হতে আবার ব্যাক চাপুন।", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
        //endregion
    }
}
