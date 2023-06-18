package com.example.AgroFarm;

import android.provider.BaseColumns;

public class Harvest {
    private Harvest() {
    }

    public static class HarvestInfo implements BaseColumns {
        public static final String TABLE_NAME = "HarvestStock";
        public static final String COL_1 = "Code";
        public static final String COL_2 = "Name";
        public static final String COL_3 = "Status";
        public static final String COL_4 = "Stock";
        public static final String COL_5 = "Price";
        public static final String COL_6 = "Type";
        public static final String COL_7 = "User_Email";
        public static final String COL_8 = "Image";

    }
}
