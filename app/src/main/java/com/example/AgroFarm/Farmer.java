package com.example.AgroFarm;

import android.provider.BaseColumns;

public class Farmer {

    private Farmer() {}

    public static class FarmerInfo implements BaseColumns {
        public static final String TABLE_NAME = "Farmers";
        public static final String COL_1 = "Name";
        public static final String COL_2 = "Address";
        public static final String COL_3 = "Mobile";
        public static final String COL_4 = "Character";
        public static final String COL_5 = "Email";
        public static final String COL_6 = "password";
        public static final String COL_7 = "Latitude";
        public static final String COL_8 = "Longitude";
    }
}
