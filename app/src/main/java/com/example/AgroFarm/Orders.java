package com.example.AgroFarm;

import android.provider.BaseColumns;

public class Orders {

    private Orders() {}

    public static class OrderInfo implements BaseColumns {
        public static final String TABLE_NAME = "Orders";
        public static final String COL_1 = "Product_Code";
        public static final String COL_2 = "Stock";
        public static final String COL_3 = "Total_Price";
        public static final String COL_4 = "Farmer_Email";
        public static final String COL_5 = "Buyer_Email";
    }
}
