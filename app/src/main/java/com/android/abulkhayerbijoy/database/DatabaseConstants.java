package com.android.abulkhayerbijoy.database;

public class DatabaseConstants {

    public static class DatabaseName {

        public static final String SR_BASIC_TABLE = "SRBasic_table";
        public static final String SKU = "SKU_table";
        public static final String CHALLAN = "ChallanItem_table";
        public static final String ORDER_ITEM = "OrderItem_table";
        public static final String ORDER = "Order_table";
        public static final String SECTION = "Section_table";
        public static final String OUTLET = "Outlet_table";
        public static final String SALES_ORDER_PROMOTION = "SalesOrderPromotion_table";

        //Promotion
        public static final String Tpr = "TPR_table";
        public static final String TPRSKUChnl = "TPRSKUChannel_table";
        public static final String TPRSlab = "TPRSlab_table";
        public static final String SlabItem = "SlabItem_table";
        public static final String TPOutlet = "TPOutlet_table";

        //Promotion Info
        public static final String TABLE_TRADE_PROMOTION = "tblTradePromotion";
        public static final String TABLE_SP_CUSTOMER = "tblSPCustomer";
        public static final String TABLE_TP_CHANNEL = "tblTPChannels";
        public static final String TABLE_TP_SKU = "tblTP_SKU";
        public static final String TABLE_TP_SLAB = "tblTPSlab";
        public static final String Table_CLP_POINT_TO_TK = "tblCLPPointToTK";
    }


    public static class PromotionFlag {
        //PROMOTION FLAGS
        /******************************************************************/
        /*****************SalesUnitEnum On OnlineSales*********************/
        public static final int TPR_PROGRAM_TYPE_QUANTITY_2 = 1; //pcs
        public static final int TPR_PROGRAM_TYPE_VALUE = 2; //value
        public static final int TPR_PROGRAM_TYPE_WEIGHT = 3; //weight
        public static final int TPR_PROGRAM_TYPE_LINECOUNT = 6; //Line Count
        /******************************************************************/
        public static final int TPR_PROGRAM_TYPE_QUANTITY_4 = 4;

        public static final int DISCOUNT = 1;
        public static final int FREE_PRODUCT = 2;
        public static final int GIFT = 3;
        public static final int CLP = 5;
    }
}
