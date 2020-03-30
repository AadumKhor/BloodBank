package com.silentlad.bloodbank.data;

import android.provider.BaseColumns;

public class HospitalContract {

    private HospitalContract() {
    }

    public static final class HospitalEntry implements BaseColumns {
        public static final String TABLE_NAME = "hospitals";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_START_DAY = "day1";
        public static final String COLUMN_END_DAY = "day2";
        public static final String COLUMN_START_TIME = "time1";
        public static final String COLUMN_END_TIME = "time2";
        public static final String COLUMN_GMAPS = "gmap";
    }
}
