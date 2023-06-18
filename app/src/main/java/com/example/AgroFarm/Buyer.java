package com.example.AgroFarm;

import android.provider.BaseColumns;

public class Buyer {

    private Buyer() {}

    public static class BuyerInfo implements BaseColumns {
        public static final String TABLE_NAME = "Buyers";
        public static final String COL_1 = "Name";
        public static final String COL_2 = "Address";
        public static final String COL_3 = "Mobile";
        public static final String COL_4 = "Character";
        public static final String COL_5 = "Email";
        public static final String COL_6 = "password";
    }
}
