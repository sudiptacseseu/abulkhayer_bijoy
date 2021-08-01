package com.android.abulkhayerbijoy.utils;

import java.util.ArrayList;
import java.util.Locale;

public class BijoyHelper {

    //HELPER METHODS

    public static double getItemPrice(int ctn, int pcs, int ctnPcsRatio, double itemPrice) {
        int quantity = 0;
        if (ctnPcsRatio > 0) {
            quantity = ctn * ctnPcsRatio + pcs;
        }

        return quantity * itemPrice;
    }

    public static int getQuantity(int ctn, int pcs, int ctnPcsRatio) {
        int quantity = 0;

        if (ctnPcsRatio > 0) {
            quantity = ctn * ctnPcsRatio + pcs;
        }
        return quantity;
    }

    public static int getCtn(int totalQty, int ctnPcsRatio) {
        int ctnNumber = 0;


        if (ctnPcsRatio > 0) {
            ctnNumber = totalQty / ctnPcsRatio;

        }
        return ctnNumber;
    }

    public static int getPcs(int totalQty, int ctnPcsRatio) {
        int pcsNumber = 0;
        if (ctnPcsRatio > 0) {
            pcsNumber = totalQty % ctnPcsRatio;
        }
        return pcsNumber;
    }

    public static String getCtnPcs(int orderQty, int ctnPcsRatio) {
        int ctnNumber = 0;
        int pcsNumber = 0;

        if (ctnPcsRatio > 0) {
            ctnNumber = orderQty / ctnPcsRatio;
            pcsNumber = orderQty % ctnPcsRatio;
        }
        return String.format(Locale.US, "%d-%d", ctnNumber, pcsNumber);
    }

    public static String getFormattedOrder(int orderQty, int ctnPcsRatio) {
        int ctnNumber = 0;
        int pcsNumber = 0;

        if (ctnPcsRatio > 0) {
            ctnNumber = orderQty / ctnPcsRatio;
            pcsNumber = orderQty % ctnPcsRatio;
        }
        return String.format(Locale.US, "অর্ডার  %d-%d", ctnNumber, pcsNumber);
    }

    public static String getFormattedIssue(int issueQty, int ctnPcsRatio) {
        int ctnNumber = 0;
        int pcsNumber = 0;

        if (ctnPcsRatio > 0) {
            ctnNumber = issueQty / ctnPcsRatio;
            pcsNumber = issueQty % ctnPcsRatio;
        }
        return String.format(Locale.US, "ইস্যু %d-%d", ctnNumber, pcsNumber);
    }

    public static String getFormattedConfirm(int issueQty, int ctnPcsRatio) {
        int ctnNumber = 0;
        int pcsNumber = 0;

        if (ctnPcsRatio > 0) {
            ctnNumber = issueQty / ctnPcsRatio;
            pcsNumber = issueQty % ctnPcsRatio;
        }
        return String.format(Locale.US, "কনফার্ম %d-%d", ctnNumber, pcsNumber);
    }

}
