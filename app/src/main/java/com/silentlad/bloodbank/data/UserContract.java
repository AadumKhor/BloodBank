package com.silentlad.bloodbank.data;

import android.provider.BaseColumns;

public class UserContract {
    private UserContract(){}

    public static final class UserEntry implements BaseColumns{
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_BG = "bloodGroup";
        public static final String COLUMN_CITY= "city";
    }
}
