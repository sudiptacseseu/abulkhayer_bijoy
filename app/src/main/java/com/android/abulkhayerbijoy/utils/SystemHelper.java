package com.android.abulkhayerbijoy.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

import com.android.abulkhayerbijoy.model.SRBasic;
import com.android.abulkhayerbijoy.startup.Startup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class SystemHelper {

    public static void setBooleanStatus(final String key, boolean isDownloadedChallan) {
        Prefs.remove(key);
        Prefs.putBoolean(key, isDownloadedChallan);
    }

    public static boolean getBooleanStatus(final String key) {
        boolean status = Prefs.getBoolean(key, false);
        return status;
    }

    public static void setStatus(final String key, boolean isOrdered) {
        Prefs.remove(key);
        Prefs.putBoolean(key, isOrdered);
    }

    public static boolean getStatus(final String key) {
        boolean status = Prefs.getBoolean(key, false);
        return status;
    }

    public static void setOutletCount(final String key, int data) {
        //Prefs.remove(key);
        Prefs.putInt(key, data);
    }

    public static int getOutletCount(final String key) {
        return Prefs.getInt(key, 0);
    }

    public static void setUserInfoOnSharedPreference(SRBasic item) {
        Prefs.remove(SRBasic.USER_INFO);
        Prefs.putString(SRBasic.USER_INFO, new Gson().toJson(item));
    }

    public static SRBasic getUserInfOnSharedPreference() {
        String struser = Prefs.getString(SRBasic.USER_INFO, "");
        SRBasic user = null;
        if (!TextUtils.isEmpty(struser))
            user = new Gson().fromJson(struser, new TypeToken<SRBasic>() {
            }.getType());

        return user;
    }

    public static int dpToPx(int dp) {
        Resources r = Startup.getContext().getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static int pxToDp(float px) {
        return Math.round(px / ((float) Startup.getContext().getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static double formatDouble(double number) {
        try {
            DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
            symbols.setDecimalSeparator('.');
            DecimalFormat format = new DecimalFormat("#0.00", symbols);
            return Double.valueOf(format.format(number));
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0.00;
        }
    }

    public static String formatDoubleAsString(double number) {
        try {
            DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
            symbols.setDecimalSeparator('.');
            DecimalFormat format = new DecimalFormat("#0.00", symbols);
            return format.format(number);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "0.00";
        }
    }

    public static String formatDoubleWithTAKASign(double number) {
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("#0.00", symbols);
        return String.format(Locale.US, "৳ %s", format.format(number));
    }

    public static String formatDoublesAsPercentage(double target, double achieved) {
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("#0.00", symbols);
        double percentage = achieved / target * 100;

        if (!Double.isInfinite(percentage)){
            return format.format(percentage);
        }
        else {
            return format.format(0.00);
        }

    }

    public static double formatDoublesAsPercentageDouble(double target, double achieved) {
        return (achieved / target * 100);
    }

    public static void appendTextAndScroll(TextView mTextView, String text) {
        if (mTextView != null) {
            //mTextView.setMovementMethod(new ScrollingMovementMethod());
            // append the new string
            mTextView.append(text + "\n");
            // find the amount we need to scroll.  This works by
            // asking the TextView's internal layout for the position
            // of the final line and then subtracting the TextView's height
            final int scrollAmount = mTextView.getLayout().getLineTop(mTextView.getLineCount()) - mTextView.getHeight();
            // if there is no need to scroll, scrollAmount will be <=0
            if (scrollAmount > 0)
                mTextView.scrollTo(0, scrollAmount);
            else
                mTextView.scrollTo(0, 0);
        }
    }

    public static boolean isAppRunning(final String packageName) {
        final ActivityManager activityManager = (ActivityManager) Startup.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null) {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isInternetOn(Context ctx) {
        Context context;
        context = ctx.getApplicationContext();
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {

            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

}
